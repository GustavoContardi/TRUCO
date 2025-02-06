package vista;

import enums.EstadoEnvido;
import enums.EstadoFlor;
import enums.EstadoTruco;
import interfaces.IControlador;
import interfaces.IVistaInicio;
import interfaces.IVistaJuego;
import vista.flujos.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class VistaConsola implements IVistaJuego, IVistaInicio, Serializable {

    private JPanel ventana;
    private JButton btnEnter;
    private JTextField txtEntrada;
    private JTextArea txtVista;
    private final JFrame frame;
    private Flujo flujoActual;
    private IControlador controlador;
    private boolean bloqBotones;


    //
    // constructor
    //

    public VistaConsola() throws RemoteException {
        this.frame = new JFrame("TRUCO - MODO CONSOLA");
        frame.setContentPane(ventana);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setSize(600, 450);
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

        btnEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    procesarEntrada(txtEntrada.getText());
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
                txtEntrada.setText("");
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int response = JOptionPane.showConfirmDialog(frame, "¿Estás seguro de que quieres cerrar la ventana? La partida sera guardada", "Confirmar Cierre", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    try {
                        controlador.guardarPartida(); // guardo la partida y el controlador notifica al contrincante
                        frame.dispose();  // Cierra la ventana
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

    }

    //
    // metodos publicos
    //

    public void setFlujoActual(Flujo flujoActual) throws RemoteException {
        this.flujoActual = flujoActual;
        flujoActual.mostrarSiguienteTexto();
    }

    @Override
    public void mostrarCartas() throws RemoteException {
        setFlujoActual(new FlujoMostrarCartas(this, controlador));
    }

    @Override
    public void actualizarPuntaje(String puntaje) throws RemoteException {
        setFlujoActual(new FlujoMostrarCartas(this, controlador)); // ese metodo tiene el puntaje incluido
    }

    @Override
    public void mostrarMensaje(String msj) {
        println("\n" + msj);
    }

    @Override
    public void limpiarPantalla() {
        txtVista.setText("");
    }

    @Override
    public void finDeMano() {
        println("-------------------------------");
        println("-     FIN DE LA MANO     -");
        println("-------------------------------");
    }

    @Override
    public void finDeLaPartida(String nombreGanador) throws RemoteException {

        println("-------------------------------------------------------------------------");
        println("                   FIN DE LA PARTIDA                        ");
        println("         MUCHAS GRACIAS POR USAR LA APLICACION              ");
        println("-------------------------------------------------------------------------");
        println("           ¡¡ GANADOR: " + nombreGanador + " !!");
        println("-------------------------------------------------------------------------");

        setFlujoActual(new FlujoFinPartida(this, controlador));
    }

    @Override
    public void cantaronRabon(String rabon, EstadoTruco estado) throws RemoteException {
        println(controlador.getNombreRival() + ": " + rabon);
        setFlujoActual(new FlujoEleccionTruco(this, controlador, estado));
    }

    @Override
    public void cantaronTanto(String tanto, EstadoEnvido estado) throws RemoteException {
        println(controlador.getNombreRival() + ": " + tanto);
        setFlujoActual(new FlujoEleccionEnvido(this, controlador, estado));
    }

    @Override
    public void cantaronFlor(String flor, EstadoFlor estado) throws RemoteException {
        println(controlador.getNombreRival() + ": " + flor);
        if(controlador.tengoFlor()) setFlujoActual(new FlujoFlor(this, controlador, estado));
        else setFlujoActual(new FlujoMostrarCartas(this, controlador)); // si no tengo flor no tiene sentido que pueda hacer otra cosa, sigue la partida
    }

    @Override
    public void println(String text) {
        txtVista.append(text + "\n");
    }

    @Override
    public void mostrarMenuPrincipal() throws RemoteException {
        limpiarPantalla();
        iniciar();
        setFlujoActual(new FlujoMostrarCartas(this, controlador));
    }

    @Override
    public void setControlador(IControlador controlador) {
        this.controlador = controlador;
    }

    @Override
    public void actualizar() {
        println("");
    }

    @Override
    public void salirDelJuego(){
        frame.dispose();
    }

    @Override
    public void meTiraronCarta(String carta) throws RemoteException {
        setFlujoActual(new FlujoMostrarCartas(this, controlador));
    }

    @Override
    public void tirarCarta(int posCarta) throws RemoteException {
        controlador.tirarCarta(posCarta);
        setFlujoActual(new FlujoMostrarCartas(this, controlador));
    }

    @Override
    public void iniciar() {
        frame.setVisible(true);
    }

    @Override
    public void mostrarAviso(String aviso) {
        int caracteres = aviso.length() + 2;
        println("-".repeat(caracteres));
        println(" " + aviso);
        println("-".repeat(caracteres));
    }

    @Override
    public void reanudarPartida() throws RemoteException {
        // solo tengo que mostrar la mesa porque tiene todo

        limpiarPantalla(); // limpio para que no quede nada arriba de lo que viene abajo
        setFlujoActual(new FlujoMostrarCartas(this, controlador));
    }

    @Override
    public void mostrarEsperaRival() throws RemoteException {
        iniciar();
        println("--------------------------------------------------------------------------------------------------------------------------------------");
        println(" SE HA REANUDADO LA PARTIDA CORRECTAMENTE. NO PODRÁ JUGAR HASTA QUE SU RIVAL INICIE SESION");
        println("--------------------------------------------------------------------------------------------------------------------------------------");
    }

    @Override
    public void bloquearBotones() throws RemoteException {
        bloqBotones = true;
    }

    @Override
    public void desbloquearBotones() throws RemoteException {
        bloqBotones = false;
    }

    @Override
    public void salir() {
        frame.setVisible(false);
    }

    public void mostrarOpciones() throws RemoteException {
        // muestra los botones disponibles actuales

        if( (controlador.nroDeRonda() == 1)){
            if(!controlador.seCantoEnvido()) {
                if(controlador.seJuegaConFlor() && controlador.tengoFlor()) println("1- Envido | 2- Truco | 3- Tirar Carta | 4- Ir al mazo | 5- Flor | 6- Abandonar Partida");
                else println("1- Envido | 2- Truco | 3- Tirar Carta | 4- Ir al mazo");
            }
            else println("2- Truco | 3- Tirar Carta | 4- Ir al mazo | 6- Abandonar Partida");
        }
        else{
            switch (controlador.estadoDelRabon()){
                case NADA -> println("2- Truco | 3- Tirar Carta | 4- Ir al mazo | 6- Abandonar Partida");
                case TRUCO -> println("2- Re Truco | 3- Tirar Carta | 4- Ir al mazo | 6- Abandonar Partida");
                case RE_TRUCO ->  println("2- Vale Cuatro | 3- Tirar Carta | 4- Ir al mazo | 6- Abandonar Partida");
                case VALE_CUATRO -> println("3- Tirar Carta | 4- Ir al mazo | 6- Abandonar Partida");
            }
        }


    }

    public void mostrarMesa() throws RemoteException {

        if(controlador != null){

            ArrayList<String> cartasYo = controlador.getCartasTiradasYo();
            ArrayList<String> cartasRival = controlador.getCartasTiradasRival();

            println(controlador.puntajeActual());
            println("\n----------------- MESA -----------------");
            println("|  " + controlador.getNombreJugador() + "        |        " + controlador.getNombreRival() + "  |");
            switch (controlador.nroDeRonda()){
                case 1 -> {
                    if(cartasYo.isEmpty() && cartasRival.isEmpty()) println("|                   |                   |");
                    else if(cartasYo.size() > 0 && cartasRival.isEmpty()) println("| " + cartasYo.get(0) + " |               |");
                    else if(cartasRival.size() > 0 && cartasYo.isEmpty()) println("|               " + " | " + cartasRival.get(0));
                    else  println("| " + cartasYo.get(0) + " | " + cartasRival.get(0) + " | ");
                }
                case 2 -> {
                    println("| " + cartasYo.get(0) + " | " + cartasRival.get(0) + " | ");

                    if(cartasYo.size() <= 1 && cartasRival.size() <= 1) println("|                   |                   |");
                    else if(cartasYo.size() > 1 && cartasRival.size() <= 1) println("| " + cartasYo.get(1) + " |                   |");
                    else if(cartasYo.size() < 2 && cartasRival.size() > 1) println("|               " + " | " + cartasRival.get(1) + " |");
                    else  println("| " + cartasYo.get(1) + " | " + cartasRival.get(1) + " | ");
                }
                case 3 -> {
                    println("| " + cartasYo.get(0) + " | " + cartasRival.get(0) + " | ");
                    println("| " + cartasYo.get(1) + " | " + cartasRival.get(1) + " | ");

                    if(cartasYo.size() < 2 && cartasRival.size() < 2) println("|                   |                   |");
                    else if(cartasYo.size() > 2 && cartasRival.size() < 3) println("| " + cartasYo.get(2) + " |                   |");
                    else if(cartasYo.size() < 3 && cartasRival.size() > 2) println("|               " + " | " + cartasRival.get(2)+ " |");
                    else  println("| " + cartasYo.get(2) + " | " + cartasRival.get(2) + " | ");
                }
            }


        }
    }


    public void mostrarCartasDisponibles() throws RemoteException {
        ArrayList<String> cartas = controlador.obtenerCartas();

        for(int i=0; i<cartas.size(); i++){
            println(i+1 + "- " + cartas.get(i));
        }
    }

    //
    // metodos privados
    //

    private void procesarEntrada(String input) throws RemoteException {
        input = input.trim();
        if (input.isEmpty()) {
            return;
        }
        flujoActual = flujoActual.procesarEntrada(input);
        flujoActual.mostrarSiguienteTexto();
    }


}
