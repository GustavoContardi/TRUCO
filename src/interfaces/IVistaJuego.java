package interfaces;

import enums.EstadoEnvido;
import enums.EstadoTruco;

import java.rmi.RemoteException;

public interface IVistaJuego {
    void mostrarCartas() throws RemoteException;
    void actualizarPuntaje(String puntaje);
    void mostrarMensaje(String msj);
    void limpiarPantalla();
    void finDeMano();
    void finDeLaPartida(String nombreGanador);
    void cantaronRabon(String rabon) throws RemoteException;
    void cantaronTanto(String tanto) throws RemoteException;
    void println(String text);
    void mostrarMenuPrincipal() throws RemoteException;
    //void setFlujoActual(Flujo flujoActual);
    void setControlador(IControlador controlador);
    void actualizar();
    void salirDelJuego();
    void meTiraronCarta(String carta);
    void tirarCarta();
    void iniciar();
}
