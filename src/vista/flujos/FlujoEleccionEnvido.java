package vista.flujos;

import enums.EstadoEnvido;
import interfaces.IControlador;
import vista.VistaConsola;

import java.io.Serializable;
import java.rmi.RemoteException;

import static enums.EstadoEnvido.*;

public class FlujoEleccionEnvido extends Flujo implements Serializable {
    EstadoEnvido estadoEnvido;

    public FlujoEleccionEnvido(VistaConsola vista, IControlador controlador, EstadoEnvido estado) {
        super(vista, controlador);
        estadoEnvido = estado;
    }

    @Override
    public Flujo procesarEntrada(String string) throws RemoteException {

        switch (estadoEnvido){
            case ENVIDO -> {
                switch (string){
                    case "1" -> controlador.cantarTanto(ENVIDO_DOBLE);
                    case "2" -> controlador.cantarTanto(REAL_ENVIDO);
                    case "3" -> controlador.cantarTanto(FALTA_ENVIDO);
                    case "4" -> controlador.tantoQuerido();
                    case "5" -> controlador.tantoNoQuerido();
                    default -> {
                        return this;
                    }
                }
            }
            case ENVIDO_DOBLE -> {
                switch (string){
                    case "1" -> controlador.cantarTanto(REAL_ENVIDO);
                    case "2" -> controlador.cantarTanto(FALTA_ENVIDO);
                    case "3" -> controlador.tantoQuerido();
                    case "4" -> controlador.tantoNoQuerido();
                    default -> {
                        return this;
                    }
                }
            }
            case REAL_ENVIDO -> {
                switch (string){
                    case "1" -> controlador.cantarTanto(FALTA_ENVIDO);
                    case "2" -> controlador.tantoQuerido();
                    case "3" -> controlador.tantoNoQuerido();
                    default -> {
                        return this;
                    }
                }
            }
            case FALTA_ENVIDO -> {
                switch (string){
                    case "1" -> controlador.tantoQuerido();
                    case "2" -> controlador.tantoNoQuerido();
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
    public void mostrarSiguienteTexto() throws RemoteException {
        vista.println("");
        switch (estadoEnvido){
            case ENVIDO -> {
                vista.println("1- Envido | 2- Real Envido | 3- Falta Envido | 4- Quiero | 5- No quiero\n");
            }
            case ENVIDO_DOBLE -> {
                vista.println("1- Real Envido | 2- Falta Envido | 3- Quiero | 4- No quiero\n");
            }
            case REAL_ENVIDO -> {
                vista.println("1- Falta Envido | 2- Quiero | 3- No quiero\n");
            }
            case FALTA_ENVIDO -> {
                vista.println("1- Quiero | 2- No quiero\n");
            }
        }
    }
}
