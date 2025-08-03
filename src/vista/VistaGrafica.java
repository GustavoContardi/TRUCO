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
    private JPanel panelBtnCartas;
    private JFrame frame;
    private IControlador controlador;
    private Image icono;
    private JFrame frameEspera;

    public VistaGrafica() throws RemoteException {
        this.frame = new JFrame("TRUCO");
        frame.setContentPane(ventana);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setSize(765, 815);
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        frameEspera = new JFrame("Esperando rival - App Truco");

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
                    try {
                        controlador.salirDeLaPartida();
                        frame.dispose();
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    // Evita cualquier acción adicional si selecciona "No"
                    //System.out.println("El usuario canceló el cierre de la ventana.");
                }
            }
        });

        panelBtnCartas.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        initIcono();
        frame.setIconImage(icono);

    }


    @Override
    public void mostrarCartas() throws RemoteException {
        btnCarta1.removeAll();
        btnCarta2.removeAll();
        btnCarta3.removeAll();
        removeAllActionListeners(btnCarta1);
        removeAllActionListeners(btnCarta2);
        removeAllActionListeners(btnCarta3);

        ArrayList<String> cartas = controlador.obtenerFotoCartas();

        if (cartas != null && (controlador.numeroDeMano() > -1 || controlador.seReanudoPartida())) {
            frame.setVisible(true);
            cerrarEsperaRival();
            actualizar();
            accionesJ2.setText("");
        }
        else {
            mostrarEsperaRival();
        }
    }

    @Override
    public void actualizarPuntaje(String puntaje) {
        puntajesLabel.setText(puntaje);
    }

    @Override
    public void mostrarMensaje(String msj) {
        accionesJ2.setText(msj);
    }

    @Override
    public void limpiarPantalla() {

    }

    @Override
    public void finDeMano() throws RemoteException {
        mostrarAviso("Fin de la mano");
        removerCartas();
        eliminarTodosAL();

        btnCarta1.setEnabled(true);
        btnCarta2.setEnabled(true);
        btnCarta3.setEnabled(true);
    }

    @Override
    public void finDeLaPartida(String nombreGanador) {
        mostrarAviso( "LA PARTIDA TERMINO. EL GANADOR ES: " + nombreGanador);

        removeBtnActionListener();
        eliminarTodosAL();
        removerCartas();

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

        accionesJ2.setText(controlador.getNombreRival() + ": " + rabon);
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
                TRUCOButton.setText(" RE TRUCO "); // si me cantaron algun rabon/truco solo puedo responder con retruco/quiero/no quiero
                TRUCOButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            controlador.cantarRabon(RE_TRUCO);
                            accionesJ2.setText("");
                            TRUCOButton.setEnabled(false);
                            setBotones();
                            //bloquearBotones();
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
                            //bloquearBotones();
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }

            case VALE_CUATRO -> {
                TRUCOButton.setText(" - - - ");
                TRUCOButton.setEnabled(false);
                // no se puede cantar más
            }
        }


    }

    @Override
    public void cantaronTanto(String tanto, EstadoEnvido estado) throws RemoteException {
        removeBtnActionListener();

        accionesJ2.setText(controlador.getNombreRival() + ": " + tanto);
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
                            //bloquearBotones();
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
                            //bloquearBotones();
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
                            //bloquearBotones();
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
                            //bloquearBotones();
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
                            //bloquearBotones();
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
                            //bloquearBotones();
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

        accionesJ2.setText(controlador.getNombreRival() + ": " + flor);

        // a los botones de 'Quiero' y 'NoQuiero' los dejo seteados y solo los activo y desactivo segun lo necesite
        btnQuiero.addActionListener(e -> {
            try {
                controlador.florQuerida(estado);
                setBotones();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });

        btnNoQuiero.setEnabled(true); // si a mi jugador le cantaron y tambien tiene flor, pero no tiene puntaje alto y no quiere arriesgar dice no quiero y es como si solo no tuviese.
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
                    btnEnvido.setText(" CONTRA FLOR ");
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
                    //mostrarAviso("Tu rival te cantó FLOR y tú no tienes. Son 3 puntos para tu rival.");
                    setBotones();
                }
            }
            case CONTRA_FLOR -> { // si me cantaron contra flor es porque tengo flor no hace falta validar
                btnEnvido.setEnabled(false);
                TRUCOButton.setText("CONTRA FLOR AL RESTO");
                TRUCOButton.addActionListener( e -> {
                    try {
                        setBotones();
                        controlador.cantarContraFlorAlResto();
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
            frame.setTitle(controlador.getNombreJugador() + " - App Truco ");
            setJMenubar();
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

        ArrayList<String> cartas = controlador.obtenerFotoCartas();

        if (cartas != null && !cartas.isEmpty()) {
            String carta1 = cartas.get(0).replace(" ", "").toLowerCase();
            String carta2 = cartas.get(1).replace(" ", "").toLowerCase();
            String carta3 = cartas.get(2).replace(" ", "").toLowerCase();

            imagen1 = basePath + carta1 + ".jpeg";
            imagen2 = basePath + carta2 + ".jpeg";
            imagen3 = basePath + carta3 + ".jpeg";

            setButtonImage(btnCarta1, imagen1, 200, 210);
            setButtonImage(btnCarta2, imagen2, 200, 210);
            setButtonImage(btnCarta3, imagen3, 200, 210);
        }

        setBotonesCartas(imagen1, imagen2, imagen3);
        setBotones();
    }

    @Override
    public void salirDelJuego() {
        frame.dispose();
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
            System.out.println("tendria que entrar aca la carta: " + carta);
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

        actualizar();

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
        frame.setVisible(true);
        frame.setTitle(controlador.getNombreJugador() + " - App Truco");
        setJMenubar();
        mostrarCartas();
        mostrarCartasTiradas();
        desactivarCartasTiradas();
        actualizarPuntaje(controlador.puntajeActual());
        desbloquearBotones();

        if(controlador.seEstabaCantandoTanto() && controlador.meCantaronElUltimo()){ // mismo que el truco pero con el tanto
            cantaronTanto(controlador.getCantoTanto(), controlador.estadoDelTanto());
        }
        else if(controlador.seEstabaCantandoTruco() && controlador.meCantaronElUltimo()){ // osea, antes de que se corte se estaba cantando algun truco y se lo cantaron a mi jugador
            cantaronRabon(controlador.getCantoTanto(), controlador.estadoDelRabon());
        }
        else if(controlador.seEstabaCantandoFlor() && controlador.meCantaronElUltimo()){ // mismo que el truco pero con la flor
            cantaronFlor(controlador.getCantoFlor(), controlador.estadoDeLaFlor());
        }

        if((controlador.seEstabaCantandoTruco() || controlador.seEstabaCantandoTanto() || controlador.seEstabaCantandoFlor()) && !controlador.meCantaronElUltimo()){
            // si se estaba cantando algo pero no fue a mi jugador, es porque canto mi jugador y tiene que esperar por eso bloqueo
            bloquearBotones();
        }
    }

    @Override
    public void mostrarEsperaRival() throws RemoteException {
        //iniciar();
        frame.setVisible(false);
        bloquearBotones();

        frameEspera.setSize(520, 380);
        JPanel panelPrincipal = (JPanel) frameEspera.getContentPane();
        panelPrincipal.setLayout(new FlowLayout());
        frameEspera.setIconImage(icono);
        frameEspera.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ImageIcon esperando = new ImageIcon("esperandoContrincante.jpeg");
        //accionesJ2.setText("Esperando contrincante...");
        Image img = esperando.getImage().getScaledInstance(520, 380, Image.SCALE_SMOOTH);

        JLabel label = new JLabel(new ImageIcon(img));
        panelPrincipal.add(label);

        frameEspera.setVisible(true);
    }


    public void setBotones() throws RemoteException {
        removeBtnActionListener(); // remuevo todos los actions listeners para que no se acumulen y los vuelvo a poner
        btnEnvido.setText("    ENVIDO    ");
        btnQuiero.setText("    QUIERO    ");
        btnNoQuiero.setText("    NO QUIERO    ");
        IRALMAZOButton.setText("    IR AL MAZO    ");
        TRUCOButton.setText("    TRUCO    ");
        btnAuxiliar.setText("");
        //accionesJ2.setText("");
        btnAuxiliar.setEnabled(false);
        btnQuiero.setEnabled(false);
        btnNoQuiero.setEnabled(false);
        IRALMAZOButton.setEnabled(true);

        if(controlador.seCantoEnvido() || controlador.nroDeRonda() > 1) btnEnvido.setEnabled(false);
        else btnEnvido.setEnabled(true);

        if(controlador.esMiTurno()) {

            btnEnvido.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if (!controlador.seCantoEnvido()) setBotonesEnvido();
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            TRUCOButton.setEnabled(true);
            switch (controlador.estadoDelRabon()) {
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
                        switch (controlador.estadoDelRabon()) {
                            case NADA -> {
                                if (controlador.esMiTurno() && controlador.puedoCantarTruco()) {
                                    TRUCOButton.setText("  RE TRUCO  ");
                                    TRUCOButton.setEnabled(false);
                                    controlador.cantarRabon(TRUCO);
                                    setBotones();
                                    bloquearBotones();
                                }
                            }
                            case TRUCO -> {
                                if (controlador.esMiTurno() && controlador.puedoCantarTruco()) {

                                    TRUCOButton.setText("  VALE CUATRO  ");
                                    TRUCOButton.setEnabled(false);
                                    controlador.cantarRabon(RE_TRUCO);
                                    setBotones();
                                    bloquearBotones();
                                }
                            }
                            case RE_TRUCO -> {
                                if (controlador.esMiTurno() && controlador.puedoCantarTruco()) {

                                    TRUCOButton.setText("  ---  ");
                                    TRUCOButton.setEnabled(false);
                                    controlador.cantarRabon(VALE_CUATRO);
                                    setBotones();
                                    bloquearBotones();
                                }
                            }
                        }
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                    ;
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
        }

        if(controlador.tengoFlor() && controlador.seJuegaConFlor() && controlador.nroDeRonda() == 1){
            btnAuxiliar.setText("  FLOR  ");
            btnAuxiliar.setEnabled(true);
            btnAuxiliar.addActionListener(e -> {
                try {
                    controlador.cantarFlor();
                    setBotones();
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }

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

    }

    @Override
    public void bloquearBotones(){
        // bloqueo los botones cuando me cantan para que responda antes de poder tirar

        removeAllActionListeners(btnCarta1);
        removeAllActionListeners(btnCarta2);
        removeAllActionListeners(btnCarta3);
        removeAllActionListeners(TRUCOButton);
        removeAllActionListeners(btnAuxiliar);
        removeAllActionListeners(btnEnvido);
        removeAllActionListeners(IRALMAZOButton);

    }

    @Override
    public void desbloquearBotones() throws RemoteException {
        actualizar();
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
        JFrame frameMSJ = new JFrame("Aviso - App Truco");
        frameMSJ.setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));


        // Label con el mensaje (centrado)
        JLabel titulo = new JLabel("<html><div style='text-align: center;'>" + text + "</div></html>");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titulo, BorderLayout.CENTER);

        // Botón "Aceptar"
        JButton botonAceptar = new JButton("Aceptar");
        botonAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameMSJ.dispose();
            }
        });
        JPanel panelBoton = new JPanel();
        panelBoton.add(botonAceptar);
        panel.add(panelBoton, BorderLayout.SOUTH);

        // --- Cálculo dinámico del tamaño ---
        FontMetrics metrics = titulo.getFontMetrics(titulo.getFont());
        int textoWidth = metrics.stringWidth(text); // Ancho del texto en píxeles
        int textoHeight = metrics.getHeight(); // Alto de una línea

        // Margen horizontal (para que el texto no quede pegado a los bordes)
        int margenHorizontal = 50;
        int anchoVentana = Math.min(700, textoWidth + margenHorizontal); // Límite máximo de 600px

        // Alto de la ventana (incluye espacio para el botón)
        int altoVentana = textoHeight + 100; // 100px extra para el botón y márgenes

        frameMSJ.setSize(anchoVentana, altoVentana+18);
        frameMSJ.add(panel);
        frameMSJ.setVisible(true);
    }

    private void setBotonesCartas(String imagen1, String imagen2, String imagen3) throws RemoteException {
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
                        actualizar();
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
                        actualizar();
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
                        actualizar();
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
        if(!cartasYo.isEmpty()){

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
        }

        //lo mismo que en el anterior while pero con las del "oponente"
        if(!cartasRival.isEmpty()){
            for(int i=0; i<cartasRival.size(); i++){
                cartaAux = basePath + cartasRival.get(i).replace(" ", "").toLowerCase() + ".jpeg";
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

    // metodo para desactivar los botones de cartas que ya tire cunado reanudo la partida
    private void desactivarCartasTiradas() throws RemoteException {
        ArrayList<String> cartasDisponibles = controlador.obtenerCartasDisponibles();

        // el espacio en blanco quiere decir que fue tirada y la correspondencia posicion de carta en lista y boton es: i = i+1
        if(cartasDisponibles.get(0).equals(" ")) btnCarta1.setEnabled(false);
        if(cartasDisponibles.get(1).equals(" ")) btnCarta2.setEnabled(false);
        if(cartasDisponibles.get(2).equals(" ")) btnCarta3.setEnabled(false);

    }

    private void removerCartas(){
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

    private void setJMenubar() {
        JMenuBar mnuPrincipal = new JMenuBar();
        JMenu mnuArchivo = new JMenu("Opciones");
        mnuPrincipal.add(mnuArchivo);
        JMenuItem mnuiAbandonar = new JMenuItem("Abandonar partida");
        JMenuItem mnuiSalir = new JMenuItem("Volver al menu principal");
        mnuiAbandonar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controlador.abandonarPartida();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        mnuiSalir.addActionListener(e ->{
            try {
                controlador.salirDeLaPartida();
                frame.dispose();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });

        mnuArchivo.add(mnuiSalir);
        mnuArchivo.add(mnuiAbandonar);
        frame.setJMenuBar(mnuPrincipal);
    }

    private void initIcono() {
        icono = new ImageIcon("icono.jpeg").getImage();
        Image originalImage = icono;
        Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icono = new ImageIcon(scaledImage).getImage();
    }

    private void cerrarEsperaRival(){
        frameEspera.setVisible(false);
    }

}
