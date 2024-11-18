package vista;

import interfaces.IControlador;
import interfaces.IVistaEleccion;
import modelo.Jugador;
import modelo.Partida;
import servidor.ServidorTruco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class vistaEleccion implements IVistaEleccion {
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

    private DefaultListModel<String> listModel;

    public vistaEleccion() {
        this.frame = new JFrame("TRUCONTARDI");
        frame.setContentPane(ventana);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 450);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        listModel = new DefaultListModel<>();
        list.setModel(listModel);



    }

    public void iniciarRecuperacionPartida() throws RemoteException {// este metodo es para recuperar una partida y elegir uno de los dos jugadores asociados a ella
        procesarEleccionPartida();
    }

    public void iniciarNuevaPartida(){ // este metodo es para una nueva partida donde aparecen todos los jugadores
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    procesarAltaJugador();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnElegir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    procesarEleccionJugador();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void procesarAltaJugador() throws RemoteException {
        String nombre = textRegistrar.getText().trim();

        if(!nombre.isEmpty()){
            controlador.agregarJugador(nombre);
            textRegistrar.setText("");
            panelAvisos("¡JUGADOR REGISTRADO CON ÉXITO!");
        }
    }

    public void procesarEleccionJugador() throws RemoteException {
        Jugador jugador = (Jugador) cbEleccion.getSelectedItem();

        if(jugador != null){
            controlador.setJugador(jugador.getIDJugador());
            frame.dispose();
        }
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

        if(lista != null){

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
        else{
            listModel.addElement("¡No hay Jugadores creados aún!");
        }
    }

    @Override
    public void actualizarListaJugadoresUsados(ArrayList<Jugador> lista) {
        if(lista != null){

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
        else{
            listModel.addElement("¡No hay Jugadores creados aún!");
        }
    }

    @Override
    public void actualizarListaPartidas(ArrayList<Partida> lista) {
        if(lista != null){

            listModel.clear();
            for (Partida partida: lista) {
                listModel.addElement(partida.toString());
            }

            cbEleccion.removeAllItems();
            for (Partida partida: lista) {
                cbEleccion.addItem(partida);
            }

        }
        else{
            listModel.addElement("¡No hay Jugadores creados aún!");
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

    private void procesarEleccionPartida() throws RemoteException {
        btnRegistrar.setEnabled(false); // para que no pueda crear jugadores
        removeAllActionListeners(btnElegir);

        actualizarListaPartidas(controlador.getListaPartidasPendientes());

        btnElegir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Partida partida = (Partida) cbEleccion.getSelectedItem();

                if(partida != null){
                    new ServidorTruco(partida);
                    try {
                        eleccionJugadoresRecuperados();
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

    }

    private void eleccionJugadoresRecuperados() throws RemoteException {
        btnRegistrar.setEnabled(false); // para que no pueda crear jugadores
        removeAllActionListeners(btnElegir);

        actualizarListaJugadores(controlador.getJugadoresRecuperados());

        btnElegir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    procesarEleccionJugador();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }


    private void removeAllActionListeners(AbstractButton button) {
        ActionListener[] listeners = button.getActionListeners();
        for (ActionListener listener : listeners) {
            button.removeActionListener(listener);
        }
    }
}
