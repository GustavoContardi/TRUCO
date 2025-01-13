package interfaces;

import modelo.Jugador;
import modelo.Partida;

import java.util.ArrayList;

public interface IVistaEleccion {
    void salir();
    void iniciar();
    void actualizarListaJugadores(ArrayList<Jugador> lista);
    void actualizarListaJugadoresUsados(ArrayList<Jugador> lista);
    void actualizarListaPartidas(ArrayList<Partida> lista);
    void setControlador(IControlador controlador);
    void mostrarMenuPrincipal();
    void reanudarPartida(ArrayList<Jugador> lista);
}
