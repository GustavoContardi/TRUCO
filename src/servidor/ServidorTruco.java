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
    private int port;

    public ServidorTruco() throws RemoteException {
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
                if (portStr == null) {
                    JOptionPane.showMessageDialog(null, "Operación cancelada.");
                    return;
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

        Partida modelo = new Partida();
        Servidor servidor = new Servidor(ip, port);

        try {
            servidor.iniciar(modelo);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RMIMVCException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

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
                if (portStr == null) {
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
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RMIMVCException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws RemoteException {
        new ServidorTruco();
    }


}
