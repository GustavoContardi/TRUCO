package interfaces;

import modelo.Jugador;
import modelo.Partida;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IVistaEleccion {
    void salir();
    void iniciar();
    void actualizarListaJugadores(ArrayList<Jugador> lista) throws RemoteException;
    void setControlador(IControlador controlador);
    void mostrarMenuPrincipal() throws RemoteException;
    void reanudarPartida(ArrayList<Jugador> lista) throws RemoteException;
}
