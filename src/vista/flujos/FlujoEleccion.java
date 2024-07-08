package vista.flujos;

import interfaces.IControlador;
import vista.vistaConsola;

import java.rmi.RemoteException;

public class FlujoEleccion extends Flujo{

    public FlujoEleccion(vistaConsola vista, IControlador controlador) {
        super(vista, controlador);
    }

    @Override
    public Flujo procesarEntrada(String string) throws RemoteException {
        return null;
    }

    @Override
    public void mostrarSiguienteTexto() throws RemoteException {

    }
}
