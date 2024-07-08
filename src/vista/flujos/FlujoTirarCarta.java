package vista.flujos;

import interfaces.IControlador;
import vista.vistaConsola;

import java.rmi.RemoteException;

public class FlujoTirarCarta extends  Flujo{

    public FlujoTirarCarta(vistaConsola vista, IControlador controlador) {
        super(vista, controlador);
    }

    @Override
    public Flujo procesarEntrada(String string) throws RemoteException {

        return new FlujoMostrarCartas(vista, controlador);
    }

    @Override
    public void mostrarSiguienteTexto() throws RemoteException {
        vista.println("\nIngrese la carta a tirar: ");
    }
}
