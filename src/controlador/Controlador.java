package controlador;

import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import enums.EstadoEnvido;
import enums.EstadoFlor;
import enums.EstadoTruco;
import enums.Eventos;
import interfaces.*;
import modelo.Carta;
import modelo.Jugador;
import modelo.Partida;
import persistencia.*;
import vista.VistaInicio;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

import static enums.EstadoEnvido.*;
import static enums.EstadoFlor.*;
import static enums.EstadoTruco.*;

public class Controlador implements IControladorRemoto, IControlador, Serializable {

    //
    // Atributos
    //

    private   IModelo          modelo;
    private   IVistaJuego      vistaJuego;
    private   IVistaEleccion   vistaEleccion;
    private   int              idJugador;

    //
    // Constructor
    //

    public Controlador() {
        this.idJugador = -1;
    }


    //
    // Metodos publicos
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
        return modelo.puntosActuales();
    }

    // cambiar
    @Override
    public ArrayList<String> obtenerCartasDisponibles() throws RemoteException { // este es para las cartas de la vista consola
       return modelo.getCartasDisponibles(idJugador);
    }

    @Override
    public ArrayList<String> obtenerFotoCartas() throws RemoteException { // este es para la vista grafica
        return modelo.getFotoCartas(idJugador);
    }

    @Override
    public void tirarCarta(int numeroDeCarta) throws RemoteException { // en realidad no es el id de la carta, porque cuando la tiro de la vista no sabe como pasarle el id pq no conoce al objeto
        modelo.tirarCarta(idJugador, numeroDeCarta);
    }

    @Override
    public void meVoyAlMazo() throws RemoteException {
        modelo.meVoyAlMazo(idJugador);
    }

    @Override
    public void abandonarPartida() throws RemoteException {
        modelo.abandonoPartida(idJugador);
        vistaJuego.salirDelJuego();
        new VistaInicio().iniciar(); // abandona y lo hago volver a la pantalla principal de una sin tener que esperar
    }

    @Override
    public void guardarPartida() throws RemoteException {
        modelo.guardarPartida();
    }

    @Override
    public void recuperarPartida() {

    }

    @Override
    public void cantarTanto(EstadoEnvido estado) throws RemoteException {
        try{
            vistaJuego.bloquearBotones(); // bloqueo los botones hasta que responda el rival para no saturar la comunicacion
            modelo.cantarEnvido(idJugador, estado);
        } catch(RemoteException e){
            e.printStackTrace();
        }
    }

    @Override
    public void cantarRabon(EstadoTruco estado) {

        try {
            vistaJuego.bloquearBotones(); // bloqueo los botones hasta que responda el rival para no saturar la comunicacion
            modelo.cantarRabon(idJugador, estado);
        } catch (RemoteException e) {
            e.printStackTrace();
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
    public EstadoFlor estadoDeLaFlor() throws RemoteException {
        return modelo.estadoFlor();
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
    public boolean esMiTurno() throws RemoteException {
        return modelo.turnoActual() == idJugador;
    }

    @Override
    public boolean seCantoEnvido()  throws RemoteException{
        return modelo.cantaronEnvido();
    }

    @Override
    public boolean seCantoEnvidoDoble()  throws RemoteException {
        return modelo.cantaronEnvidoDoble();
    }

    @Override
    public boolean seCantoRealEnvido()  throws RemoteException {
        return modelo.cantaronRealEnvido();
    }

    @Override
    public boolean seCantoFaltaEnvido()  throws RemoteException {
        return modelo.cantaronFaltaEnvido();
    }

    @Override
    public boolean seCantoTruco()  throws RemoteException {
        return modelo.getQuienCantoTruco() != 0;
    }

    @Override
    public boolean seCantoReTruco()  throws RemoteException {
        return modelo.getQuienCantoReTruco() != 0;
    }

    @Override
    public boolean seCantoValeCuatro()  throws RemoteException {
        return false;
    }

    @Override
    public int nroDeRonda()  throws RemoteException{
        return modelo.numeroDeRonda();
    }

    @Override
    public void rabonQuerido() throws RemoteException {
        modelo.rabonQuerido(idJugador);
    }

    @Override
    public void rabonNoQuerido() throws RemoteException{
        modelo.rabonNoQuerido(idJugador);
    }

    @Override
    public void tantoQuerido() throws RemoteException {
        modelo.tantoQuerido(idJugador);
    }

    @Override
    public void tantoNoQuerido() throws RemoteException {
        modelo.tantoNoQuerido(idJugador);
    }

    @Override
    public void salirDeLaPartida() throws RemoteException {
        modelo.jugadorSalioDePartida(idJugador);
    }

    @Override
    public boolean seUnioJugador(int idJugador) throws RemoteException {
        return modelo.seUnioJugador(idJugador);
    }

    @Override
    public boolean reanudoJugador(int idJugador) throws RemoteException {
        return modelo.reanudoJugador(idJugador);
    }

    @Override
    public ArrayList<Jugador> listaJugadoresMasGanadores() {
        return PersistenciaJugador.getJugadoresGuardados(true);
    }

    @Override
    public boolean puedoCantarTruco() throws RemoteException {
        return modelo.puedeCantarTruco(idJugador);
    }

    @Override
    public boolean puedoCantarEnvido(EstadoEnvido estado) {
        return false;
    }

    @Override
    public String getNombreJugador() throws RemoteException {
        return modelo.getNombreJugador(idJugador);
    }

    @Override
    public void volverAlMenuPrincipal() throws RemoteException {
        //guardarPartida(); // guardo por si se hizo algun movimiento que no se guardo antes de salir al menu principal
        new VistaInicio().iniciar();
    }

    @Override
    public String getNombreRival() throws RemoteException {
        return modelo.getNombreRival(idJugador); // le paso el id de mi jugador y me manda el contrario
    }

    @Override
    public ArrayList<String> getCartasTiradasYo() throws RemoteException {
        return modelo.getCartasTiradasYoStr(idJugador);
    }

    @Override
    public ArrayList<String> getCartasTiradasRival() throws RemoteException {
        return modelo.getCartasTiradasRivalStr(idJugador);
    }

    @Override
    public boolean verificarCartaTirada(int nroCarta) throws RemoteException {
        return modelo.verificarCartaTirada(idJugador, nroCarta);
    }

    @Override
    public ArrayList<Partida> getListaPartidasPendientes() throws RemoteException {
        return null;
    }

    @Override
    public ArrayList<Jugador> getJugadoresRecuperados() throws RemoteException {
        return modelo.getJugadores();
    }

    @Override
    public void restablecerPartida() throws RemoteException {
        // por que esto?
    }

    @Override
    public void actualizarJugador(int idJugador, String nuevoNombre) {
        try {
            PersistenciaJugador.actualizarNombre(idJugador, nuevoNombre);
            modelo.actualizarListaJugadores();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarJugador(int idJugador) {
        try {
            PersistenciaJugador.eliminarJugador(idJugador);
            modelo.actualizarListaJugadores();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean tengoFlor() throws RemoteException {
        return modelo.tieneFlor(idJugador);
    }

    @Override
    public void cantarFlor() throws RemoteException {
        modelo.cantarFlor(idJugador, FLOR);
    }

    @Override
    public void cantarContraFlor() throws RemoteException {
        vistaJuego.bloquearBotones(); // bloqueo los botones hasta que responda el rival para no saturar la comunicacion
        modelo.cantarFlor(idJugador, CONTRA_FLOR);
    }

    @Override
    public void cantarContraFlorAlResto() throws RemoteException {
        vistaJuego.bloquearBotones(); // bloqueo los botones hasta que responda el rival para no saturar la comunicacion
        modelo.cantarFlor(idJugador, CONTRA_FLOR_AL_RESTO);
    }

    @Override
    public void noQuieroFlor(EstadoFlor estado) throws RemoteException {
        modelo.florNoQuerida(idJugador);
    }

    @Override
    public void florQuerida(EstadoFlor estado) throws RemoteException {
        modelo.florQuerida(idJugador);
    }

    @Override
    public boolean seJuegaConFlor() throws RemoteException {
        return modelo.getSeJuegaConFlor();
    }

    @Override
    public boolean seEstabaCantandoTanto() throws RemoteException {
        return modelo.getSeEstabaCantandoTanto();
    }

    @Override
    public boolean seEstabaCantandoTruco() throws RemoteException {
        return modelo.getSeEstabaCantandoTruco();
    }

    @Override
    public boolean seEstabaCantandoFlor() throws RemoteException {
        return modelo.getSeEstabaCantandoFlor();
    }

    // este metodo es para saber si antes de salir de la partida me cantaron algo (tanto, rabon, flor), para poder reanudar correctamente
    @Override
    public boolean meCantaronElUltimo() throws RemoteException {
        if(modelo.getUltimoJugadorCanto() == idJugador) return false;
        else return true;
    }

    // estos metodos son para cuando reanudo poder sacar el canto de algun lado
    // cambiar
    @Override
    public String getCantoTanto() throws RemoteException {
        return modelo.getCantoTanto();
    }

    // cambiar
    @Override
    public String getCantoTruco() throws RemoteException {
        return modelo.getCantoTruco();
    }

    @Override
    public String getCantoFlor() throws RemoteException {
        return modelo.getCantoFlor();
    }

    @Override
    public int numeroDeMano() throws RemoteException {
        return modelo.getNumeroMano(); // si es -1 todavia no se unieron
    }

    @Override
    public boolean seReanudoPartida() throws RemoteException {
        return modelo.getReanudoJ1() && modelo.getReanudoJ2(); // si reanudaron los dos se reanudo la partida
    }

    @Override
    public void setJugador(int idJugador) throws RemoteException {
        System.out.println("no vaya a ser cosa que setee aca porque se pudre");
        this.idJugador = idJugador;
        modelo.agregarJugador(idJugador);
        vistaJuego.mostrarMenuPrincipal();
    }

    @Override
    public void setJugadorReanudar(int idJugador) throws RemoteException {
        System.out.println("controlador reanudo: " + idJugador);
        this.idJugador = idJugador;
        modelo.reanudoPartida(idJugador);
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
    // observer
    //


    @Override
    public void actualizar(IObservableRemoto model, Object cambio) throws RemoteException {
        Eventos evento = (Eventos) cambio;      // lo dejo casteado

        switch (evento){
            case LISTA_JUGADORES_DISPONIBLES -> {
                vistaEleccion.actualizarListaJugadores(PersistenciaJugador.getJugadoresGuardados(false));
            }
            case CANTO_TRUCO -> {
                if(modelo.getQuienCantoTruco() != idJugador) vistaJuego.cantaronRabon(modelo.getCantoTruco(), TRUCO);
                else vistaJuego.bloquearBotones(); // bloqueo los botones al que canto para que no pueda cantar nada mas y no sature la comunicacion
            }
            case CANTO_RETRUCO -> {
                if(modelo.getQuienCantoReTruco() != idJugador) vistaJuego.cantaronRabon(modelo.getCantoTruco(), EstadoTruco.RE_TRUCO);
                else vistaJuego.bloquearBotones();
            }
            case CANTO_VALE_CUATRO ->{
                if(modelo.getQuienCantoValeCuatro() != idJugador) vistaJuego.cantaronRabon(modelo.getCantoTruco(), EstadoTruco.VALE_CUATRO);
                else vistaJuego.bloquearBotones();
            }
            case CANTO_ENVIDO -> {
                if(modelo.getQuienCantoEnvido() != idJugador) vistaJuego.cantaronTanto(modelo.getCantoTanto(), ENVIDO);
                else vistaJuego.bloquearBotones();
            }
            case CANTO_ENVIDO_DOBLE -> {
                if(modelo.getQuienCantoEnvidoDoble() != idJugador) vistaJuego.cantaronTanto(modelo.getCantoTanto(), ENVIDO_DOBLE);
                else vistaJuego.bloquearBotones();
            }
            case CANTO_REAL_ENVIDO -> {
                if(modelo.getQuienCantoRealEnvido() != idJugador) vistaJuego.cantaronTanto(modelo.getCantoTanto(), EstadoEnvido.REAL_ENVIDO);
                else vistaJuego.bloquearBotones();
            }
            case CANTO_FALTA_ENVIDO -> {
                if(modelo.getQuienCantoFaltaEnvido() != idJugador) vistaJuego.cantaronTanto(modelo.getCantoTanto(), EstadoEnvido.FALTA_ENVIDO);
                else vistaJuego.bloquearBotones();
            }
            case NUEVA_RONDA -> {
                vistaJuego.mostrarCartas();
            }
            case MENSAJEJ1 -> vistaJuego.actualizar();

            case MENSAJEJ2 -> vistaJuego.actualizar();

            case CARTA_TIRADAJ1 -> { // este aviso es que el JUGADOR 1 tiro una carta
                if(modelo.getIdJ1() != idJugador) vistaJuego.meTiraronCarta(modelo.ultimaCartaTiradaJ1().toString());
                vistaJuego.actualizar();
            }
            case CARTA_TIRADAJ2 -> { // este aviso es que el JUGADOR 2 tiro una carta
                if(modelo.getIdJ2() != idJugador) vistaJuego.meTiraronCarta(modelo.ultimaCartaTiradaJ2().toString());
                vistaJuego.actualizar();
            }
            case FIN_PARTIDA -> vistaJuego.finDeLaPartida(modelo.getJugadorGanador());
            case PUNTAJES -> vistaJuego.actualizarPuntaje(modelo.puntosActuales());
            case FIN_MANO -> {
                vistaJuego.actualizarPuntaje(modelo.puntosActuales());
                vistaJuego.finDeMano();
            }
            case INICIO_PARTIDA -> {
                vistaJuego.mostrarMenuPrincipal();
            }
            case ABANDONO_PARTIDA -> {
                if(idJugador == modelo.getIDJugadorGanador()) vistaJuego.finDeLaPartida(modelo.getJugadorGanador() /*+ " (POR ABANDONO)"*/);
            }
            case SALIO_DE_PARTIDA -> {
                if(idJugador != modelo.getIDJugadorSalio()) {
                    vistaJuego.bloquearBotones();
                    vistaJuego.mostrarAviso("SU RIVAL SALIO DE LA PARTIDA, NO PODRA JUGAR HASTA QUE VUELVA ");
                }
            }
            case LISTA_JUGADORES_TOTALES -> {
                // aca hay que pasarle la lista no ordenada
                System.out.println("");
                vistaEleccion.actualizarListaJugadores(PersistenciaJugador.getJugadoresGuardados(false));
            }
            case CANTO_QUERIDO -> {
                if(modelo.getIdJugadorQuiereCantar() != idJugador) vistaJuego.mostrarMensaje(modelo.getCantoQuiero());       //
                vistaJuego.desbloquearBotones(); // desbloqueo los botones a ambos, no pasa nada porque es solo para el canto y si no es el turno no podrá jugar
            }                                                                                                                                           //
            case CANTO_NO_QUERIDO -> {
                if(modelo.getIdJugadorNoQuizoCanto() != idJugador) vistaJuego.mostrarMensaje(modelo.getCantoNoQuiero());     //
                vistaJuego.desbloquearBotones();
            }
            case TANTO_QUERIDO -> { // este metodo se usa para avisar el resultado del tanto, no para avisar que se dijo 'QUIERO' al envido
                vistaJuego.desbloquearBotones();
                vistaJuego.mostrarAviso(modelo.getResultadoTanto());
            }
            case RESTABLECER_PARTIDA -> {
                vistaJuego.reanudarPartida();
            }
            case RESTABLECIO_J1 -> {
                if(idJugador != -1) {
                    if(idJugador == modelo.getIdJ1()) vistaJuego.mostrarEsperaRival();
                }
                else if(!(modelo.getReanudoJ2())) vistaEleccion.reanudarPartida(getJugadoresRecuperados()); // esto es para que se elimine el jugador de la eleccion
            }
            case RESTABLECIO_J2 -> {
                if(idJugador != -1) {
                    if(idJugador == modelo.getIdJ2()) vistaJuego.mostrarEsperaRival();
                }
                else if(!(modelo.getReanudoJ1())) vistaEleccion.reanudarPartida(getJugadoresRecuperados());
            }
            case CANTO_FLOR -> {
                if(modelo.getQuienCantoFlor() != idJugador) vistaJuego.cantaronFlor(modelo.getCantoFlor(), FLOR);

                // si el jugador al que le cantaron no tiene flor ya le sumo los 3 al que canto y tiene
                if(modelo.getQuienCantoFlor() != idJugador && !modelo.tieneFlor(idJugador)) modelo.florNoQuerida(idJugador);
            }
            case CANTO_CONTRAFLOR -> {
                if(modelo.getQuienCantoFlor() != idJugador) vistaJuego.cantaronFlor(modelo.getCantoFlor(), CONTRA_FLOR);
                else vistaJuego.bloquearBotones();
            }
            case CANTO_CONTRAFLOR_ALRESTO -> {
                if(modelo.getQuienCantoFlor() != idJugador) vistaJuego.cantaronFlor(modelo.getCantoFlor(), CONTRA_FLOR_AL_RESTO);
                else vistaJuego.bloquearBotones();
            }

        }


    }

    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T modeloRemoto) {
        this.modelo = (IModelo) modeloRemoto; // es necesario castear el modelo remoto
    }


    //
    // metodos privados
    //

    private boolean quedanCartasJugador() throws RemoteException { // en este metodo verifico si el jugador tiene cartas para tirar, para no tener errores futuros
        ArrayList<Carta> cartas;
        int contador = 0;

        if(idJugador != modelo.getIdJ1()) cartas = modelo.getCartasJ1();
        else cartas = modelo.getCartasJ2();

        for(Carta c : cartas){
            if(!c.isFueTirada()) contador += 1;
        }

        if(contador >= 1) return true;
        else return false;
    }

}





