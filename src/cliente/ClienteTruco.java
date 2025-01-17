package cliente;

import ar.edu.unlu.rmimvc.Util;
import controlador.Controlador;
import interfaces.IVistaEleccion;
import ar.edu.unlu.rmimvc.cliente.Cliente;
import ar.edu.unlu.rmimvc.RMIMVCException;
import interfaces.IVistaJuego;
import vista.inicio;
import vista.vistaConsola;
import vista.vistaGrafica;
import vista.vistaEleccion;

import javax.swing.*;
import java.io.IOException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;


public class ClienteTruco {
    private int portCliente;
    private int portServer;


    // reanuda = true -> es que va a reanudar una partida por terminar
    public ClienteTruco(boolean reanuda) throws RemoteException {
        Socket clientSocket = null;
        ArrayList<String> opciones = new ArrayList<>();
        opciones.add("Interfaz Gráfica");
        opciones.add("Consola Gráfica");

        // Obtener IP del cliente
        ArrayList<String> ips = Util.getIpDisponibles();
        String ip = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la que escuchará peticiones el cliente",
                "IP del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ips.toArray(),
                null
        );

        if (ip == null) {
            JOptionPane.showMessageDialog(null, "Operación cancelada.");
            inicio ini = new inicio();
            ini.iniciar(); // inicia sin pantalla de carga asi da la sensacion que solo volvio para atrás
            return;
        }

        // Configurar puerto del cliente
        while (true) {
            try {
                String portStr = (String) JOptionPane.showInputDialog(
                        null,
                        "Seleccione el puerto en el que escuchará peticiones el cliente",
                        "Puerto del cliente",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        9999
                );

                if (portStr == null) {
                    JOptionPane.showMessageDialog(null, "Operación cancelada.");
                    inicio ini = new inicio();
                    ini.iniciar(); // inicia sin pantalla de carga asi da la sensacion que solo volvio para atrás
                    return;
                }

                portCliente = Integer.parseInt(portStr);

                if (portCliente < 1100 || portCliente > 65535) {
                    throw new NumberFormatException("Puerto fuera de rango (1-65535).");
                }

                // Verificar si el puerto está disponible
                try (ServerSocket serverSocket = new ServerSocket(portCliente)) {
                    break;
                }
            } catch (BindException e) {
                JOptionPane.showMessageDialog(null, "El puerto ya está en uso. Intente con otro.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un número válido para el puerto (1100-65535).", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al verificar el puerto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Obtener IP del servidor
        String ipServidor = (String) JOptionPane.showInputDialog(
                null,
                "Ingrese la IP del servidor",
                "IP del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null
        );

        if (ipServidor == null) {
            JOptionPane.showMessageDialog(null, "Operación cancelada.");
            inicio ini = new inicio();
            ini.iniciar(); // inicia sin pantalla de carga asi da la sensacion que solo volvio para atrás
            return;
        }

        // Conexión al servidor
        while (true) {
            try {
                String portStr = (String) JOptionPane.showInputDialog(
                        null,
                        "Seleccione el puerto al que desea conectarse",
                        "Conexión al servidor",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        8888
                );

                if (portStr == null) {
                    JOptionPane.showMessageDialog(null, "Operación cancelada.");
                    inicio ini = new inicio();
                    ini.iniciar(); // inicia sin pantalla de carga asi da la sensacion que solo volvio para atrás
                    return;
                }

                portServer = Integer.parseInt(portStr);

                if (portServer < 1100 || portServer > 65535) {
                    JOptionPane.showMessageDialog(null, "El puerto debe estar entre 1100 y 65535.", "Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                // Intentar conectar al servidor
                clientSocket = new Socket(ipServidor, portServer);
                break; // Salir del bucle si la conexión es exitosa
            } catch (ConnectException e) {
                JOptionPane.showMessageDialog(null, "No se pudo conectar al servidor en el puerto " + portServer + ". Verifique que esté activo.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un número válido para el puerto.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al intentar conectarse: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Interfaz del cliente
        String interfaz = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione cómo quiere visualizar el juego",
                "Interfaz gráfica",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones.toArray(),
                null
        );

        if (interfaz == null) {
            JOptionPane.showMessageDialog(null, "Operación cancelada.");
            inicio ini = new inicio();
            ini.iniciar(); // inicia sin pantalla de carga asi da la sensacion que solo volvio para atrás
            return;
        }

        IVistaJuego vista;
        Controlador controlador = new Controlador();
        IVistaEleccion vistaEleccion = new vistaEleccion();
        controlador.setVistaEleccion(vistaEleccion);
        vistaEleccion.setControlador(controlador);

        if (interfaz.equals("Consola Gráfica")) {
            vista = new vistaConsola();
            controlador.setVistaJuego(vista);
        } else {
            vista = new vistaGrafica();
            controlador.setVistaJuego(vista);
        }

        vista.setControlador(controlador);

        Cliente c = new Cliente(ip, portCliente, ipServidor, portServer);

        try {
            c.iniciar(controlador);
            if (reanuda) {
                vistaEleccion.reanudarPartida(controlador.getJugadoresRecuperados());
            } else {
                vistaEleccion.mostrarMenuPrincipal();
            }
        } catch (RemoteException | RMIMVCException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws RemoteException {
        //new ClienteTruco(OpcionesInicio.VISTA_GRAFICA_NO_REANUDAR);
    }
}

