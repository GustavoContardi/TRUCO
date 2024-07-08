package interfaces;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import enums.EstadoTruco;
import enums.EstadoEnvido;
import modelo.Carta;
import modelo.Jugador;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IModelo extends IObservableRemoto {

    void nuevaPartida() throws RemoteException;
    void nuevaRonda() throws RemoteException;
    void finDeLaRonda() throws RemoteException;
    void siguienteTurno() throws RemoteException;
    int turnoActual() throws RemoteException;
    void tirarCarta(int idJugador, int idCarta) throws RemoteException;
    void finDePartida() throws RemoteException;
    String puntosActuales() throws RemoteException;
    void cantarRabon(int id, EstadoTruco estado) throws RemoteException;
    void cantarEnvido(int id, EstadoEnvido estado) throws RemoteException;
    void meVoyAlMazo(int id) throws RemoteException;
    boolean esFinDePartida() throws RemoteException;
    void agregarJugador(Jugador jugador) throws RemoteException;
    void altaJugador(String nombre) throws RemoteException;
    void actualizarPuntos() throws RemoteException;
    EstadoTruco estadoRabon() throws RemoteException;
    EstadoEnvido estadoTanto() throws RemoteException;
    int numeroDeRonda() throws RemoteException;
    boolean cantaronEnvido() throws RemoteException;
    boolean cantaronEnvidoDoble() throws RemoteException;
    boolean cantaronRealEnvido() throws RemoteException;
    boolean cantaronFaltaEnvido() throws RemoteException;
    int getQuienCantoTruco() throws RemoteException;
    int getQuienCantoReTruco() throws RemoteException;
    int getQuienCantoValeCuatro() throws RemoteException;
    int getQuienCantoEnvido()throws RemoteException;
    int getQuienCantoEnvidoDoble()throws RemoteException;
    int getQuienCantoRealEnvido()throws RemoteException;
    int getQuienCantoFaltaEnvido()throws RemoteException;
    Carta ultimaCartaTiradaJ1()throws RemoteException;
    Carta ultimaCartaTiradaJ2()throws RemoteException;
    int getIdJ1()throws RemoteException;
    int getIdJ2()throws RemoteException;
    int getIdJugadorNoQuizoCanto()throws RemoteException;
    String getUltimoMensaje()throws RemoteException;;
    ArrayList<Carta> getCartasJ1() throws RemoteException;
    ArrayList<Carta> getCartasJ2() throws RemoteException;
    void tantoQuerido(int idJugadorQuizo) throws RemoteException; //
    void tantoNoQuerido(int idjugNoQuizo) throws RemoteException; // jugador que no quizo
    void rabonQuerido(int idJugadorQuizo) throws RemoteException; //
    void rabonNoQuerido(int idjugNoQuizo) throws RemoteException; // jugador que no quizo
    String getResultadoTanto() throws RemoteException;
    String getJugadorGanador() throws RemoteException;
    String getNombreRival(int idJugador) throws RemoteException; // le paso el jugador mio, necesito el contrario
}
