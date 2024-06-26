package controlador;

import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import enums.EstadoEnvido;
import enums.EstadoTruco;
import enums.Eventos;
import interfaces.*;
import modelo.Carta;
import modelo.Jugador;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Controlador implements IControladorRemoto, IControlador, Serializable {

    //
    // Atributos
    //
    private IModelo modelo;
    private IVistaJuego vistaJuego;
    private IVistaEleccion vistaEleccion;
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
    public void iniciarPartida() throws RemoteException {
        modelo.nuevaPartida();
    }

    @Override
    public void agregarJugador(String jugador) throws RemoteException {
        modelo.altaJugador(jugador);
    }

    @Override
    public String puntajeActual() throws RemoteException {
        try{
            return modelo.puntosActuales();
        } catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
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
    public int meVoyAlMazo() throws RemoteException {
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
    public String cantarTanto(int opcion) throws RemoteException {
        try{
            modelo.cantarEnvido(jugador.getIDJugador(), opcion);
        } catch(RemoteException e){
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String cantarRabon(int opcion) {
        try{
            modelo.cantarRabon(jugador.getIDJugador(), opcion);
        }catch(RemoteException e){
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public int esTurnoDe() {
        try{
            return modelo.turnoActual();
        }catch(RemoteException e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public EstadoEnvido estadoDelTanto() {
        try{
            return modelo.estadoTanto();
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public EstadoTruco estadoDelRabon() {
        try{
            return modelo.estadoRabon();
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void cantoNoQuerido() throws RemoteException{
        // ???

    }

    @Override
    public void setModelo(IModelo modelo) {
        this.modelo = modelo;
    }

    @Override
    public boolean esMiTurno() {
        try{
            return modelo.turnoActual() == jugador.getIDJugador();
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean seCantoEnvido() {
       try{
           return modelo.cantaronEnvido();
       }catch(RemoteException e){
           e.printStackTrace();
           return false;
       }
    }

    @Override
    public boolean seCantoEnvidoDoble() {
        try{
            return modelo.cantaronEnvidoDoble();
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean seCantoRealEnvido() {
        try{
            return modelo.cantaronRealEnvido();
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean seCantoFaltaEnvido() {
        try{
            return modelo.cantaronFaltaEnvido();
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean seCantoTruco() {
        try{
            return modelo.getQuienCantoTruco() != 0;
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean seCantoReTruco() {
        try{
            return modelo.getQuienCantoReTruco() != 0;
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean seCantoValeCuatro() {
        return false;
    }

    @Override
    public int nroDeRonda() {
        try{
            return modelo.numeroDeRonda();
        }catch(RemoteException e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void rabonQuerido() {

    }

    @Override
    public void rabonNoQuerido() {
        try{
            modelo.meVoyAlMazo(jugador.getIDJugador());
        }catch(RemoteException e){
            e.printStackTrace();
        }
    }

    @Override
    public void tantoQuerido() {
        /*try{

        }catch(RemoteException e){
            e.printStackTrace();;
        }
        */
    }

    @Override
    public void tantoNoQuerido() {
        /*try{

        }catch(RemoteException e){
            e.printStackTrace();;
        }
        */
    }

    @Override
    public ArrayList<Jugador> listaJugadoresMasGanadores() {
        return Jugador.getListaJugadores();
    }

    @Override
    public void setJugador(Jugador j) throws RemoteException {
        jugador = j;
        modelo.agregarJugador(jugador);
    }

    @Override
    public void setVistaEleccion(IVistaEleccion eleccion) {
        vistaEleccion = eleccion;
    }

    @Override
    public void setVistaJuego(IVistaJuego juego) {
        this.vistaJuego = juego;
    }


    //
    //
    //


    @Override
    public void actualizar(IObservableRemoto model, Object cambio) throws RemoteException {
        Eventos evento = (Eventos) cambio;      // lo dejo casteado

        switch (evento){
            case LISTA_JUGADORES_DISPONIBLES -> {
                vistaEleccion.actualizarListaJugadores(Jugador.getListaJugadores());
            }
            case CANTO_TRUCO -> {

            }
            case CANTO_RETRUCO -> {

            }
            case CANTO_VALE_CUATRO ->{

            }
            case CANTO_ENVIDO ->{

            }
            case CANTO_REAL_ENVIDO -> {

            }
            case CANTO_ENVIDO_DOBLE -> {

            }
            case CANTO_FALTA_ENVIDO -> {

            }
            case NUEVA_RONDA -> {

            }
            case MENSAJEJ1 -> {
                //controlador.getUltimoMensaje();
            }
            case MENSAJEJ2 -> {
                //controlador.getUltimoMensaje();
            }
            case CARTA_TIRADAJ1 -> { // este aviso es que el JUGADOR 1 tiro una carta
                if(modelo.getIdJ1() != jugador.getIDJugador()) vistaJuego.meTiraronCarta(modelo.ultimaCartaTiradaJ1().toString());
            }
            case CARTA_TIRADAJ2 -> { // este aviso es que el JUGADOR 1 tiro una carta
                if(modelo.getIdJ2() != jugador.getIDJugador()) vistaJuego.meTiraronCarta(modelo.ultimaCartaTiradaJ2().toString());
            }
            case FIN_PARTIDA -> vistaJuego.finDeLaPartida("");
            case PUNTAJES -> vistaJuego.actualizarPuntaje(modelo.puntosActuales());
            case FIN_MANO -> {
                vistaJuego.actualizarPuntaje(modelo.puntosActuales());
                vistaJuego.finDeMano();
            }
            case INICIO_PARTIDA -> {
                vistaJuego.mostrarMenuPrincipal();
            }
            case ABANDONO_PARTIDA -> {

            }
            case LISTA_JUGADORES_TOTALES -> {
                // aca hay que pasarle la lista no ordenada pero cuando tenga la clase Persistencia
            }
        }


    }

    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T t) throws RemoteException {
        this.modelo = (IModelo) t;
    }
}
