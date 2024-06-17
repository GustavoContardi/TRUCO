package controlador;

import interfaces.*;
import modelo.Carta;
import modelo.Jugador;

import java.io.Serializable;
import java.util.ArrayList;

public class Controlador implements Serializable, iControlador {
    private iModelo modelo;
    private iVistaJuego vistaJuego;
    private iVistaEleccion vistaEleccion;

    @Override
    public void iniciarPartida() {

    }

    @Override
    public void agregarJugador(Jugador j) {

    }

    @Override
    public String puntajeActual() {
        return "";
    }

    @Override
    public ArrayList<Carta> obtenerCartas() {
        return null;
    }

    @Override
    public int tirarCarta(Carta carta, int idJugador) {
        return 0;
    }

    @Override
    public int meVoyAlMazo() {
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
        return "";
    }

    @Override
    public String cantarRabon(int opcion, String canto) {
        return "";
    }

    @Override
    public int esTurnoDe() {
        return 0;
    }

    @Override
    public int estadoDelTanto() {
        return 0;
    }

    @Override
    public int estadoDelRabon() {
        return 0;
    }

    @Override
    public void cantoNoQuerido() {

    }

    @Override
    public void setJugador(Jugador j) {

    }

    @Override
    public void setVistaEleccion(iVistaEleccion eleccion) {

    }

    @Override
    public void setVistaJuego(iVistaJuego juego) {

    }
}
