package interfaces;

public interface iVistaJuego {
    void mostrarCartas();
    void actualizarPuntaje(String puntaje);
    void mostrarMensaje(String msj);
    void limpiarPantalla();
    void finDeMano();
    void finDeLaPartida();
    void cantaronRabon(String rabon);
    void cantaronTanto(String tanto);
    void println(String text);
    void mostrarMenuPrincipal();
    //void setFlujoActual(Flujo flujoActual);
    void setControlador(iControlador controlador);
    void actualizar();


}
