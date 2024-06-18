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
    void tirarCarta(Carta carta, int idJugador);
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

    void setJugador(Jugador j);
    void setVistaEleccion(iVistaEleccion eleccion);
    void setVistaJuego(iVistaJuego juego);

}