package servidor;

import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.servidor.Servidor;
import interfaces.IModelo;
import modelo.Partida;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.net.BindException;
import java.net.ServerSocket;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ServidorTruco {
    //
    // atributos
    //

    private     int         port;
    private     int         puntosParaGanar;    // 15 o 30 puntos para ganar
    private     boolean     flor;               // true = se juega con flor | false = se juega sin flor

    //
    // constructor 1
    //

    public ServidorTruco() throws RemoteException {
        ArrayList<String> opcionesFlor = new ArrayList<>();
        opcionesFlor.add("CON FLOR");
        opcionesFlor.add("SIN FLOR");

        ArrayList<String> opcionesPuntos = new ArrayList<>();
        opcionesPuntos.add("15 PUNTOS - PARTIDA RÁPIDA");
        opcionesPuntos.add("30 PUNTOS - PARTIDA LARGA");

        ArrayList<String> ips = Util.getIpDisponibles();
        String ip = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la que escuchará peticiones el servidor", "IP del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ips.toArray(),
                null
        );
        while (true) {
            if(ip == null) return;
            try {
                String portStr = (String) JOptionPane.showInputDialog(
                        null,
                        "Seleccione el puerto en el que escuchará peticiones el servidor",
                        "Puerto del servidor",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        8888
                );

                // Validar si el usuario presionó cancelar o cerró el diálogo
                if (portStr == null) {
                    JOptionPane.showMessageDialog(null, "Operación cancelada.");
                    return;
                }

                port = Integer.parseInt(portStr);

                // Intentar abrir el puerto para verificar su disponibilidad
                try (ServerSocket serverSocket = new ServerSocket(port)) {
                    //JOptionPane.showMessageDialog(null, "¡Servidor iniciado!");
                    break;
                }
                } catch (BindException ex) {
                    JOptionPane.showMessageDialog(null, "El puerto ya está en uso. Intente con otro.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Ingrese un número válido para el puerto.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error al verificar el puerto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
        }

        String puntos = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione a cuantos puntos quiere jugar la partida", "Configuración de la Partida",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionesPuntos.toArray(),
                null
        );

        String jardinera = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione si quiere jugar la partida con o sin flor", "Configuración de la Partida",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionesFlor.toArray(),
                null
        );

        if(jardinera.equals("CON FLOR")) flor = true;
        else flor = false;

        if(puntos.equals("15 PUNTOS - PARTIDA RÁPIDA")) puntosParaGanar = 15;
        else puntosParaGanar = 30;

        Partida modelo = new Partida(puntosParaGanar, flor);
        Servidor servidor = new Servidor(ip, port);

        try {
            servidor.iniciar(modelo);
            JOptionPane.showMessageDialog(null, "¡Servidor iniciado!");
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RMIMVCException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //
    // constructor 2
    //

    // si se ejecuta este es porque se va a reanudar una partida seleccionada

    public ServidorTruco(Partida modelo) {
        port = 0;
        ArrayList<String> ips = Util.getIpDisponibles();
        String ip = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la que escuchará peticiones el servidor", "IP del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ips.toArray(),
                null
        );
        while (true) {
            try {
                String portStr = (String) JOptionPane.showInputDialog(
                        null,
                        "Seleccione el puerto en el que escuchará peticiones el servidor",
                        "Puerto del servidor",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        8888
                );

                // Validar si el usuario presionó cancelar o cerró el diálogo
                if (portStr == null || ip == null) {
                    JOptionPane.showMessageDialog(null, "Operación cancelada.");
                    break;
                }

                port = Integer.parseInt(portStr);

                // Intentar abrir el puerto para verificar su disponibilidad
                try (ServerSocket serverSocket = new ServerSocket(port)) {
                    //JOptionPane.showMessageDialog(null, "Puerto " + port + " está disponible. ¡Servidor iniciado!");
                    break;
                }

            } catch (BindException ex) {
                JOptionPane.showMessageDialog(null, "El puerto ya está en uso. Intente con otro.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Ingrese un número válido para el puerto.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al verificar el puerto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        Servidor servidor = new Servidor(ip, port);

        try {
            servidor.iniciar(modelo);
            JOptionPane.showMessageDialog(null, "Puerto " + port + " está disponible. ¡Servidor iniciado!");
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RMIMVCException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    //
    //  main
    //

    public static void main(String[] args) throws RemoteException {
        //new ServidorTruco();
    }


}
