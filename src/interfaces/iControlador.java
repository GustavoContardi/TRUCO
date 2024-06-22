package interfaces;

import enums.estadoEnvido;
import enums.estadoTruco;
import modelo.Carta;
import modelo.Jugador;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface iControlador {
    void iniciarPartida() throws RemoteException;
    void agregarJugador(Jugador j) throws RemoteException;
    String puntajeActual() throws RemoteException;
    ArrayList<String> obtenerCartas();
    void tirarCarta(int numeroDeCarta);
    int meVoyAlMazo() throws RemoteException;
    void guardarPartida();
    void recuperarPartida();
    String cantarTanto(int opcion) throws RemoteException;
    String cantarRabon(int opcion);
    int esTurnoDe();
    estadoEnvido estadoDelTanto();
    estadoTruco estadoDelRabon();
    void cantoNoQuerido() throws RemoteException;
    void setModelo(iModelo modelo);
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

    void setJugador(Jugador j);
    void setVistaEleccion(iVistaEleccion eleccion);
    void setVistaJuego(iVistaJuego juego);

}