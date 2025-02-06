package interfaces;

import modelo.Jugador;
import modelo.Partida;

import java.util.ArrayList;

public interface IVistaEleccion {
    void salir();
    void iniciar();
    void actualizarListaJugadores(ArrayList<Jugador> lista);
    void setControlador(IControlador controlador);
    void mostrarMenuPrincipal();
    void reanudarPartida(ArrayList<Jugador> lista);
}
