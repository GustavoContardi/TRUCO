package vista.flujos;

import controlador.Controlador;
import interfaces.IControlador;
import vista.vistaConsola;
import vista.vistaGrafica;

import java.io.Serializable;
import java.rmi.RemoteException;

public abstract class Flujo implements Serializable {
    protected final vistaConsola vista;
    protected final IControlador controlador;

    public Flujo(vistaConsola vista, IControlador controlador) {
        this.vista = vista;
        this.controlador = controlador;
    }

    public abstract Flujo procesarEntrada(String string) throws RemoteException;

    public abstract void mostrarSiguienteTexto() throws RemoteException;
}
