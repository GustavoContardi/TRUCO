package vista.flujos;

import enums.EstadoTruco;
import interfaces.IControlador;
import vista.vistaConsola;

import java.io.Serializable;
import java.rmi.RemoteException;

import static enums.EstadoTruco.*;

public class FlujoEleccionTruco extends Flujo implements Serializable {
    private EstadoTruco estadoTruco;
    public FlujoEleccionTruco(vistaConsola vista, IControlador controlador, EstadoTruco estadoTruco) {
        super(vista, controlador);
        this.estadoTruco = estadoTruco;
    }

    @Override
    public Flujo procesarEntrada(String string) throws RemoteException {

        switch (estadoTruco){
            case TRUCO -> {
                switch (string){
                    case "1" -> controlador.cantarRabon(RE_TRUCO);
                    case "2" -> controlador.rabonQuerido();
                    case "3" -> controlador.rabonNoQuerido();
                    default -> {
                        return this;
                    }
                }
            }
            case RE_TRUCO -> {
                switch (string){
                    case "1" -> controlador.cantarRabon(VALE_CUATRO);
                    case "2" -> controlador.rabonQuerido();
                    case "3" -> controlador.rabonNoQuerido();
                    default -> {
                        return this;
                    }
                }
            }
            case VALE_CUATRO -> {
                switch (string){
                    case "1" -> controlador.rabonQuerido();
                    case "2" -> controlador.rabonNoQuerido();
                    default -> {
                        return this;
                    }
                }
            }
            default -> {
                return this;
            }
        }

        return new FlujoMostrarCartas(vista, controlador);
    }

    @Override
    public void mostrarSiguienteTexto() {
        vista.println("");
        switch (estadoTruco){
            case TRUCO         ->  vista.println("1- Re Truco | 2- Quiero | 3- No Quiero");
            case RE_TRUCO      ->  vista.println("1- Vale Cuatro | 2- Quiero | 3- No Quiero");
            case VALE_CUATRO   ->  vista.println("1- Quiero | 2- No Quiero");
        }
    }
}
