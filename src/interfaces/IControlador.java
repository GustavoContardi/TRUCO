package interfaces;

import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import ar.edu.unlu.rmimvc.observer.IObservadorRemoto;
import enums.EstadoEnvido;
import enums.EstadoTruco;
import modelo.Jugador;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IControlador {
    void iniciarPartida() throws RemoteException;
    void agregarJugador(String jugador) throws RemoteException;
    String puntajeActual() throws RemoteException;
    ArrayList<String> obtenerCartas() throws RemoteException;
    void tirarCarta(int numeroDeCarta) throws RemoteException;
    int meVoyAlMazo() throws RemoteException;
    void guardarPartida() throws RemoteException;
    void recuperarPartida() throws RemoteException;
    String cantarTanto(int opcion) throws RemoteException;
    String cantarRabon(int opcion) throws RemoteException;
    int esTurnoDe() throws RemoteException;
    EstadoEnvido estadoDelTanto() throws RemoteException;
    EstadoTruco estadoDelRabon()throws RemoteException ;
    void cantoNoQuerido() throws RemoteException;
    void setModelo(IModelo modelo);
    boolean esMiTurno()throws RemoteException ;
    boolean seCantoEnvido() throws RemoteException;
    boolean seCantoEnvidoDoble() throws RemoteException;
    boolean seCantoRealEnvido() throws RemoteException;
    boolean seCantoFaltaEnvido() throws RemoteException;
    boolean seCantoTruco();
    boolean seCantoReTruco();
    boolean seCantoValeCuatro();
    int nroDeRonda() throws RemoteException;
    void rabonQuerido() throws RemoteException;
    void rabonNoQuerido() throws RemoteException;
    void tantoQuerido() throws RemoteException;
    void tantoNoQuerido() throws RemoteException ;
    ArrayList<Jugador> listaJugadoresMasGanadores();

    void setJugador(int idJugador) throws RemoteException;
    void setVistaEleccion(IVistaEleccion eleccion) throws RemoteException;
    void setVistaJuego(IVistaJuego juego) throws RemoteException;

}