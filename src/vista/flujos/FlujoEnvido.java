package vista.flujos;

import interfaces.IControlador;
import vista.VistaConsola;

import java.io.Serializable;
import java.rmi.RemoteException;

import static enums.EstadoEnvido.*;

public class FlujoEnvido extends Flujo implements Serializable {

    public FlujoEnvido(VistaConsola vista, IControlador controlador) {
        super(vista, controlador);
    }

    @Override
    public Flujo procesarEntrada(String string) throws RemoteException {

        switch (string){
            case "1" -> {
                controlador.cantarTanto(ENVIDO);
                return new FlujoMostrarCartas(vista, controlador);
            }
            case "2" ->{
                controlador.cantarTanto(REAL_ENVIDO);
                return new FlujoMostrarCartas(vista, controlador);
            }
            case "3"->{
                controlador.cantarTanto(FALTA_ENVIDO);
                return new FlujoMostrarCartas(vista, controlador);
            }
            case "4" ->{
                return new FlujoMostrarCartas(vista, controlador);
            }
        }

        return this;
    }

    @Override
    public void mostrarSiguienteTexto() {
        vista.println("\n1- Envido | 2- Real Envido | 3- Falta Envido | 4- Volver\n");
    }
}
