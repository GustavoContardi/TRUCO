package controlador;

import enums.estadoEnvido;
import enums.estadoTruco;
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
    public void tirarCarta(int numeroDeCarta) {
        return;
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
    public String cantarTanto(int opcion) {
        modelo.cantarEnvido(jugador.getIDJugador(), opcion);
        return "";
    }

    @Override
    public String cantarRabon(int opcion) {
        modelo.cantarRabon(jugador.getIDJugador(), opcion);
        return "";
    }

    @Override
    public int esTurnoDe() {
        return modelo.turnoActual();
    }

    @Override
    public estadoEnvido estadoDelTanto() {
        return modelo.estadoTanto();
    }

    @Override
    public estadoTruco estadoDelRabon() {
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
    public boolean esMiTurno() {
        return modelo.turnoActual() == jugador.getIDJugador();
    }

    @Override
    public boolean seCantoEnvido() {
        return modelo.cantaronEnvido();
    }

    @Override
    public boolean seCantoEnvidoDoble() {
        return modelo.cantaronEnvidoDoble();
    }

    @Override
    public boolean seCantoRealEnvido() {
        return modelo.cantaronRealEnvido();
    }

    @Override
    public boolean seCantoFaltaEnvido() {
        return modelo.cantaronFaltaEnvido();
    }

    @Override
    public boolean seCantoTruco() {
        return false;
    }

    @Override
    public boolean seCantoReTruco() {
        return false;
    }

    @Override
    public boolean seCantoValeCuatro() {
        return false;
    }

    @Override
    public int nroDeRonda() {
        return modelo.numeroDeRonda();
    }

    @Override
    public void rabonQuerido() {

    }

    @Override
    public void rabonNoQuerido() {
        modelo.meVoyAlMazo(jugador.getIDJugador());
    }

    @Override
    public void tantoQuerido() {

    }

    @Override
    public void tantoNoQuerido() {

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
