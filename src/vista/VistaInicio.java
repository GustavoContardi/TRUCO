package vista;

import cliente.ClienteTruco;
import modelo.Anotador;
import modelo.Jugador;
import modelo.Partida;
import persistencia.PersistenciaJugador;
import persistencia.PersistenciaPartida;
import servidor.ServidorTruco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class VistaInicio extends JFrame {
    private JPanel panel1;
    private JButton btnCrearNuevo;
    private JButton btnSalir;
    private JButton btnReglas;
    private JButton btnTop;
    private JButton btnAnotador;
    private JLabel titulo;
    private JLabel instrucciones;
    private JButton btnReanudar;

    public VistaInicio() {
        setSize(500, 450);
        setContentPane(panel1);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Menú principal - Truco");

        instrucciones.setText("¡Bienvenido al Trucontardi! Seleccione una opción");

        // EVENTOS
        btnCrearNuevo.addActionListener(e -> pantallaJugar(false));

        btnSalir.addActionListener(e -> System.exit(0));

        btnTop.addActionListener(e -> pantallaTopJugadores());

        btnReanudar.addActionListener(e -> pantallaJugar(true)); // Reanudar partida

        btnReglas.addActionListener(e -> abrirURL("https://trucogame.com/pages/reglamento-de-truco-argentino"));

        btnAnotador.addActionListener(e -> {
            AnotadorGrafico anotador = new AnotadorGrafico(this);
            anotador.iniciar();
            setVisible(false);
        });


    }

    public void iniciar(){
        setVisible(true);
    }

    public void iniciarConPantallaCarga(){
        PantallaCarga pantallaCarga = new PantallaCarga();
        pantallaCarga.mostrarPantalla();
        setVisible(true);
    }

    public void salir(){
        setVisible(false);
    }

    //
    //  metodos privados
    //

    // abre una pestaña del navegador con la url que se le pase
    private void abrirURL(String url){
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    private void pantallaTopJugadores(){
        JFrame frame2 = new JFrame("TRUCONTARDI");
        frame2.setResizable(false);
        frame2.setSize(500, 600);
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel ventana = new JPanel();
        ventana.setLayout(new BorderLayout()); // Usar BorderLayout para que el JScrollPane ocupe todo el espacio
        JList<String> list = new JList<>();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        list.setModel(listModel);
        JScrollPane scroll = new JScrollPane(list); // Agregar el JList al JScrollPane

        frame2.setContentPane(ventana);
        ventana.add(scroll, BorderLayout.CENTER); // Agregar el JScrollPane al centro del BorderLayout

        for (Jugador j : PersistenciaJugador.listaJugadoresGuardados(true)) {
            if(j != null) listModel.addElement(j.toString());
        }

        frame2.setVisible(true);
    }

    private void pantallaJugar(boolean reanuda){
        // Crear el marco de la ventana
        JFrame frame = new JFrame("APP TRUCO - REANUDAR PARTIDA");
        frame.setSize(450, 170);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // Centrar la ventana

        // Crear el panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Etiqueta de instrucciones
        JLabel label = new JLabel("INGRESE UNA OPCION PARA REANUDAR LA PARTIDA", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(label, BorderLayout.CENTER);

        // Espaciador entre la etiqueta y los botones
        //panel.add(Box.createVerticalStrut(20), BorderLayout.CENTER);

        // Panel para botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnCrearServer = new JButton();
        JButton btnIngresarServer = new JButton();

        if(reanuda){

            btnCrearServer.setText("CREAR UN SERVIDOR");
            btnIngresarServer.setText("INGRESAR A UN SERVIDOR");
            // le seteo los action listener antes de añadirlos al panel
            btnIngresarServer.addActionListener(e -> {
            try {
                new ClienteTruco(true);
                dispose();
                frame.dispose();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
            });

            btnCrearServer.addActionListener(e -> {
                ArrayList<Partida> partidas = PersistenciaPartida.listaPartidasGuardadas();

                if(partidas == null || partidas.isEmpty()) {
                    JFrame frameMSJ;
                    frameMSJ = new JFrame("TRUCONTARDI");
                    frameMSJ.setSize(400, 100);
                    JPanel panelPrincipal = (JPanel) frameMSJ.getContentPane();
                    panelPrincipal.setLayout(new BorderLayout());

                    JLabel etiqueta1 = new JLabel("NO TIENES PARTIDAS PENDIENTES, ¡PARA JUGAR CREA UNA!");
                    panelPrincipal.add(etiqueta1, BorderLayout.CENTER);

                    frameMSJ.setVisible(true);
                    frameMSJ.setLocationRelativeTo(null);
                }
                else{
                    Partida partida = (Partida) JOptionPane.showInputDialog(
                            null,
                            "Seleccione la partida que quiera reanudar", "PARTIDAS PENDIENTES",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            partidas.toArray(),
                            null
                    );
                    new ServidorTruco(partida);
                }
                    btnCrearServer.setEnabled(false);
            });

        }
        else {
            btnCrearServer.setText("CREAR UN JUEGO");
            btnIngresarServer.setText("INGRESAR A UN JUEGO");
            btnCrearServer.addActionListener(e -> {
                try {
                    new ServidorTruco();
                    //instrucciones.setText("¡Servido creado con éxito! Ahora puede ingresar a él");
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            });

            btnIngresarServer.addActionListener(e -> {
                try {
                    new ClienteTruco(false);
                    dispose();
                    frame.dispose();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            });
        }

        buttonPanel.add(btnCrearServer);
        buttonPanel.add(btnIngresarServer);


        panel.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(panel);
        frame.setVisible(true);
    }
}
