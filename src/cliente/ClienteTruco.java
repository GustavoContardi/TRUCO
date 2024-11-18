package cliente;

import ar.edu.unlu.rmimvc.Util;
import controlador.Controlador;
import enums.OpcionesInicio;
import interfaces.IVistaEleccion;
import ar.edu.unlu.rmimvc.cliente.Cliente;
import ar.edu.unlu.rmimvc.RMIMVCException;
import interfaces.IVistaJuego;
import vista.vistaConsola;
import vista.vistaGrafica;
import vista.vistaEleccion;

import javax.swing.*;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

import static enums.OpcionesInicio.VISTA_CONSOLA_NO_REANUDAR;

public class ClienteTruco implements Serializable {
    // opcion 1 == vista grafica  -||||-  opcion 2 == vista consola
    public ClienteTruco(OpcionesInicio inicio) throws RemoteException {
        ArrayList<String> ips = Util.getIpDisponibles();
        String ip = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la que escuchará peticiones el cliente", "IP del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ips.toArray(),
                null
        );
        String port = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que escuchará peticiones el cliente", "Puerto del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                9999
        );
        String ipServidor = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la corre el servidor", "IP del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null
        );
        String portServidor = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que corre el servidor", "Puerto del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                8888
        );



        Controlador controlador = new Controlador();
        IVistaEleccion vistaEleccion = new vistaEleccion();
        vistaEleccion.setControlador(controlador);
        controlador.setVistaEleccion(vistaEleccion);

        switch (inicio) {
            case VISTA_CONSOLA_NO_REANUDAR -> {
                IVistaJuego juego = new vistaConsola();
                controlador.setVistaJuego(juego);
                juego.setControlador(controlador);
            }
            case VISTA_GRAFICA_NO_REANUDAR -> {
                IVistaJuego juego = new vistaGrafica();
                controlador.setVistaJuego(juego);
                juego.setControlador(controlador);
            }
            case VISTA_CONSOLA_REANUDAR -> {
                IVistaJuego juego = new vistaConsola();
                controlador.setVistaJuego(juego);
                juego.setControlador(controlador);
                controlador.setReanudarPartida(true);
                // hay que hacer algo aca para reanudar
            }
            case VISTA_GRAFICA_REANUDAR -> {
                IVistaJuego juego = new vistaGrafica();
                controlador.setVistaJuego(juego);
                juego.setControlador(controlador);
                controlador.setReanudarPartida(true);
                // hay que hacer algo aca para reanudar
            }
        }

        Cliente c = new Cliente(ip, Integer.parseInt(port), ipServidor, Integer.parseInt(portServidor));

        try {
            vistaEleccion.mostrarMenuPrincipal();
            c.iniciar(controlador);
        } catch (RemoteException | RMIMVCException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws RemoteException {
        //new ClienteTruco(VISTA_CONSOLA_NO_REANUDAR);
    }
}

