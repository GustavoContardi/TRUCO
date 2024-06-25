package cliente;

import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.cliente.Cliente;
import controlador.Controlador;
import interfaces.IVistaEleccion;
import interfaces.IVistaInicio;
import interfaces.IVistaJuego;
import modelo.Partida;
import vista.vistaConsola;
import vista.vistaEleccion;
import vista.vistaGrafica;
import vista.vistaInicio;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ClienteTruco {
    // opcion 1 == vista grafica |||| opcion 2 == vista consola
    public ClienteTruco(int op) {
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

        if(op == 1){
            IVistaJuego juego = new vistaGrafica();
            controlador.setVistaJuego(juego);
        }
        else{
            IVistaJuego juego = new vistaConsola();
            controlador.setVistaJuego(juego);
        }

        Cliente c = new Cliente(ip, Integer.parseInt(port), ipServidor, Integer.parseInt(portServidor));

        vistaEleccion.mostrarMenuPrincipal();
        try {
            c.iniciar(controlador);
        } catch (
                RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (
                RMIMVCException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
