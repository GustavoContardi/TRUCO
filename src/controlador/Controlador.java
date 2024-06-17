package controlador;

import interfaces.*;
import modelo.Carta;
import modelo.Jugador;

import java.io.Serializable;
import java.util.ArrayList;

public class Controlador implements Serializable, iControlador {

    //
    // Atributos
    //
    private iModelo modelo;
    private iVistaJuego vistaJuego;
    private iVistaEleccion vistaEleccion;
    private Jugador jugador;

    //
    // Constructor
    //

    public Controlador() {
    }


    //
    // Metodos
    //

    @Override
    public void iniciarPartida() {
        modelo.nuevaPartida();
    }

    @Override
    public void agregarJugador(Jugador j) {
        this.jugador = j;
        modelo.agregarJugador(j);
    }

    @Override
    public String puntajeActual() {
        return modelo.puntosActuales();
    }

    @Override
    public ArrayList<String> obtenerCartas() {
        ArrayList<String> lista = new ArrayList<>();

        for(Carta carta : jugador.getCartasObtenidas()){
            lista.add(carta.toString());
        }

        return lista;
    }

    @Override
    public int tirarCarta(Carta carta, int idJugador) {
        return 0;
    }

    @Override
    public int meVoyAlMazo() {
        modelo.meVoyAlMazo(jugador.getIDJugador());
        return 0;
    }

    @Override
    public void guardarPartida() {

    }

    @Override
    public void recuperarPartida() {

    }

    @Override
    public String cantarTanto(int opcion, String canto) {
        modelo.cantarEnvido(jugador.getIDJugador(), opcion);
        return "";
    }

    @Override
    public String cantarRabon(int opcion, String canto) {
        modelo.cantarRabon(jugador.getIDJugador(), opcion);
        return "";
    }

    @Override
    public int esTurnoDe() {
        return modelo.turnoActual();
    }

    @Override
    public int estadoDelTanto() {
        return modelo.estadoTanto();
    }

    @Override
    public int estadoDelRabon() {
        return modelo.estadoRabon();
    }

    @Override
    public void cantoNoQuerido() {
        // ???
    }

    @Override
    public void setModelo(iModelo modelo) {
        this.modelo = modelo;
    }

    @Override
    public void setJugador(Jugador j) {
        jugador = j;
    }

    @Override
    public void setVistaEleccion(iVistaEleccion eleccion) {
        vistaEleccion = eleccion;
    }

    @Override
    public void setVistaJuego(iVistaJuego juego) {
        this.vistaJuego = juego;
    }
}
