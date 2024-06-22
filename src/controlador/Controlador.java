package controlador;

import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import enums.estadoEnvido;
import enums.estadoTruco;
import interfaces.*;
import modelo.Carta;
import modelo.Jugador;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Controlador implements Serializable, iControlador, IControladorRemoto {

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
    public void iniciarPartida() throws RemoteException {
        modelo.nuevaPartida();
    }

    @Override
    public void agregarJugador(Jugador j) throws RemoteException {
        this.jugador = j;
        modelo.agregarJugador(j);
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
    public estadoEnvido estadoDelTanto() {
        try{
            return modelo.estadoTanto();
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public estadoTruco estadoDelRabon() {
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
    public void setModelo(iModelo modelo) {
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

    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T modeloRemoto) throws RemoteException {
        this.modelo = (iModelo) modeloRemoto;
    }

    @Override
    public void actualizar(IObservableRemoto modelo, Object cambio) throws RemoteException {

    }
}
