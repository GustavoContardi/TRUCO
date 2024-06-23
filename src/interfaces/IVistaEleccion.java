package interfaces;

import modelo.Jugador;

import java.util.ArrayList;

public interface IVistaEleccion {
    void salir();
    void iniciar();
    void actualizarListaJugadores(ArrayList<Jugador> lista);
}
