package interfaces;

import enums.estadoTruco;
import enums.estadoEnvido;
import modelo.Carta;
import modelo.Jugador;

import java.rmi.RemoteException;

public interface iModelo {
    void nuevaPartida() throws RemoteException;
    void nuevaRonda() throws RemoteException;
    void finDeLaRonda() throws RemoteException;
    void siguienteTurno() throws RemoteException;
    int turnoActual() throws RemoteException;
    Carta tirarCarta(int idJJugador, int idCarta) throws RemoteException;
    void finDePartida() throws RemoteException;
    String puntosActuales() throws RemoteException;
    void cantarRabon(int id, int opcion) throws RemoteException;
    void cantarEnvido(int id, int opcion) throws RemoteException;
    void meVoyAlMazo(int id) throws RemoteException;
    boolean esFinDePartida() throws RemoteException;
    void agregarJugador(Jugador jugador) throws RemoteException;
    void actualizarPuntos() throws RemoteException;
    estadoTruco estadoRabon() throws RemoteException;
    estadoEnvido estadoTanto() throws RemoteException;
    int numeroDeRonda() throws RemoteException;
    boolean cantaronEnvido() throws RemoteException;
    boolean cantaronEnvidoDoble() throws RemoteException;
    boolean cantaronRealEnvido() throws RemoteException;
    boolean cantaronFaltaEnvido() throws RemoteException;
    int getQuienCantoTruco() throws RemoteException;
    int getQuienCantoReTruco() throws RemoteException;
}
