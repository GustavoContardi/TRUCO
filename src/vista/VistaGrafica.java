package vista;

import javax.imageio.ImageIO;
import javax.swing.*;

import enums.EstadoEnvido;
import enums.EstadoFlor;
import enums.EstadoTruco;
import interfaces.IControlador;
import interfaces.IVistaJuego;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

import static enums.EstadoEnvido.*;
import static enums.EstadoTruco.*;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class VistaGrafica implements IVistaJuego, Serializable {
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

    public VistaGrafica() throws RemoteException {
        this.frame = new JFrame("TRUCONTARDI");
        frame.setContentPane(ventana);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setSize(730, 820);
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);


        // Control de cierre de ventana
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Mostrar el cuadro de confirmación
                int response = JOptionPane.showConfirmDialog(
                        frame,
                        "¿Estás seguro de que quieres cerrar la ventana? La partida será guardada.",
                        "Confirmar Cierre",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (response == JOptionPane.YES_OPTION) {
                    // Cierra la ventana si el usuario confirma
                    frame.dispose();
                } else {
                    // Evita cualquier acción adicional si selecciona "No"
                    System.out.println("El usuario canceló el cierre de la ventana.");
                }
            }
        });
    }


    @Override
    public void mostrarCartas() throws RemoteException {
        String basePath = "fotocartas/";
        int ronda = controlador.nroDeRonda();
        String carta1="", carta2="", carta3="";

        btnCarta1.removeAll();
        btnCarta2.removeAll();
        btnCarta3.removeAll();
        removeAllActionListeners(btnCarta1);
        removeAllActionListeners(btnCarta2);
        removeAllActionListeners(btnCarta3);


        ArrayList<String> cartas = controlador.obtenerTodasLasCartas();

        if (cartas != null) {
            if(cartas.get(0) != null) carta1 = cartas.get(0).replace(" ", "").toLowerCase();
            if(cartas.get(1) != null) carta2 = cartas.get(1).replace(" ", "").toLowerCase();
            if(cartas.get(2) != null) carta3 = cartas.get(2).replace(" ", "").toLowerCase();

            String imagen1 = basePath + carta1 + ".jpeg";
            String imagen2 = basePath + carta2 + ".jpeg";
            String imagen3 = basePath + carta3 + ".jpeg";

            setButtonImage(btnCarta1, imagen1, 200, 220);
            setButtonImage(btnCarta2, imagen2, 200, 220);
            setButtonImage(btnCarta3, imagen3, 200, 220);

            actualizar();
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
    public void finDeMano() throws RemoteException {
        panelAvisos("Fin de la mano");

        CartasOP1.removeAll();
        CartasOP1.revalidate();
        CartasOP1.repaint();
        CartasOP2.removeAll();
        CartasOP2.revalidate();
        CartasOP2.repaint();
        CartasOP3.removeAll();
        CartasOP3.revalidate();
        CartasOP3.repaint();

        CartasYo1.removeAll();
        CartasYo1.revalidate();
        CartasYo1.repaint();
        CartasYo2.removeAll();
        CartasYo2.revalidate();
        CartasYo2.repaint();
        CartasYo3.removeAll();
        CartasYo3.revalidate();
        CartasYo3.repaint();

        btnCarta1.setIcon(null);
        btnCarta2.setIcon(null);
        btnCarta3.setIcon(null);

        eliminarTodosAL();

        btnCarta1.setEnabled(true);
        btnCarta2.setEnabled(true);
        btnCarta3.setEnabled(true);
    }

    @Override
    public void finDeLaPartida(String nombreGanador) {
        panelAvisos( "LA PARTIDA TERMINO. EL GANADOR ES: " + nombreGanador);

        removeBtnActionListener();
        eliminarTodosAL();

        CartasOP1.removeAll();
        CartasOP1.revalidate();
        CartasOP1.repaint();
        CartasOP2.removeAll();
        CartasOP2.revalidate();
        CartasOP2.repaint();
        CartasOP3.removeAll();
        CartasOP3.revalidate();
        CartasOP3.repaint();

        CartasYo1.removeAll();
        CartasYo1.revalidate();
        CartasYo1.repaint();
        CartasYo2.removeAll();
        CartasYo2.revalidate();
        CartasYo2.repaint();
        CartasYo3.removeAll();
        CartasYo3.revalidate();
        CartasYo3.repaint();

        btnCarta1.setIcon(null);
        btnCarta2.setIcon(null);
        btnCarta3.setIcon(null);

        accionesJ2.setText("Presione cualquier boton para volver al menu..");
        btnEnvido.setEnabled(true);
        btnAuxiliar.setEnabled(true);
        TRUCOButton.setEnabled(true);
        btnEnvido.setText("  VOLVER  ");
        TRUCOButton.setText("  AL  ");
        btnAuxiliar.setText("  INICIO  ");

        btnEnvido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controlador.volverAlMenuPrincipal();
                    frame.dispose();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        TRUCOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controlador.volverAlMenuPrincipal();
                    frame.dispose();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnAuxiliar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controlador.volverAlMenuPrincipal();
                    frame.dispose();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
        IRALMAZOButton.setEnabled(false);

    }

    @Override
    public void cantaronRabon(String rabon, EstadoTruco estado) throws RemoteException {
        removeBtnActionListener();

        accionesJ2.setText(rabon);
        btnEnvido.setEnabled(false);
        btnQuiero.setEnabled(true);
        btnNoQuiero.setEnabled(true);

        btnQuiero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controlador.rabonQuerido();
                    accionesJ2.setText("");
                    setBotones();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnNoQuiero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    accionesJ2.setText("");
                    controlador.rabonNoQuerido();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        switch(estado){
            case TRUCO -> {
                TRUCOButton.setEnabled(true);
                TRUCOButton.setText(" RE TRUCO ");
                TRUCOButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            controlador.cantarRabon(RE_TRUCO);
                            accionesJ2.setText("");
                            TRUCOButton.setEnabled(false);
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
                            controlador.cantarRabon(VALE_CUATRO);
                            accionesJ2.setText("");
                            TRUCOButton.setEnabled(false);
                            setBotones();
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }

            case VALE_CUATRO -> {
                TRUCOButton.setText(" - - - ");
                TRUCOButton.setEnabled(false);
                TRUCOButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            controlador.rabonQuerido();
                            setBotones();
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }
                    }
                });

                // no se puede cantar más
            }
        }


    }

    @Override
    public void cantaronTanto(String tanto, EstadoEnvido estado) throws RemoteException {
        removeBtnActionListener();

        accionesJ2.setText(tanto);
        btnQuiero.setEnabled(true);
        btnNoQuiero.setEnabled(true);

        btnQuiero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accionesJ2.setText("");
                try {
                    setBotones();
                    controlador.tantoQuerido();
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
                    setBotones();
                    controlador.tantoNoQuerido();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        switch (estado){
            case ENVIDO -> {
                btnEnvido.setEnabled(true);
                btnEnvido.setText(" ENVIDO ");
                btnEnvido.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            controlador.cantarTanto(ENVIDO_DOBLE);
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
                            controlador.cantarTanto(REAL_ENVIDO);
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
                            controlador.cantarTanto(FALTA_ENVIDO);
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
                            controlador.cantarTanto(REAL_ENVIDO);
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
                            controlador.cantarTanto(FALTA_ENVIDO);
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
                TRUCOButton.setEnabled(false);
                btnAuxiliar.setText("FALTA ENVIDO");
                btnAuxiliar.setEnabled(true);
                btnAuxiliar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            controlador.cantarTanto(FALTA_ENVIDO);
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
    public void cantaronFlor(String flor, EstadoFlor estado) throws RemoteException {
        removeBtnActionListener();

        btnEnvido.setText("         ");
        IRALMAZOButton.setText("         ");
        TRUCOButton.setText("         ");
        btnAuxiliar.setText("         ");
        btnQuiero.setEnabled(false);
        btnNoQuiero.setEnabled(false);
        btnAuxiliar.setEnabled(false);
        IRALMAZOButton.setEnabled(false);
        btnEnvido.setEnabled(true);
        TRUCOButton.setEnabled(true);

        accionesJ2.setText(flor);

        // a los botones de 'Quiero' y 'NoQuiero' los dejo seteados y solo los activo y desactivo segun lo necesite
        btnQuiero.addActionListener(e -> {
            try {
                controlador.florQuerida(estado);
                setBotones();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });

        btnNoQuiero.addActionListener(e -> {
            try {
                controlador.noQuieroFlor(estado);
                setBotones();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });

        switch (estado){
            case FLOR -> {
                if(controlador.tengoFlor()){
                    btnEnvido.setText("CONTRA FLOR");
                    btnEnvido.addActionListener( e -> {
                        try {
                            setBotones();
                            controlador.cantarContraFlor();
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }
                    });
                    TRUCOButton.setText("CONTRA FLOR AL RESTO");
                    TRUCOButton.addActionListener( e -> {
                        try {
                            setBotones();
                            controlador.cantarContraFlorAlResto();
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }
                    });
                }
                else{
                    mostrarAviso("Tu rival te cantó FLOR y tú no tienes. Son 3 puntos para tu rival.");
                    setBotones();
                }
            }
            case CONTRA_FLOR -> {
                btnEnvido.setEnabled(false);
                TRUCOButton.setText("CONTRA FLOR AL RESTO");
                TRUCOButton.addActionListener( e -> {
                    try {
                        controlador.cantarContraFlorAlResto();
                        setBotones();
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                });

                btnQuiero.setEnabled(true);
                btnNoQuiero.setEnabled(true);
            }
            case CONTRA_FLOR_AL_RESTO -> {
                btnQuiero.setEnabled(true);
                btnNoQuiero.setEnabled(true);
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
        if(controlador != null) {
            frame.setTitle(" APP TRUCO - " + controlador.getNombreJugador());
        }
        mostrarCartas();
    }


    @Override
    public void setControlador(IControlador controlador) {
        this.controlador = controlador;
    }

    @Override
    public void actualizar() throws RemoteException {
        String basePath = "fotocartas/";
        int ronda = controlador.nroDeRonda();
        btnCarta1.removeAll();
        btnCarta2.removeAll();
        btnCarta3.removeAll();
        removeAllActionListeners(btnCarta1);
        removeAllActionListeners(btnCarta2);
        removeAllActionListeners(btnCarta3);
        String imagen1 = "", imagen2 = "", imagen3 = "";

        ArrayList<String> cartas = controlador.obtenerCartas();

        if (cartas != null && !cartas.isEmpty()) {
            String carta1 = cartas.get(0).replace(" ", "").toLowerCase();
            String carta2 = cartas.get(1).replace(" ", "").toLowerCase();
            String carta3 = cartas.get(2).replace(" ", "").toLowerCase();

            imagen1 = basePath + carta1 + ".jpeg";
            imagen2 = basePath + carta2 + ".jpeg";
            imagen3 = basePath + carta3 + ".jpeg";

        }

        setBotonesCartas(imagen1, imagen2, imagen3);
        setBotones();
    }

    @Override
    public void salirDelJuego() {

    }

    @Override
    public void meTiraronCarta(String carta) throws RemoteException {

        int ronda = controlador.nroDeRonda();
        String basePath = "fotocartas/";
        String carta1 = carta.replace(" ", "").toLowerCase();
        String img = basePath + carta1 + ".jpeg";
        ImageIcon imageIcon = createResizedImageIcon(img, 160, 170);

        JLabel label = new JLabel(imageIcon);

        if(ronda == 1){
            CartasOP1.add(label);
            CartasOP1.revalidate();
            CartasOP1.repaint();
        }
        else if(ronda == 2){
            CartasOP2.add(label);
            CartasOP2.revalidate();
            CartasOP2.repaint();
        }
        else if (ronda == 3){
            CartasOP3.add(label);
            CartasOP3.revalidate();
            CartasOP3.repaint();
        }

        setBotones();

    }

    @Override
    public void tirarCarta(int posCarta) {

    }

    @Override
    public void iniciar() {
        frame.setVisible(true);
    }

    @Override
    public void mostrarAviso(String aviso) {
        panelAvisos(aviso);
    }

    @Override
    public void reanudarPartida() throws RemoteException {
        mostrarCartas();
        mostrarCartasTiradas();
        actualizarPuntaje(controlador.puntajeActual());
    }

    @Override
    public void mostrarEsperaRival() throws RemoteException {
        iniciar();
        accionesJ2.setText("SE HA REANUDADO LA PARTIDA CORRECTAMENTE. NO PODRÁ JUGAR HASTA QUE SU RIVAL INICIE SESION");
    }

    public void setBotones() throws RemoteException {
        removeBtnActionListener(); // remuevo todos los actions listeners para que no se acumulen y los vuelvo a poner
        btnEnvido.setText("    ENVIDO    ");
        btnQuiero.setText("    QUIERO    ");
        btnNoQuiero.setText("    NO QUIERO    ");
        IRALMAZOButton.setText("    IR AL MAZO    ");
        btnAuxiliar.setText("");
        accionesJ2.setText("");
        btnAuxiliar.setEnabled(false);
        btnQuiero.setEnabled(false);
        btnNoQuiero.setEnabled(false);

        if(controlador.seCantoEnvido() || controlador.nroDeRonda() > 1) btnEnvido.setEnabled(false);
        else btnEnvido.setEnabled(true);

        btnEnvido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(!controlador.seCantoEnvido()) setBotonesEnvido();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        TRUCOButton.setEnabled(true);
        switch (controlador.estadoDelRabon()){
            case NADA -> {
                TRUCOButton.setEnabled(true);
                TRUCOButton.setText("  TRUCO  ");
            }
            case TRUCO -> {
                TRUCOButton.setEnabled(true);
                TRUCOButton.setText(" RE TRUCO  ");
            }                                               // activo todos los botones
            case RE_TRUCO -> {
                TRUCOButton.setEnabled(true);
                TRUCOButton.setText(" VALE CUATRO ");
            }
            case VALE_CUATRO -> {
                TRUCOButton.setText(" -- ");
                TRUCOButton.setEnabled(false);
            }
        }
        TRUCOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    switch (controlador.estadoDelRabon()){
                        case NADA -> {
                            if(controlador.esMiTurno() && controlador.puedoCantarTruco(TRUCO)){
                                TRUCOButton.setText("  RE TRUCO  ");
                                TRUCOButton.setEnabled(false);
                                controlador.cantarRabon(TRUCO);
                                setBotones();
                            }
                        }
                        case TRUCO -> {
                            if(controlador.esMiTurno() && controlador.puedoCantarTruco(RE_TRUCO)){

                                TRUCOButton.setText("  VALE CUATRO  ");
                                TRUCOButton.setEnabled(false);
                                controlador.cantarRabon(RE_TRUCO);
                                setBotones();
                            }
                        }
                        case RE_TRUCO -> {
                            if(controlador.esMiTurno() && controlador.puedoCantarTruco(RE_TRUCO)){

                                TRUCOButton.setText("  ---  ");
                                TRUCOButton.setEnabled(false);
                                controlador.cantarRabon(VALE_CUATRO);
                                setBotones();
                            }
                        }
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                };
            }
        });

        IRALMAZOButton.setText(" IR AL MAZO ");
        IRALMAZOButton.setEnabled(true);
        IRALMAZOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controlador.meVoyAlMazo();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // terminar para los demas botones

    }

    public void setBotonesEnvido() throws RemoteException {
        btnQuiero.setEnabled(false);
        removeBtnActionListener();

        IRALMAZOButton.setText("   VOLVER   ");
        IRALMAZOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setBotones();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnEnvido.setText("    ENVIDO    ");
        btnEnvido.setEnabled(true);
        btnEnvido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(controlador.esMiTurno()) {
                        controlador.cantarTanto(ENVIDO);
                        btnEnvido.setEnabled(false);
                        setBotones();
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
                        controlador.cantarTanto(REAL_ENVIDO);
                        btnEnvido.setEnabled(false);
                        setBotones();
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
                        controlador.cantarTanto(FALTA_ENVIDO);
                        btnEnvido.setEnabled(false);
                        setBotones();
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
        if(controlador.tengoFlor() && controlador.seJuegaConFlor()){
            btnNoQuiero.setEnabled(true);
            btnNoQuiero.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        controlador.cantarFlor();
                        btnNoQuiero.setEnabled(false);
                        setBotones();
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
        else btnNoQuiero.setEnabled(false);

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

    private void removeBtnActionListener() {
        removeAllActionListeners(TRUCOButton);
        removeAllActionListeners(btnQuiero);
        removeAllActionListeners(btnNoQuiero);
        removeAllActionListeners(btnEnvido);
        removeAllActionListeners(IRALMAZOButton);
    }


    private void panelAvisos(String text){
        JFrame frameMSJ;
        frameMSJ = new JFrame("APP TRUCO - AVISO");
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

        removeAllActionListeners(btnCarta1);
        removeAllActionListeners(btnCarta2);
        removeAllActionListeners(btnCarta3);

    }

    private void setBotonesCartas(String imagen1, String imagen2, String imagen3) throws RemoteException {
        int ronda = controlador.nroDeRonda();

        removeAllActionListeners(btnCarta1);
        removeAllActionListeners(btnCarta2);
        removeAllActionListeners(btnCarta3);

        btnCarta1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (controlador.esMiTurno()) {
                        mostrarCartaTiradaYO(imagen1);
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
                        mostrarCartaTiradaYO(imagen2);
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
                        mostrarCartaTiradaYO(imagen3);
                        btnCarta3.setEnabled(false);
                        controlador.tirarCarta(3);
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void setButtonImage(JButton button, String imagePath, int width, int height) {
        try {
            // Load the image
            BufferedImage img = ImageIO.read(new File(imagePath));
            // Resize the image to fit the button
            Image resizedImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            // Set the resized image as the icon of the button
            button.setIcon(new ImageIcon(resizedImage));
            // Set button size
            button.setPreferredSize(new Dimension(width, height));
            // Optional: Remove button decorations
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private ImageIcon createResizedImageIcon(String path, int width, int height) {
        try {
            BufferedImage img = ImageIO.read(new File(path));
            Image resizedImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void mostrarCartasTiradas() throws RemoteException {
        // aca tengo que mostrar las cartas que se tiraron cuando reanudo la partida (si es que se tiraron)

        String basePath                 = "fotocartas/";
        String cartaAux                 = " ";
        ArrayList<String> cartasYo      = controlador.getCartasTiradasYo();
        ArrayList<String> cartasRival   = controlador.getCartasTiradasRival();

        // primero seteo las cartas que "mi" jugador tiró
        for(int i=0; i<cartasYo.size(); i++){
            cartaAux = basePath + cartasYo.get(i).replace(" ", "").toLowerCase() + ".jpeg"; // basicamente lo hago fotocarta para que pueda crear la imagen
            ImageIcon imageIcon = createResizedImageIcon(cartaAux, 160, 170);

            JLabel label = new JLabel(imageIcon);

            if(i == 0){ // si se guardó primera es porque se tiro primera = ronda 1
                CartasYo1.add(label);
                CartasYo1.revalidate();
                CartasYo1.repaint();
            }
            else if(i == 1){ // ronda 2
                CartasYo2.add(label);
                CartasYo2.revalidate();
                CartasYo2.repaint();
            }
            else if (i == 2){ // ronda 3
                CartasYo3.add(label);
                CartasYo3.revalidate();
                CartasYo3.repaint();
            }
        }

        //lo mismo que en el anterior while pero con las del "oponente"
        for(int i=0; i<cartasRival.size(); i++){
            cartaAux = cartasYo.get(i).replace(" ", "").toLowerCase();
            ImageIcon imageIcon = createResizedImageIcon(cartaAux, 160, 170);
            JLabel label = new JLabel(imageIcon);

            if(i == 0){
                CartasOP1.add(label);
                CartasOP1.revalidate();
                CartasOP1.repaint();
            }
            else if(i == 1){
                CartasOP2.add(label);
                CartasOP2.revalidate();
                CartasOP2.repaint();
            }
            else if (i == 2){
                CartasOP3.add(label);
                CartasOP3.revalidate();
                CartasOP3.repaint();
            }
        }
    }

    private void mostrarCartaTiradaYO(String carta) throws RemoteException {
        // metodo privado, lo que solo hace es mostrar en el panel de mis cartas tiradas

        int ronda = controlador.nroDeRonda();
        ImageIcon imageIcon = createResizedImageIcon(carta, 160, 170);

        JLabel label = new JLabel(imageIcon);

        if(ronda == 1){
            CartasYo1.add(label);
            CartasYo1.revalidate();
            CartasYo1.repaint();
        }
        else if(ronda == 2){
            CartasYo2.add(label);
            CartasYo2.revalidate();
            CartasYo2.repaint();
        }
        else if (ronda == 3){
            CartasYo3.add(label);
            CartasYo3.revalidate();
            CartasYo3.repaint();
        }

        setBotones();

    }

    private void eliminarTodosAL(){
        removeAllActionListeners(btnCarta1);
        removeAllActionListeners(btnCarta2);
        removeAllActionListeners(btnCarta3);
        removeAllActionListeners(btnEnvido);
        removeAllActionListeners(btnAuxiliar);
        removeAllActionListeners(btnNoQuiero);
        removeAllActionListeners(btnQuiero);
        removeAllActionListeners(TRUCOButton);
        removeAllActionListeners(IRALMAZOButton);
    }

}
