package vista;

import interfaces.IControlador;
import interfaces.IVistaEleccion;
import modelo.Jugador;
import modelo.Partida;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class VistaEleccion implements IVistaEleccion {
    private JPanel ventana;
    private JTabbedPane tabbedPane1;
    private JPanel JUGADORES;
    private JPanel Eleccion;
    private JList list;
    private JComboBox cbEleccion;
    private JButton btnElegir;
    private JPanel ABMJugador;
    private JPanel panelBtnABM;
    private JButton btnCrearJugador;
    private JButton btnActualizarJugador;
    private JButton btnEliminarJugador;
    private JTextField textRegistrar;
    private JButton btnRegistrar;
    private final JFrame frame;
    private IControlador controlador;
    private String nombreJugador;

    private DefaultListModel<String> listModel;

    public VistaEleccion() throws RemoteException {
        this.frame = new JFrame("TRUCONTARDI");
        frame.setContentPane(ventana);
        frame.setSize(600, 450);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        listModel = new DefaultListModel<>();
        list.setModel(listModel);

        procesarAltaJugador();
        procesarActualizarJugador();
        procesarEliminarJugador();
        procesarEleccionJugador();
        setJMenubar();
    }

    public void procesarAltaJugador() throws RemoteException {
        nombreJugador = ""; // reseteo la variable cada vez que se va a dar de alta un jugador
        removeAllActionListeners(btnCrearJugador);
        btnCrearJugador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ventanaCrearJugador();
                nombreJugador = JOptionPane.showInputDialog(frame, "Por favor, ingresa el nombre:", "CREAR JUGADOR", JOptionPane.QUESTION_MESSAGE);
                if(!(nombreJugador.trim().isEmpty())){
                    try {
                        controlador.agregarJugador(nombreJugador);
                        panelAvisos("¡JUGADOR REGISTRADO CON ÉXITO!");
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

    }

    public void procesarActualizarJugador() throws RemoteException {
        removeAllActionListeners(btnActualizarJugador);
        btnActualizarJugador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaActualizarJugador();
            }
        });
    }

    public void procesarEleccionJugador() throws RemoteException {
        removeAllActionListeners(btnElegir);
        btnElegir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Jugador jugador = (Jugador) cbEleccion.getSelectedItem();

                    if(jugador != null){
                        controlador.setJugador(jugador.getIDJugador());
                        frame.dispose();
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // este metodo pone el action listener para que se setee el jugador cuando se reanuda la partida
    public void procesarEleccionJugadorReanudar(){
        removeAllActionListeners(btnElegir);
        btnElegir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Jugador jugador = (Jugador) cbEleccion.getSelectedItem();

                    if(jugador != null){
                        frame.dispose();
                        controlador.setJugadorReanudar(jugador.getIDJugador());
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void procesarEliminarJugador(){
        removeAllActionListeners(btnEliminarJugador);
        btnEliminarJugador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaEliminarJugador();
            }
        });
    }

    @Override
    public void salir() {
        frame.setVisible(false);
    }

    @Override
    public void iniciar() {
        frame.setVisible(true);
    }

    @Override
    public void actualizarListaJugadores(ArrayList<Jugador> lista) {

        if(lista == null || lista.isEmpty()) listModel.addElement("¡No hay Jugadores creados aún!");

        else{

            listModel.clear();
            for (Jugador jugador: lista) {
                listModel.addElement(jugador.toString());
            }

            cbEleccion.removeAllItems();
            for (Jugador jugador: lista) {
                if (!jugador.getElecto()) {
                    cbEleccion.addItem(jugador);
                }
            }

        }
    }

    @Override
    public void setControlador(IControlador controlador) {
        this.controlador = controlador;
    }

    @Override
    public void mostrarMenuPrincipal() {
        actualizarListaJugadores(controlador.listaJugadoresMasGanadores());
        iniciar();
    }

    // pantalla para reanudar
    @Override
    public void reanudarPartida(ArrayList<Jugador> lista) {
        frame.setVisible(true);
        btnActualizarJugador.setEnabled(false);
        btnEliminarJugador.setEnabled(false);   // desabilito los botones porque no los necesito cuando reanudo
        btnCrearJugador.setEnabled(false);

        procesarEleccionJugadorReanudar(); // le actualizo el action listener al boton para que reanude

        if(lista.isEmpty()) listModel.addElement("¡Ya se eligieron todos los jugadores y la partida esta en curso! Tal vez se equivocó de partida...;)");

        else{
            listModel.clear();
            for (Jugador jugador: lista) {
                listModel.addElement(jugador.toString());
            }

            cbEleccion.removeAllItems();
            for (Jugador jugador: lista) {
                if (!jugador.getElecto()) {
                    cbEleccion.addItem(jugador);
                }
            }

        }

    }

    private void panelAvisos(String text){
        JFrame frameMSJ;
        frameMSJ = new JFrame("TRUCONTARDI");
        frameMSJ.setSize(400, 100);
        JPanel panelPrincipal = (JPanel) frameMSJ.getContentPane();
        panelPrincipal.setLayout(new BorderLayout());

        JLabel etiqueta1 = new JLabel(text);
        panelPrincipal.add(etiqueta1, BorderLayout.CENTER);

        frameMSJ.setVisible(true);
        frameMSJ.setLocationRelativeTo(null);
    }


    //
    // metodos privados
    //


    private void removeAllActionListeners(AbstractButton button) {
        ActionListener[] listeners = button.getActionListeners();
        for (ActionListener listener : listeners) {
            button.removeActionListener(listener);
        }
    }

    private void ventanaActualizarJugador(){
        JFrame frame2 = new JFrame("ACTUALIZAR JUGADOR");
        frame2.setSize(445, 300);
        frame2.setLayout(new BorderLayout());
        frame2.setResizable(false);
        JComboBox<Jugador> comboBox = new JComboBox<>();

        ArrayList<Jugador> lista = controlador.listaJugadoresMasGanadores();
        for(Jugador j : lista){
            comboBox.addItem(j);
        }

        JButton btnSeleccionar = new JButton("SELECCIONAR");
        JPanel panelCentro = new JPanel();
        JPanel panelCentro2 = new JPanel();
        panelCentro2.setLayout(new GridBagLayout());
        panelCentro.setLayout(new BorderLayout());
        panelCentro.add(comboBox, BorderLayout.NORTH);
        panelCentro.add(panelCentro2, BorderLayout.CENTER);
        panelCentro2.add(btnSeleccionar);

        // Agregar el panel al centro del marco
        frame2.add(panelCentro, BorderLayout.CENTER);

        // Centrar la ventana en la pantalla
        frame2.setLocationRelativeTo(null);

        // Hacer la ventana visible
        frame2.setVisible(true);

        btnSeleccionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Jugador jugador = (Jugador) comboBox.getSelectedItem();

                if(jugador != null){
                    nombreJugador = JOptionPane.showInputDialog(frame, "Por favor, ingresa el nombre:", "TRUCONTARDI - ACTUALIZAR JUGADOR", JOptionPane.QUESTION_MESSAGE);
                    controlador.actualizarJugador(jugador.getIDJugador(), nombreJugador);
                    frame2.dispose();
                }
            }
        });

    }


    private void ventanaEliminarJugador() {
        JFrame frame2 = new JFrame("ELIMINAR JUGADOR");
        frame2.setSize(445, 300);
        frame2.setLayout(new BorderLayout());
        frame2.setResizable(false);
        JComboBox<Jugador> comboBox = new JComboBox<>();

        // Agregar jugadores al comboBox
        ArrayList<Jugador> lista = controlador.listaJugadoresMasGanadores();
        for (Jugador j : lista) {
            comboBox.addItem(j);
        }

        // Crear el botón
        JButton btnSeleccionar = new JButton("SELECCIONAR");
        JPanel panelCentro = new JPanel();
        JPanel panelCentro2 = new JPanel();
        panelCentro2.setLayout(new GridBagLayout());
        panelCentro.setLayout(new BorderLayout());
        panelCentro.add(comboBox, BorderLayout.NORTH);
        panelCentro.add(panelCentro2, BorderLayout.CENTER);
        panelCentro2.add(btnSeleccionar);

        // Agregar el panel al centro del marco
        frame2.add(panelCentro, BorderLayout.CENTER);

        // Centrar la ventana en la pantalla
        frame2.setLocationRelativeTo(null);

        // Hacer visible el marco
        frame2.setVisible(true);

        // Agregar ActionListener al botón
        btnSeleccionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el jugador seleccionado
                Jugador jugador = (Jugador) comboBox.getSelectedItem();
                if (jugador != null) {
                    // Mostrar cuadro de confirmación
                    int respuesta = JOptionPane.showConfirmDialog(
                            frame2,
                            "¿Seguro que quieres eliminar a " + jugador.getNombre() + "?",
                            "Confirmación",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

                    // Acciones según la respuesta
                    if (respuesta == JOptionPane.YES_OPTION) {
                        controlador.eliminarJugador(jugador.getIDJugador());
                        JOptionPane.showMessageDialog(frame2, "Jugador eliminado exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(frame2, "Eliminación cancelada.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame2, "Por favor, selecciona un jugador.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void setJMenubar() {
        JMenuBar mnuPrincipal = new JMenuBar();
        JMenu mnuArchivo = new JMenu("Opciones");
        mnuPrincipal.add(mnuArchivo);
        JMenuItem mnuiSalir = new JMenuItem("Volver al menu principal");

        mnuiSalir.addActionListener(e ->{
            new VistaInicio().iniciar();
            frame.dispose();
        });

        mnuArchivo.add(mnuiSalir);
        frame.setJMenuBar(mnuPrincipal);
    }

}
