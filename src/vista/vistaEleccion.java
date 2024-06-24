package vista;

import interfaces.IControlador;
import interfaces.IVistaEleccion;
import modelo.Jugador;
import modelo.Partida;

import javax.swing.*;
import java.util.ArrayList;

public class vistaEleccion implements IVistaEleccion {
    private JPanel ventana;
    private JTabbedPane tabbedPane1;
    private JPanel JUGADORES;
    private JPanel CREARNUEVO;
    private JList list1;
    private JComboBox comboBox1;
    private JButton ELEGIRButton;
    private JTextField textRegistrar;
    private JButton CREARJUGADORButton;
    private final JFrame frame;
    private IControlador controlador;

    public vistaEleccion() {
        this.frame = new JFrame("TRUCONTARDI");
        frame.setContentPane(ventana);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 450);
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

    }

    @Override
    public void actualizarListaPartidas(ArrayList<Partida> lista) {

    }

    @Override
    public void setControlador(IControlador controlador) {
        this.controlador = controlador;
    }


}
