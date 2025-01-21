package vista.flujos;

import enums.EstadoFlor;
import interfaces.IControlador;
import vista.VistaConsola;

import java.rmi.RemoteException;

public class FlujoFlor extends Flujo{
    private EstadoFlor estadoFlor;

    public FlujoFlor(VistaConsola vista, IControlador controlador, EstadoFlor estado) {
        super(vista, controlador);
        estadoFlor = estado;
    }

    @Override
    public Flujo procesarEntrada(String string) throws RemoteException {
        switch (estadoFlor){
            case FLOR -> {
                switch (string){
                    case "1" -> controlador.cantarContraFlor();
                    case "2" -> controlador.cantarContraFlorAlResto();
                }
            }

            case CONTRA_FLOR -> {
                switch (string){
                    case "1" -> controlador.cantarContraFlorAlResto();
                    case "2" -> controlador.florQuerida(estadoFlor);
                    case "3" -> controlador.noQuieroFlor(estadoFlor);
                }
            }

            case CONTRA_FLOR_AL_RESTO -> {
                switch (string){
                    case "1" -> controlador.florQuerida(estadoFlor);
                    case "2" -> controlador.noQuieroFlor(estadoFlor);
                }
            }
        }

        return new FlujoMostrarCartas(vista, controlador);
    }

    @Override
    public void mostrarSiguienteTexto() throws RemoteException {
        vista.println("");
        switch (estadoFlor){
            case FLOR -> {
                if(controlador.tengoFlor()) vista.println("1- Contra Flor | 2- Contra Flor al Resto\n");
            }
            case CONTRA_FLOR -> {
                vista.println("1- Contra Flor al Resto | 2- Quiero | 3- No quiero\n");
            }
            case CONTRA_FLOR_AL_RESTO -> {
                vista.println("1- Quiero | 2- No quiero\n");
            }
        }
    }
}
