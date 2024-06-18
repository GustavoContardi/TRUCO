package vista;

import javax.swing.*;

import interfaces.iControlador;
import interfaces.iVistaJuego;

import java.awt.*;
import java.awt.event.ActionEvent;
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
    private JToolBar toolBtn;
    private JButton btnAuxiliar;
    private JFrame frame;
    private iControlador controlador;

    public vistaGrafica() {
        this.frame = new JFrame("TRUCONTARDI");
        frame.setContentPane(ventana);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 950);
        frame.setVisible(true);
        setBotones();
    }


    @Override
    public void mostrarCartas() {

    }

    @Override
    public void actualizarPuntaje(String puntaje) {

    }

    @Override
    public void mostrarMensaje(String msj) {
        puntajesLabel.setText(msj);
    }

    @Override
    public void limpiarPantalla() {

    }

    @Override
    public void finDeMano() {

    }

    @Override
    public void finDeLaPartida(String nombreGanador) {
        panelAvisos( "LA PARTIDA TERMINO. EL GANADOR ES: " + nombreGanador);

        // demas cosas
    }

    @Override
    public void cantaronRabon(String rabon) {

    }

    @Override
    public void cantaronTanto(String tanto) {

    }

    @Override
    public void println(String text) {
        // nada aca, es para la vista consola
    }

    @Override
    public void mostrarMenuPrincipal() {

    }

    @Override
    public void setControlador(iControlador controlador) {
        this.controlador = controlador;
    }

    @Override
    public void actualizar() {

    }

    @Override
    public void salirDelJuego() {

    }

    public void setBotones(){
        removeBtnActionListener(); // remuevo todos los actions listeners para que no se acumulen y los vuelvo a poner
        btnEnvido.setText("    ENVIDO    ");
        TRUCOButton.setText("    TRUCO    ");
        btnQuiero.setText("    QUIERO    ");
        btnNoQuiero.setText("    NO QUIERO    ");
        IRALMAZOButton.setText("    IR AL MAZO    ");
        btnAuxiliar.setText("");
        btnAuxiliar.setEnabled(false);
        btnQuiero.setEnabled(false);
        btnNoQuiero.setEnabled(false);

        btnEnvido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBotonesEnvido();
            }
        });
    }

    public void setBotonesEnvido(){
        btnQuiero.setEnabled(false);
        btnNoQuiero.setEnabled(false);

        IRALMAZOButton.setText("   VOLVER   ");
        IRALMAZOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBotones();
            }
        });

        btnEnvido.setText("    ENVIDO    ");
        btnEnvido.setEnabled(true);
        btnEnvido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(controlador.esMiTurno()) {
                    controlador.cantarTanto(1);
                    controlador.seCantoEnvido();
                }
            }
        });

        TRUCOButton.setText("    REAL ENVIDO    ");
        TRUCOButton.setEnabled(true);
        TRUCOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(controlador.esMiTurno()) {
                    controlador.cantarTanto(3);
                    controlador.seCantoRealEnvido();
                }
            }
        });

        btnAuxiliar.setText("    FALTA ENVIDO    ");
        btnAuxiliar.setEnabled(true);
        btnAuxiliar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(controlador.esMiTurno()) {
                    controlador.cantarTanto(4);
                    controlador.seCantoFaltaEnvido();
                }
            }
        });


    }

    public void setBotonesCartas(){
        int ronda = controlador.nroDeRonda();

    }


    //
    // metodos privados
    //

    private void setBotonesCartas(JLabel labelImagen1, JLabel labelImagen2, JLabel labelImagen3){
        int ronda = controlador.nroDeRonda();

        removeAllActionListeners(btnCarta1);
        removeAllActionListeners(btnCarta2);
        removeAllActionListeners(btnCarta3);

        btnCarta1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(controlador.esMiTurno()) {

                    if(ronda == 1) {
                        CartasYo1.add(labelImagen1);
                        CartasYo1.revalidate();
                        CartasYo1.repaint();
                    }
                    else if(ronda == 2) {
                        CartasYo2.add(labelImagen1);
                        CartasYo2.revalidate();
                        CartasYo2.repaint();
                    }
                    else if(ronda == 3) {
                        CartasYo3.add(labelImagen1);
                        CartasYo3.revalidate();
                        CartasYo3.repaint();
                    }
                    btnCarta1.setEnabled(false);
                    controlador.tirarCarta(1);
                }
            }
        });

        btnCarta2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(controlador.esMiTurno()) {

                    if(ronda == 1) {
                        CartasYo1.add(labelImagen1);
                        CartasYo1.revalidate();
                        CartasYo1.repaint();
                    }
                    else if(ronda == 2) {
                        CartasYo2.add(labelImagen1);
                        CartasYo2.revalidate();
                        CartasYo2.repaint();
                    }
                    else if(ronda == 3) {
                        CartasYo3.add(labelImagen1);
                        CartasYo3.revalidate();
                        CartasYo3.repaint();
                    }
                    btnCarta2.setEnabled(false);
                    controlador.tirarCarta(2);
                }
            }
        });

        btnCarta3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(controlador.esMiTurno()) {
                    if(ronda == 1) {
                        CartasYo1.add(labelImagen1);
                        CartasYo1.revalidate();
                        CartasYo1.repaint();
                    }
                    else if(ronda == 2) {
                        CartasYo2.add(labelImagen1);
                        CartasYo2.revalidate();
                        CartasYo2.repaint();
                    }
                    else if(ronda == 3) {
                        CartasYo3.add(labelImagen1);
                        CartasYo3.revalidate();
                        CartasYo3.repaint();
                    }
                    btnCarta3.setEnabled(false);
                    controlador.tirarCarta(3);

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

    private void removeBtnActionListener() {
        removeAllActionListeners(TRUCOButton);
        removeAllActionListeners(btnQuiero);
        removeAllActionListeners(btnNoQuiero);
        removeAllActionListeners(btnEnvido);
        removeAllActionListeners(IRALMAZOButton);
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

}
