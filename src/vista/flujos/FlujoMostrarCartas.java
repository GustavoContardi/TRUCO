package vista.flujos;

import enums.EstadoTruco;
import interfaces.IControlador;
import vista.VistaConsola;

import java.io.Serializable;
import java.rmi.RemoteException;

public class FlujoMostrarCartas extends Flujo implements Serializable {

    public FlujoMostrarCartas(VistaConsola vista, IControlador controlador) {
        super(vista, controlador);
    }

    @Override
    public Flujo procesarEntrada(String string) throws RemoteException {

        switch (string){ // primero pongo esta opcion sin ninguna restriccion, si el jugador quiere abandonar lo puede hacer cuando quiera, independientemente del turno y estado de la partida
            case "6" -> {
                controlador.abandonarPartida();
            }
        }

        if(vista.getBloquearBotones()) return this;

        if(!controlador.esMiTurno()) vista.println("\n----------------------- ESPERE A SU TURNO ----------------------------\n");
        else{
            switch (string){
                case "1" -> {
                    if(controlador.seCantoEnvido()) vista.println("\nNo puedes cantar el envido ahora\n");
                    else return new FlujoEnvido(vista, controlador);
                }
                case "2" -> {
                    if(controlador.puedoCantarTruco(controlador.estadoDelRabon())){
                        switch (controlador.estadoDelRabon()){
                            case NADA -> controlador.cantarRabon(EstadoTruco.TRUCO);
                            case TRUCO -> controlador.cantarRabon(EstadoTruco.RE_TRUCO);
                            case RE_TRUCO -> controlador.cantarRabon(EstadoTruco.VALE_CUATRO);
                            case VALE_CUATRO -> {
                                vista.println("No hay nada para cantar");
                                return new FlujoMostrarCartas(vista, controlador);
                            }
                        }
                    }
                    else vista.println("No puedes cantar en este momentos...\n");
                }
                case "3" -> {
                    return new FlujoTirarCarta(vista, controlador);
                }
                case "4" -> controlador.meVoyAlMazo();
                case "5" -> {
                    if(controlador.tengoFlor()) controlador.cantarFlor();
                }
            }
        }
        return new FlujoMostrarCartas(vista, controlador);
    }

    @Override
    public void mostrarSiguienteTexto() throws RemoteException {
        if(controlador.obtenerCartasDisponibles() != null && (controlador.numeroDeMano() > -1 || controlador.seReanudoPartida())){
            System.out.println("numero de mano: " + controlador.numeroDeMano());
            vista.println("");
            vista.mostrarMesa();
            vista.println("");
            vista.println("\nCARTAS DE: " + controlador.getNombreJugador().toUpperCase());
            vista.mostrarCartasDisponibles();
            vista.println("");
            vista.mostrarOpciones();
        }
        else{
            vista.println("--------------------------------------");
            vista.println("--------- ESPERANDO AL RIVAL ---------");
            vista.println("--------------------------------------\n");
        }
    }
}
