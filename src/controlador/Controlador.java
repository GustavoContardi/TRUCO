package controlador;

import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import enums.EstadoEnvido;
import enums.EstadoTruco;
import enums.Eventos;
import interfaces.*;
import modelo.Carta;
import modelo.Jugador;
import persistencia.Persistencia;

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
    public ArrayList<String> obtenerCartas() throws RemoteException {
        ArrayList<String> lista = new ArrayList<>();
        ArrayList<Carta> cartasJugador = new ArrayList<>();

        if (jugador.getIDJugador() == modelo.getIdJ1()) cartasJugador = modelo.getCartasJ1();
        else cartasJugador = modelo.getCartasJ2();

        if(cartasJugador != null){
            for(Carta carta : cartasJugador){
                lista.add(carta.toString());
            }
        }

        return lista;
    }

    @Override
    public void tirarCarta(int numeroDeCarta) throws RemoteException { // en realidad no es el id de la carta, porque cuando la tiro de la vista no sabe como pasarle el id pq no conoce al objeto
        int idCarta = 0;
        if(jugador.getIDJugador() == modelo.getIdJ1()) idCarta = modelo.getCartasJ1().get(numeroDeCarta-1).getIdCarta();
        else idCarta = modelo.getCartasJ2().get(numeroDeCarta-1).getIdCarta();

        modelo.tirarCarta(jugador.getIDJugador(), idCarta);
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
        return Persistencia.listaJugadoresGuardados(true);
    }

    @Override
    public void setJugador(int idJugador) throws RemoteException {
        jugador = Persistencia.recuperarJugador(idJugador);
        modelo.agregarJugador(jugador);

        vistaJuego.mostrarMenuPrincipal();
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
                vistaEleccion.actualizarListaJugadores(Persistencia.listaJugadoresGuardados(false));
            }
            case CANTO_TRUCO -> {
                if(modelo.turnoActual() != jugador.getIDJugador()) vistaJuego.cantaronRabon(Persistencia.mensajeCantoTruco(EstadoTruco.TRUCO));
            }
            case CANTO_RETRUCO -> {
                if(modelo.turnoActual() != jugador.getIDJugador()) vistaJuego.cantaronRabon(Persistencia.mensajeCantoTruco(EstadoTruco.RE_TRUCO));
            }
            case CANTO_VALE_CUATRO ->{
                if(modelo.turnoActual() != jugador.getIDJugador()) vistaJuego.cantaronRabon(Persistencia.mensajeCantoTruco(EstadoTruco.VALE_CUATRO));
            }
            case CANTO_ENVIDO -> {
                if(modelo.turnoActual() != jugador.getIDJugador()) vistaJuego.cantaronTanto(Persistencia.mensajeCantoTanto(EstadoEnvido.ENVIDO));
            }
            case CANTO_REAL_ENVIDO -> {
                if(modelo.turnoActual() != jugador.getIDJugador()) vistaJuego.cantaronTanto(Persistencia.mensajeCantoTanto(EstadoEnvido.ENVIDO_DOBLE));
            }
            case CANTO_ENVIDO_DOBLE -> {
                if(modelo.turnoActual() != jugador.getIDJugador()) vistaJuego.cantaronTanto(Persistencia.mensajeCantoTanto(EstadoEnvido.REAL_ENVIDO));
            }
            case CANTO_FALTA_ENVIDO -> {
                if(modelo.turnoActual() != jugador.getIDJugador()) vistaJuego.cantaronTanto(Persistencia.mensajeCantoTanto(EstadoEnvido.FALTA_ENVIDO));
            }
            case NUEVA_RONDA -> {
                vistaJuego.mostrarCartas(); // ?? podria hacer un metodo pero si refresca la pantalla se actualiza
            }
            case MENSAJEJ1 -> {
                // if (modelo.) vistaJuego.mostrarMensaje(modelo.getUltimoMensaje());
                vistaJuego.actualizar();
            }
            case MENSAJEJ2 -> {
                // if () vistaJuego.mostrarMensaje(modelo.getUltimoMensaje());
                vistaJuego.actualizar();
            }
            case CARTA_TIRADAJ1 -> { // este aviso es que el JUGADOR 1 tiro una carta
                vistaJuego.actualizar();
                if(modelo.getIdJ1() != jugador.getIDJugador()) vistaJuego.meTiraronCarta(modelo.ultimaCartaTiradaJ1().toString());
            }
            case CARTA_TIRADAJ2 -> { // este aviso es que el JUGADOR 1 tiro una carta
                vistaJuego.actualizar();
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
                vistaEleccion.actualizarListaJugadores(Persistencia.listaJugadoresGuardados(false));
            }
        }

    }

    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T t) throws RemoteException {
        this.modelo = (IModelo) t;
    }
}
