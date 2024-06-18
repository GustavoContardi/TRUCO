package interfaces;

import enums.estadoEnvido;
import enums.estadoTruco;
import modelo.Carta;
import modelo.Jugador;

import java.util.ArrayList;

public interface iControlador {
    void iniciarPartida();
    void agregarJugador(Jugador j);
    String puntajeActual();
    ArrayList<String> obtenerCartas();
    void tirarCarta(int numeroDeCarta);
    int meVoyAlMazo();
    void guardarPartida();
    void recuperarPartida();
    String cantarTanto(int opcion);
    String cantarRabon(int opcion);
    int esTurnoDe();
    estadoEnvido estadoDelTanto();
    estadoTruco estadoDelRabon();
    void cantoNoQuerido();
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

    void setJugador(Jugador j);
    void setVistaEleccion(iVistaEleccion eleccion);
    void setVistaJuego(iVistaJuego juego);

}