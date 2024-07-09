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
        Integer nroCarta;
        try{
            nroCarta = Integer.parseInt(string);
        }catch (NumberFormatException e) {
            vista.println("Ingrese un número válido.\n");
            return this;
        }

        if(nroCarta > controlador.obtenerCartas().size() || 1 > nroCarta) {
            vista.println("Ingrese un número dentro del rango.\n");
            return this;
        }
        else controlador.tirarCarta(nroCarta);

        return new FlujoMostrarCartas(vista, controlador);
    }

    @Override
    public void mostrarSiguienteTexto() throws RemoteException {
        vista.println("\nIngrese la carta a tirar: ");
    }
}
