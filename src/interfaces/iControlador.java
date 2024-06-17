package interfaces;

import modelo.Carta;
import modelo.Jugador;

import java.util.ArrayList;

public interface iControlador {
    void iniciarPartida();
    void agregarJugador(Jugador j);
    String puntajeActual();
    ArrayList<Carta> obtenerCartas();
    int tirarCarta(Carta carta, int idJugador);
    int meVoyAlMazo();
    void guardarPartida();
    void recuperarPartida();
    String cantarTanto(int opcion, String canto);
    String cantarRabon(int opcion, String canto);
    int esTurnoDe();
    int estadoDelTanto();
    int estadoDelRabon();
    void cantoNoQuerido();


    void setJugador(Jugador j);
    void setVistaEleccion(iVistaEleccion eleccion);
    void setVistaJuego(iVistaJuego juego);

}