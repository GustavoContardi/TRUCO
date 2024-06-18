package interfaces;

import enums.estadoTruco;
import enums.estadoEnvido;
import modelo.Carta;
import modelo.Jugador;

public interface iModelo {
    void nuevaPartida();
    void nuevaRonda();
    void finDeLaRonda();
    void siguienteTurno();
    int turnoActual();
    Carta tirarCarta(int idJJugador, int idCarta);
    void finDePartida();
    String puntosActuales();
    void cantarRabon(int id, int opcion);
    void cantarEnvido(int id, int opcion);
    void meVoyAlMazo(int id);
    boolean esFinDePartida();
    void agregarJugador(Jugador jugador);
    void actualizarPuntos();
    estadoTruco estadoRabon();
    estadoEnvido estadoTanto();
}
