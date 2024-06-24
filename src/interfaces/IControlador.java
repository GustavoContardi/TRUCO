package interfaces;

import enums.EstadoEnvido;
import enums.EstadoTruco;
import modelo.Jugador;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IControlador {
    void iniciarPartida() throws RemoteException;
    void agregarJugador(String jugador) throws RemoteException;
    String puntajeActual() throws RemoteException;
    ArrayList<String> obtenerCartas();
    void tirarCarta(int numeroDeCarta);
    int meVoyAlMazo() throws RemoteException;
    void guardarPartida();
    void recuperarPartida();
    String cantarTanto(int opcion) throws RemoteException;
    String cantarRabon(int opcion);
    int esTurnoDe();
    EstadoEnvido estadoDelTanto();
    EstadoTruco estadoDelRabon();
    void cantoNoQuerido() throws RemoteException;
    void setModelo(IModelo modelo);
    boolean esMiTurno();
    boolean seCantoEnvido();
    boolean seCantoEnvidoDoble();
    boolean seCantoRealEnvido();
    boolean seCantoFaltaEnvido();
    boolean seCantoTruco();
    boolean seCantoReTruco();
    boolean seCantoValeCuatro();
    int nroDeRonda();
    void rabonQuerido();
    void rabonNoQuerido();
    void tantoQuerido();
    void tantoNoQuerido();
    ArrayList<Jugador> listaJugadoresMasGanadores();

    void setJugador(Jugador j) throws RemoteException;
    void setVistaEleccion(IVistaEleccion eleccion);
    void setVistaJuego(IVistaJuego juego);

}