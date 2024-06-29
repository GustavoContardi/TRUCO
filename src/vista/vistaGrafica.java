package vista;

import javax.swing.*;

import interfaces.IControlador;
import interfaces.IVistaJuego;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class vistaGrafica implements IVistaJuego {
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
    private IControlador controlador;

    public vistaGrafica() {
        this.frame = new JFrame("TRUCONTARDI");
        frame.setContentPane(ventana);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 950);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        setBotones();

    }


    @Override
    public void mostrarCartas() throws RemoteException {
        String basePath = "fotocartas/";
        int ronda = controlador.nroDeRonda();
        btnCarta1.removeAll();
        btnCarta2.removeAll();
        btnCarta3.removeAll();
        removeAllActionListeners(btnCarta1);
        removeAllActionListeners(btnCarta2);
        removeAllActionListeners(btnCarta3);

        ArrayList<String> cartas = controlador.obtenerCartas();

        if (cartas != null && !cartas.isEmpty()) {
            String carta1 = cartas.get(0).replace(" ", "").toLowerCase();
            String carta2 = cartas.get(1).replace(" ", "").toLowerCase();
            String carta3 = cartas.get(2).replace(" ", "").toLowerCase();

            System.out.println(carta1 + " " + carta2 + " " + carta3);

            ImageIcon imagen1 = new ImageIcon(basePath + carta1 + ".jpeg");
            ImageIcon imagen2 = new ImageIcon(basePath + carta2 + ".jpeg");
            ImageIcon imagen3 = new ImageIcon(basePath + carta3 + ".jpeg");

            btnCarta1.setIcon(imagen1);
            btnCarta2.setIcon(imagen2);
            btnCarta3.setIcon(imagen3);

            setBotonesCartas(imagen1, imagen2, imagen3);
            setBotones();
        }
        else {
            accionesJ2.setText("Esperando contrincante...");
        }



    }

    @Override
    public void actualizarPuntaje(String puntaje) {
        puntajesLabel.setText(puntaje);
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
    public void cantaronRabon(String rabon) throws RemoteException {
        removeBtnActionListener();

        accionesJ2.setText(rabon);
        btnQuiero.setEnabled(true);
        btnNoQuiero.setEnabled(true);

        btnQuiero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //controlador.rabonQuerido();
                accionesJ2.setText("");
                setBotones();
            }
        });

        btnNoQuiero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    accionesJ2.setText("");
                    controlador.cantoNoQuerido();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        switch(controlador.estadoDelRabon()){
            case TRUCO -> {
                TRUCOButton.setEnabled(true);
                TRUCOButton.setText(" RE TRUCO ");
                TRUCOButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            controlador.cantarRabon(2);
                            accionesJ2.setText("");
                            setBotones();
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
            case RE_TRUCO -> {
                TRUCOButton.setEnabled(true);
                TRUCOButton.setText(" VALE CUATRO ");
                TRUCOButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            controlador.cantarRabon(3);
                            accionesJ2.setText("");
                            setBotones();
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }

            case VALE_CUATRO -> {
                TRUCOButton.setText("");
                TRUCOButton.setEnabled(false);
                // no se puede cantar más
            }
        }


    }

    @Override
    public void cantaronTanto(String tanto) throws RemoteException {
        removeBtnActionListener();

        accionesJ2.setText(tanto);
        btnQuiero.setEnabled(true);
        btnNoQuiero.setEnabled(true);

        btnQuiero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accionesJ2.setText("");
                try {
                    controlador.tantoQuerido();
                    setBotones();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnNoQuiero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accionesJ2.setText("");
                try {
                    controlador.tantoNoQuerido();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        switch (controlador.estadoDelTanto()){
            case ENVIDO -> {
                btnEnvido.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            controlador.cantarTanto(2);
                            accionesJ2.setText("");
                            setBotones();
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                TRUCOButton.setText("REAL ENVIDO");
                TRUCOButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            controlador.cantarTanto(3);
                            accionesJ2.setText("");
                            setBotones();
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                btnAuxiliar.setText("FALTA ENVIDO");
                btnAuxiliar.setEnabled(true);
                btnAuxiliar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            controlador.cantarTanto(4);
                            accionesJ2.setText("");
                            setBotones();
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }

            case ENVIDO_DOBLE -> {
                btnEnvido.setEnabled(false);
                btnEnvido.setText(" ---- ");
                TRUCOButton.setText("REAL ENVIDO");
                TRUCOButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            controlador.cantarTanto(3);
                            accionesJ2.setText("");
                            setBotones();
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                btnAuxiliar.setText("FALTA ENVIDO");
                btnAuxiliar.setEnabled(true);
                btnAuxiliar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            controlador.cantarTanto(4);
                            accionesJ2.setText("");
                            setBotones();
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
            case REAL_ENVIDO -> {
                btnEnvido.setText(" ---- ");
                btnEnvido.setEnabled(false);
                btnAuxiliar.setText("FALTA ENVIDO");
                btnAuxiliar.setEnabled(true);
                btnAuxiliar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            controlador.cantarTanto(4);
                            accionesJ2.setText("");
                            setBotones();
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }

            case FALTA_ENVIDO -> {
                btnEnvido.setEnabled(false);
                btnAuxiliar.setEnabled(false);
                TRUCOButton.setEnabled(false);
                btnEnvido.setText(" ---- ");
                TRUCOButton.setText(" ---- ");
                btnAuxiliar.setText(" ---- ");
            }
        }
    }

    @Override
    public void println(String text) {
        // nada aca, es para la vista consola
    }

    @Override
    public void mostrarMenuPrincipal() throws RemoteException {
        iniciar();
        mostrarCartas();
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

    public void setBotones(){
        removeBtnActionListener(); // remuevo todos los actions listeners para que no se acumulen y los vuelvo a poner
        btnEnvido.setText("    ENVIDO    ");
        TRUCOButton.setText("    TRUCO    ");
        btnQuiero.setText("    QUIERO    ");
        btnNoQuiero.setText("    NO QUIERO    ");
        IRALMAZOButton.setText("    IR AL MAZO    ");
        btnAuxiliar.setText("");
        accionesJ2.setText("");
        btnAuxiliar.setEnabled(false);
        btnQuiero.setEnabled(false);
        btnNoQuiero.setEnabled(false);

        btnEnvido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBotonesEnvido();
            }
        });

        // terminar para los demas botones

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
                try {
                    if(controlador.esMiTurno()) {
                        try {
                            controlador.cantarTanto(1);
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }
                        controlador.seCantoEnvido();
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        TRUCOButton.setText("    REAL ENVIDO    ");
        TRUCOButton.setEnabled(true);
        TRUCOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(controlador.esMiTurno()) {
                        try {
                            controlador.cantarTanto(3);
                            controlador.seCantoRealEnvido();
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnAuxiliar.setText("    FALTA ENVIDO    ");
        btnAuxiliar.setEnabled(true);
        btnAuxiliar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(controlador.esMiTurno()) {
                        try {
                            controlador.cantarTanto(4);
                            controlador.seCantoFaltaEnvido();
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });


    }

    public void setImagenesCartas(String fotoCarta) throws RemoteException {
        int ronda = controlador.nroDeRonda();

        if(ronda == 1){
            ImageIcon imagen1 = new ImageIcon("fotocartas\\" + fotoCarta + ".jpeg");
            JLabel label1 = new JLabel(imagen1);
            btnCarta1.add(label1);
            btnCarta1.revalidate();
            btnCarta1.repaint();
        }
        if(ronda == 2){
            ImageIcon imagen2 = new ImageIcon("fotocartas\\" + fotoCarta + ".jpeg");
            JLabel label2 = new JLabel(imagen2);
            btnCarta2.add(label2);
            btnCarta2.revalidate();
            btnCarta2.repaint();
        }
        if(ronda == 3) {
            ImageIcon imagen3 = new ImageIcon("fotocartas\\" + fotoCarta + ".jpeg");
            JLabel label3 = new JLabel(imagen3);
            btnCarta3.add(label3);
            btnCarta3.revalidate();
            btnCarta3.repaint();
        }
    }


    //
    // metodos privados
    //

    private void setBotonesAccionesCartas(JLabel labelImagen1, JLabel labelImagen2, JLabel labelImagen3) throws RemoteException {
        int ronda = controlador.nroDeRonda();

        removeAllActionListeners(btnCarta1);
        removeAllActionListeners(btnCarta2);
        removeAllActionListeners(btnCarta3);

        btnCarta1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
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
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnCarta2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
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
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnCarta3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
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

    private void bloquearBotones(){
        // bloqueo los botones cuando me cantan para que responda antes de poder tirar

    }

    private void setBotonesCartas(ImageIcon imagen1, ImageIcon imagen2, ImageIcon imagen3) throws RemoteException {
        int ronda = controlador.nroDeRonda();

        removeAllActionListeners(btnCarta1);
        removeAllActionListeners(btnCarta2);
        removeAllActionListeners(btnCarta3);

        btnCarta1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (controlador.esMiTurno()) {
                        if (ronda == 1) {
                            CartasYo1.add(new JLabel(imagen1));
                        } else if (ronda == 2) {
                            CartasYo2.add(new JLabel(imagen1));
                        } else if (ronda == 3) {
                            CartasYo3.add(new JLabel(imagen1));
                        }
                        CartasYo1.revalidate();
                        CartasYo1.repaint();
                        btnCarta1.setEnabled(false);
                        controlador.tirarCarta(1);
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnCarta2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (controlador.esMiTurno()) {
                        if (ronda == 1) {
                            CartasYo1.add(new JLabel(imagen2));
                        } else if (ronda == 2) {
                            CartasYo2.add(new JLabel(imagen2));
                        } else if (ronda == 3) {
                            CartasYo3.add(new JLabel(imagen2));
                        }
                        CartasYo1.revalidate();
                        CartasYo1.repaint();
                        btnCarta2.setEnabled(false);
                        controlador.tirarCarta(2);
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnCarta3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (controlador.esMiTurno()) {
                        if (ronda == 1) {
                            CartasYo1.add(new JLabel(imagen3));
                        } else if (ronda == 2) {
                            CartasYo2.add(new JLabel(imagen3));
                        } else if (ronda == 3) {
                            CartasYo3.add(new JLabel(imagen3));
                        }
                        CartasYo1.revalidate();
                        CartasYo1.repaint();
                        btnCarta3.setEnabled(false);
                        controlador.tirarCarta(3);
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
