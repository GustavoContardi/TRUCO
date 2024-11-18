package interfaces;

import enums.EstadoEnvido;
import enums.EstadoTruco;
import modelo.Jugador;
import modelo.Partida;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IControlador {
    void iniciarPartida() throws RemoteException;
    void agregarJugador(String jugador) throws RemoteException;
    String puntajeActual() throws RemoteException;
    ArrayList<String> obtenerCartas() throws RemoteException;
    void tirarCarta(int numeroDeCarta) throws RemoteException;
    void meVoyAlMazo() throws RemoteException;
    void guardarPartida() throws RemoteException;
    void recuperarPartida() throws RemoteException;
    void cantarTanto(EstadoEnvido estado) throws RemoteException;
    void cantarRabon(EstadoTruco estado) throws RemoteException;
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
    boolean puedoCantarTruco(EstadoTruco estado) throws RemoteException;
    boolean puedoCantarEnvido(EstadoEnvido estado);
    String getNombreJugador() throws RemoteException;
    void volverAlMenuPrincipal() throws RemoteException;
    String getNombreRival()throws RemoteException;
    ArrayList<String> getCartasTiradasYo() throws RemoteException;
    ArrayList<String> getCartasTiradasRival() throws RemoteException;
    ArrayList<Partida> getListaPartidasPendientes() throws RemoteException;
    ArrayList<Jugador> getJugadoresRecuperados() throws RemoteException;
    void restablecerPartida() throws RemoteException;
    boolean getReanudarPartida() throws RemoteException;
    void setReanudarPartida(boolean reanudarPartida) throws RemoteException;
    void actualizarJugador(int idJugador, String nombreNuevo);
    void eliminarJugador(int idJugador);


    void setJugador(int idJugador) throws RemoteException;
    void setVistaEleccion(IVistaEleccion eleccion) throws RemoteException;
    void setVistaJuego(IVistaJuego juego) throws RemoteException;

}