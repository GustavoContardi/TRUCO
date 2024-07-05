package interfaces;

import enums.EstadoEnvido;
import enums.EstadoTruco;

import java.rmi.RemoteException;

public interface IVistaJuego {
    void mostrarCartas() throws RemoteException;
    void actualizarPuntaje(String puntaje);
    void mostrarMensaje(String msj);
    void limpiarPantalla();
    void finDeMano() throws RemoteException;
    void finDeLaPartida(String nombreGanador);
    void cantaronRabon(String rabon, EstadoTruco estado) throws RemoteException;
    void cantaronTanto(String tanto, EstadoEnvido estado) throws RemoteException;
    void println(String text);
    void mostrarMenuPrincipal() throws RemoteException;
    //void setFlujoActual(Flujo flujoActual);
    void setControlador(IControlador controlador);
    void actualizar() throws RemoteException;
    void salirDelJuego();
    void meTiraronCarta(String carta) throws RemoteException;
    void tirarCarta();
    void iniciar();
}
