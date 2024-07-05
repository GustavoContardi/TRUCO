package vista;

import enums.EstadoEnvido;
import enums.EstadoTruco;
import interfaces.IControlador;
import interfaces.IVistaInicio;
import interfaces.IVistaJuego;

import javax.swing.*;
import java.awt.*;

public class vistaConsola implements IVistaJuego, IVistaInicio {
    private JPanel ventana;
    private JButton btnEnter;
    private JTextField txtEntrada;
    private JTextArea txtVista;
    private final JFrame frame;

    public vistaConsola() {
        frame = new JFrame("TRUCONTARDI");
        frame.setContentPane(ventana);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

    }


    @Override
    public void mostrarCartas() {

    }

    @Override
    public void actualizarPuntaje(String puntaje) {

    }

    @Override
    public void mostrarMensaje(String msj) {

    }

    @Override
    public void limpiarPantalla() {

    }

    @Override
    public void finDeMano() {

    }

    @Override
    public void finDeLaPartida(String nombreGanador) {

    }

    @Override
    public void cantaronRabon(String rabon, EstadoTruco estado) {

    }

    @Override
    public void cantaronTanto(String tanto, EstadoEnvido estado) {

    }

    @Override
    public void println(String text) {

    }

    @Override
    public void mostrarMenuPrincipal() {

    }

    @Override
    public void setControlador(IControlador controlador) {

    }

    @Override
    public void actualizar() {

    }

    @Override
    public void salirDelJuego() {

    }

    @Override
    public void meTiraronCarta(String carta) {

    }

    @Override
    public void tirarCarta() {

    }

    @Override
    public void iniciar() {
        frame.setVisible(true);
    }

    @Override
    public void salir() {
        frame.setVisible(false);
    }
}
