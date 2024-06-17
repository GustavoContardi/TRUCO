package interfaces;

import modelo.Carta;

import java.util.ArrayList;

public interface iVistaJuego {
    void mostrarCartas(ArrayList<Carta> cartas);
    void actualizarPuntaje(String puntaje);
    void mostrarMensaje(String msj);
    void limpiarPantalla();
    void finDeMano();
    void finDeLaPartida();
    void cantaronRabon(String rabon);
    void cantaronTanto(String tanto);


}
