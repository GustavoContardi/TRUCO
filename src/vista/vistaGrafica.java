package vista;

import javax.swing.*;

import interfaces.iControlador;
import interfaces.iVistaJuego;

import java.awt.*;
import java.awt.event.ActionListener;

public class vistaGrafica implements iVistaJuego{
    private JPanel ventana;
    private JButton btnEnvido;
    private JButton TRUCOButton;
    private JButton IRALMAZOButton;
    private JButton btnNoQuiero;
    private JPanel panelCartasTiradasYo;
    private JPanel panelCartasOp;
    private JPanel CartasOP1;
    private JPanel CartasOP2;
    private JPanel CartasOP3;
    private JPanel CartasYo1;
    private JPanel CartasYo2;
    private JPanel CartasYo3;
    private JButton btnQuiero;
    private JButton btnCarta1;
    private JButton btnCarta2;
    private JButton btnCarta3;
    private JLabel accionesJ2;
    private JLabel puntajesLabel;
    private JFrame frame;

    public vistaGrafica() {
        this.frame = new JFrame("TRUCONTARDI");
        frame.setContentPane(ventana);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 950);
        frame.setVisible(true);
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
    public void finDeLaPartida() {

    }

    @Override
    public void cantaronRabon(String rabon) {

    }

    @Override
    public void cantaronTanto(String tanto) {

    }

    @Override
    public void println(String text) {

    }

    @Override
    public void mostrarMenuPrincipal() {

    }

    @Override
    public void setControlador(iControlador controlador) {

    }

    @Override
    public void actualizar() {

    }

    public void setBotones(){

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

    /*private void removeBtnActionListener() {
        removeAllActionListeners(TRUCOButton);
        removeAllActionListeners(btnQuiero);
        removeAllActionListeners(btnNoQuiero);
        removeAllActionListeners(FLORButton);
        removeAllActionListeners(btnEnvido);
        removeAllActionListeners(IRALMAZOButton);
    }

     */

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

}
