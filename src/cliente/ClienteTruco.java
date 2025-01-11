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


public class ClienteTruco {
    // opcion 1 == vista grafica  -||||-  opcion 2 == vista consola
    public ClienteTruco() throws RemoteException {
        ArrayList<String> opciones = new ArrayList<>();
        opciones.add("Interfáz gráfica");
        opciones.add("Consola");
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
        String interfaz = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione como quiere visualizar el juego", "Interfaz gráfica",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones.toArray(),
                null
        );


        IVistaJuego vista;
        Controlador controlador = new Controlador();
        IVistaEleccion vistaEleccion = new vistaEleccion();
        controlador.setVistaEleccion(vistaEleccion);
        vistaEleccion.setControlador(controlador);

        if(interfaz.equals("Consola")) {
            vista = new vistaConsola();
            controlador.setVistaJuego(vista);
        }
        else {
            vista = new vistaGrafica();
            controlador.setVistaJuego(vista);
        }

        vista.setControlador(controlador);

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
        //new ClienteTruco(OpcionesInicio.VISTA_GRAFICA_NO_REANUDAR);
    }
}

