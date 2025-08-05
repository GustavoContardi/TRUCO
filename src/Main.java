import persistencia.PersistenciaJugador;

import vista.VistaInicio;

import java.rmi.RemoteException;


public class Main {
    public static void main(String[] args) throws RemoteException {
        // MAIN \\

        PersistenciaJugador.delvolverTodosJugadores();
        VistaInicio inicio = new VistaInicio();
        VistaInicio inicio2 = new VistaInicio();

        // ACORDARSE A LO ULTIMO PONER LAS PANTALLAS DE CARGA
        inicio.iniciar();

    }
}


