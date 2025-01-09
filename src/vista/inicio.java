package vista;

import cliente.ClienteTruco;
import modelo.Jugador;
import persistencia.PersistenciaJugador;
import servidor.ServidorTruco;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;

public class inicio extends JFrame{
    private JPanel panel1;
    private JButton btnCrearNuevo;
    private JButton btnSalir;
    private JButton btnReglas;
    private JButton btnTop;
    private JButton btnIngresar;
    private JLabel titulo;
    private JLabel instrucciones;

    public inicio() {
        setSize(500, 450);
        setContentPane(panel1);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Menú principal - Truco");

        instrucciones.setText("¡Bienvenido al Trucontardi! Seleccione una opción");

        // EVENTOS

        btnCrearNuevo.addActionListener(e -> {
            try {
                new ServidorTruco();
                btnCrearNuevo.setEnabled(false); // desactivo para que cree solo 1 servidor
                instrucciones.setText("¡Servido creado con éxito! Ahora puede ingresar a él");
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });

        btnIngresar.addActionListener(e -> {
            try {
                new ClienteTruco();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });

        btnSalir.addActionListener(e -> {
            System.exit(0);
        });

        btnTop.addActionListener(e -> {
            pantallaTopJugadores();
        });

    }


    public void iniciar(){
        setVisible(true);
    }

    public void salir(){
        setVisible(false);
    }

    //
    //  metodos privados
    //

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
}
