package vista;

import cliente.ClienteTruco;
import modelo.Jugador;
import modelo.Partida;
import persistencia.PersistenciaJugador;
import persistencia.PersistenciaPartida;
import servidor.ServidorTruco;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
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
    private Image icono;

    public VistaInicio() {
        setContentPane(panel1);
        setSize(450, 460);
        setTitle("Menú principal - Truco");
        setLocationRelativeTo(null);
        setResizable(false);

        initIcono();
        setIconImage(icono);

        setBotonesInicio();

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
        JFrame frame2 = new JFrame("Top jugadores - Truco");
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

        int indice = 1;
        for (Jugador j : PersistenciaJugador.listaJugadoresGuardados(true)) {
            if(j != null) listModel.addElement(indice + "- " + j.toString());
            indice++;
        }

        frame2.setVisible(true);
    }

    private void initIcono() {
        icono = new ImageIcon("icono.jpeg").getImage();
        Image originalImage = icono;
        Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icono = new ImageIcon(scaledImage).getImage();
    }

    private void setBotonesInicio(){
        eliminarAllActionListener();
        btnReglas.setEnabled(true);
        btnAnotador.setEnabled(true);
        btnCrearNuevo.setEnabled(true);

        instrucciones.setText("¡Bienvenido al Trucontardi! Seleccione una opción");

        // EVENTOS

        btnCrearNuevo.addActionListener(e -> {
            setBotonesJugar();
        });

        btnSalir.setText("  SALIR  ");
        btnSalir.addActionListener(e -> System.exit(0));

        btnTop.setText(" TOP JUGADORES");
        btnTop.addActionListener(e -> pantallaTopJugadores());


        btnReglas.addActionListener(e -> abrirURL("https://trucogame.com/pages/reglamento-de-truco-argentino"));

        btnAnotador.setText(" ANOTADOR ");
        btnAnotador.addActionListener(e -> {
            new AnotadorGrafico(this).iniciar();
            setVisible(false);
        });
    }

    private void setBotonesJugar(){
        eliminarAllActionListener();

        instrucciones.setText("Seleccione una opción para empezar a jugar");

        btnCrearNuevo.setEnabled(true);
        btnCrearNuevo.setText("CREAR NUEVA PARTIDA");
        btnCrearNuevo.addActionListener(e -> {
            try {
                new ServidorTruco();
                btnCrearNuevo.setEnabled(false);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });

        btnTop.setText("REANUDAR UNA PARTIDA");
        btnTop.addActionListener(e -> {
            reanudarPartida();
        });

        btnAnotador.setText("INGRESAR A UNA PARTIDA");
        btnAnotador.addActionListener(e -> {
            try {
                int tipoPartida = ingresarPartida();
                if(tipoPartida == 1) new ClienteTruco(false); // 1 == ingresa a una nueva partida
                else if (tipoPartida == 2) new ClienteTruco(true); // 1 == reanuda una partida pendiente

                if(tipoPartida == 2 || tipoPartida == 1) dispose();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });

        btnReglas.setEnabled(false);

        btnSalir.setText("  VOLVER  ");
        btnSalir.addActionListener(e -> {
            setBotonesInicio();
        });
    }

    private int ingresarPartida(){
        ArrayList<String> opcionesPuntos = new ArrayList<>();

        opcionesPuntos.add("INGRESAR A UNA PARTIDA NUEVA");
        opcionesPuntos.add("INGRESAR A UNA PARTIDA PENDIENTE");

        String tipoPartida = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione al tipo de partida que quiere ingresar", "Ingresar a la partida",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionesPuntos.toArray(),
                null
        );

        if(tipoPartida == null) return 0; // 0 == null osea que cancelo no hago nada
        else if(tipoPartida.equals("INGRESAR A UNA PARTIDA NUEVA")) return 1; // 1 == ingresa a una partida nueva
        else return 2; // reanuda una partida pendiente
    }

    private void reanudarPartida(){
        ArrayList<Partida> partidas = PersistenciaPartida.listaPartidasGuardadas();

        // pregunto si tiene partidas guardadas pendientes
        if(partidas == null || partidas.isEmpty()) {
            JFrame frameMSJ;
            frameMSJ = new JFrame("TRUCONTARDI");
            frameMSJ.setSize(400, 100);
            JPanel panelPrincipal = (JPanel) frameMSJ.getContentPane();
            panelPrincipal.setLayout(new BorderLayout());

            JLabel etiqueta1 = new JLabel("NO TIENES PARTIDAS PENDIENTES, ¡PARA JUGAR CREA UNA!");
            frameMSJ.dispose();
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
            if(partida != null) {
                new ServidorTruco(partida);
                btnCrearNuevo.setEnabled(false);
            }
        }
    }

    //
    // Actions listeners
    //

    private void eliminarAllActionListener(){
        removeAllActionListeners(btnAnotador);
        removeAllActionListeners(btnCrearNuevo);
        removeAllActionListeners(btnTop);
        removeAllActionListeners(btnSalir);
        removeAllActionListeners(btnReglas);
    }

    private void removeAllActionListeners(AbstractButton button) {
        ActionListener[] listeners = button.getActionListeners();
        for (ActionListener listener : listeners) {
            button.removeActionListener(listener);
        }
    }
}
