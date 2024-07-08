package vista;

import controlador.Controlador;
import enums.EstadoEnvido;
import enums.EstadoTruco;
import interfaces.IControlador;
import interfaces.IVistaInicio;
import interfaces.IVistaJuego;
import vista.flujos.Flujo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class vistaConsola implements IVistaJuego, IVistaInicio {
    private JPanel ventana;
    private JButton btnEnter;
    private JTextField txtEntrada;
    private JTextArea txtVista;
    private final JFrame frame;
    private Flujo flujoActual;
    private IControlador controlador;

    public vistaConsola() {
        frame = new JFrame("TRUCONTARDI");
        frame.setContentPane(ventana);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 350);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        btnEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarEntrada(txtEntrada.getText());
                txtEntrada.setText("");
            }
        });

    }

    public void setFlujoActual(Flujo flujoActual) {
        this.flujoActual = flujoActual;
        flujoActual.mostrarSiguienteTexto();
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
        println("--------------------------");
        println("-     FIN DE LA MANO     -");
        println("--------------------------");
    }

    @Override
    public void finDeLaPartida(String nombreGanador) {
        limpiarPantalla();
        println("\n--------------------------------------------------------------------");
        println("        FIN DE LA PARTIDA");
        println("MUCHAS GRACIAS POR USAR LA APLICACION");
        println("--------------------------------------------------------------------");
        println("     ¡¡ GANADOR: " + nombreGanador + " !!");
        println("--------------------------------------------------------------------");
    }

    @Override
    public void cantaronRabon(String rabon, EstadoTruco estado) {

    }

    @Override
    public void cantaronTanto(String tanto, EstadoEnvido estado) {

    }

    @Override
    public void println(String text) {
        txtVista.append(text + "\n");
    }

    @Override
    public void mostrarMenuPrincipal() {

    }

    @Override
    public void setControlador(IControlador controlador) {
        this.controlador = controlador;
    }

    @Override
    public void actualizar() {

    }

    @Override
    public void salirDelJuego() {
        frame.dispose();
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
    public void mostrarAviso(String aviso) {
        println("-----------------------");
        println("AVISO: " + aviso);
        println("-----------------------");
    }

    @Override
    public void salir() {
        frame.setVisible(false);
    }


    //
    // metodos privados
    //

    private void procesarEntrada(String input) {
        input = input.trim();
        if (input.isEmpty()) {
            return;
        }
        flujoActual = flujoActual.procesarEntrada(input);
        flujoActual.mostrarSiguienteTexto();
    }

}
