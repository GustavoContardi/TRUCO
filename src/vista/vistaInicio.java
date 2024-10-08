package vista;

import cliente.ClienteTruco;
import enums.OpcionesInicio;
import interfaces.IVistaInicio;
import modelo.Jugador;
import persistencia.PersistenciaJugador;
import servidor.ServidorTruco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.net.URI;

import static enums.OpcionesInicio.*;


public class vistaInicio implements IVistaInicio {

    private JPanel ventana;
    private JLabel tituloLabel;
    private JButton btnIniciarNueva;
    private JButton btnReanudar;
    private JButton btnAnotador;
    private JButton btnSalir;
    private JButton btnReglas;
    private JLabel instrucciones;
    private JFrame frame;
    private anotadorGrafico anotadorG;

    //
    //  constructor
    //

    public vistaInicio() {
        this.frame = new JFrame("APP TRUCO");
        frame.setContentPane(ventana);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setSize(500, 500);
        frame.setVisible(true);
        anotadorG = new anotadorGrafico(this);

        setBotonesInicio();

    }

    //
    //  metodos publicos
    //

    public void setBotonesInicio(){
        eliminarTodosAcLis();
        instrucciones.setText("¡Bienvenido al Trucontardi! Seleccione una opción para comenzar.");

        btnIniciarNueva.setText("INICIAR TRUCONTARDI");
        btnReanudar.setText("TOP JUGADORES GANADORES"); // revisar para hacerlo mas corto o nose
        btnAnotador.setText("ANOTADOR");
        btnAnotador.setEnabled(true);
        btnReglas.setEnabled(true);

        btnSalir.setText(" SALIR ");
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnIniciarNueva.setVisible(true);

        btnIniciarNueva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBotonesIniciarRed();
            }
        });

        btnReanudar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantallaTopJugadores();
            }
        });

        btnAnotador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                anotadorG.iniciar();
            }
        });

        btnReglas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirURL("https://www.nhfournier.es/como-jugar/truco/");
            }
        });
    }


    public void botonesElegirVista(){
        eliminarTodosAcLis();

        instrucciones.setText("Seleccione la vista del juego");
        btnSalir.setText(" VOLVER ");
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBotonesIniciarRed();
            }
        });

        btnIniciarNueva.setEnabled(true);
        btnIniciarNueva.setText(" VISTA GRAFICA ");
        btnIniciarNueva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new ClienteTruco(VISTA_GRAFICA_NO_REANUDAR);
                    frame.dispose();
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnReanudar.setText(" VISTA CONSOLA ");
        btnReanudar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new ClienteTruco(VISTA_CONSOLA_NO_REANUDAR);
                    frame.dispose();
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }

    private void setBotonesIniciarRed(){
        eliminarTodosAcLis();
        instrucciones.setText("Seleccione una opción");
        btnIniciarNueva.setText("CREAR NUEVO SERVIDOR");
        btnReanudar.setText("INGRESAR A UN SERVIDOR");
        btnAnotador.setText("REANUDAR UNA PARTIDA");
        btnReglas.setEnabled(false);
        btnAnotador.setEnabled(false); // hasta que tengamos bien la serializacion, al final del desarrollo

        btnIniciarNueva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new ServidorTruco();
                    //btnIniciarNueva.setEnabled(false);
                    instrucciones.setText("¡Servidor Creado! Ahora puedes ingresar a él");
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnReanudar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botonesElegirVista();
            }
        });
        btnAnotador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBotonesRecuperarPartida();
            }
        });

        btnSalir.setText("VOLVER");
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBotonesInicio();
            }
        });
    }

    public void iniciar(){
        frame.setVisible(true);
    }

    public void salir(){
        frame.setVisible(false);
    }

    //
    //  metodos privados
    //

    private void removeAllActionListeners(AbstractButton button) {
        ActionListener[] listeners = button.getActionListeners();
        for (ActionListener listener : listeners) {
            button.removeActionListener(listener);
        }
    }

    private void eliminarTodosAcLis(){
        removeAllActionListeners(btnReglas);
        removeAllActionListeners(btnSalir);
        removeAllActionListeners(btnIniciarNueva);
        removeAllActionListeners(btnAnotador);
        removeAllActionListeners(btnReanudar);
    }

    private void abrirURL(String url){
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
                // Manejo de errores: puedes mostrar un mensaje al usuario aquí
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
            listModel.addElement(j.toString());
        }

        frame2.setVisible(true);
    }

    private void setBotonesRecuperarPartida(){
        eliminarTodosAcLis();
        instrucciones.setText("Seleccione una opción");
        btnIniciarNueva.setText("CREAR NUEVO SERVIDOR");
        btnReanudar.setText("INGRESAR A UN SERVIDOR");
        btnAnotador.setText("REANUDAR UNA PARTIDA");
        btnReglas.setEnabled(false);
        btnAnotador.setEnabled(false); // hasta que tengamos bien la serializacion, al final del desarrollo

        btnIniciarNueva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vistaEleccion eleccion = new vistaEleccion();
                //eleccion.iniciarRecuperacionPartida();
                btnIniciarNueva.setEnabled(false);
                instrucciones.setText("¡Servidor Creado! Ahora puedes ingresar a él");

            }
        });

        btnReanudar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVistaRecuperarPartida();
            }
        });

        btnAnotador.setEnabled(false);

        btnSalir.setText("VOLVER");
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBotonesIniciarRed();
            }
        });
    }

    private void setVistaRecuperarPartida(){

    }

}
