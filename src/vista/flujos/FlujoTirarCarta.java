package vista.flujos;

import interfaces.IControlador;
import vista.VistaConsola;

import java.io.Serializable;
import java.rmi.RemoteException;

public class FlujoTirarCarta extends  Flujo implements Serializable {

    public FlujoTirarCarta(VistaConsola vista, IControlador controlador) {
        super(vista, controlador);
    }

    @Override
    public Flujo procesarEntrada(String string) throws RemoteException {
        Integer nroCarta;
        try{
            nroCarta = Integer.parseInt(string);
        }catch (NumberFormatException e) {
            vista.mostrarAviso("¡Ingrese un número válido! ");
            return this;
        }

        if( (nroCarta > controlador.obtenerCartasDisponibles().size() || 1 > nroCarta)) {
            vista.mostrarAviso("Ingrese un número dentro del rango. ");
            return this;
        }
        // esto es si ya tiro la carta 1 y vuelve a apretar 1
        if(!controlador.verificarCartaTirada(nroCarta)){
            vista.mostrarAviso("Ingrese un numero dentro del rango. ");
            return this;
        }
        else controlador.tirarCarta(nroCarta);
        return new FlujoMostrarCartas(vista, controlador);
    }

    @Override
    public void mostrarSiguienteTexto() throws RemoteException {
        vista.mostrarCartasDisponibles();
        vista.println("\nIngrese la carta a tirar: ");
    }
}
