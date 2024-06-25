package cliente;

import ar.edu.unlu.rmimvc.Util;
import controlador.Controlador;
import interfaces.IVistaEleccion;
import ar.edu.unlu.rmimvc.cliente.Cliente;
import ar.edu.unlu.rmimvc.RMIMVCException;
import interfaces.IVistaJuego;
import vista.vistaConsola;
import vista.vistaGrafica;
import vista.vistaEleccion;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ClienteTruco {
    // opcion 1 == vista grafica |||| opcion 2 == vista consola
    public ClienteTruco(int vista) throws RemoteException {
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

        if(vista == 1){
            IVistaJuego juego = new vistaGrafica();
            controlador.setVistaJuego(juego);
            juego.setControlador(controlador);
        }
        else{
            IVistaJuego juego = new vistaConsola();
            controlador.setVistaJuego(juego);
            juego.setControlador(controlador);
        }

        Cliente c = new Cliente(ip, Integer.parseInt(port), ipServidor, Integer.parseInt(portServidor));

        try {
            c.iniciar(controlador);
            vistaEleccion.mostrarMenuPrincipal();
        } catch (RemoteException | RMIMVCException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws RemoteException {
        new ClienteTruco(0);
    }

}
