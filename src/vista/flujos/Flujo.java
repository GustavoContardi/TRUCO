package vista.flujos;

import interfaces.IControlador;
import vista.VistaConsola;

import java.io.Serializable;
import java.rmi.RemoteException;

public abstract class Flujo implements Serializable {
    protected final VistaConsola vista;
    protected final IControlador controlador;

    public Flujo(VistaConsola vista, IControlador controlador) {
        this.vista = vista;
        this.controlador = controlador;
    }

    public abstract Flujo procesarEntrada(String string) throws RemoteException;

    public abstract void mostrarSiguienteTexto() throws RemoteException;
}
