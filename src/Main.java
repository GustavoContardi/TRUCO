import ar.edu.unlu.rmimvc.Util;
import modelo.Carta;
import modelo.Envido;
import modelo.Jugador;
import modelo.Mazo;
import org.w3c.dom.ls.LSOutput;
import persistencia.PersistenciaJugador;
import vista.VistaConsola;
import vista.VistaGrafica;
import vista.VistaInicio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;

import static javax.swing.text.StyleConstants.getBackground;

public class Main {
    public static void main(String[] args) throws RemoteException {
        // MAIN \\

        PersistenciaJugador.delvolverTodosJugadores();
        VistaInicio inicio = new VistaInicio();
        VistaInicio inicio2 = new VistaInicio();

        /*Carta c1 = new Carta(12, "Basto", 1, 1);
        Carta c2 = new Carta(7, "Oro", 1, 2);
        Carta c3 = new Carta(4, "Espada", 1, 3);

        ArrayList<Carta> cartas = new ArrayList<>();
        cartas.add(c1);
        cartas.add(c2);
        cartas.add(c3);

        Envido envido = new Envido();

        int puntos = envido.calcularPuntosEnvido(cartas);
        System.out.println("Puntos envido: " + puntos);*/


        // ACORDARSE A LO ULTIMO PONER LAS PANTALLAS DE CARGA
        inicio.iniciar();
        inicio2.iniciar();


       /* JDialog dialog = new JDialog();
        dialog.setModal(false); // NO bloquea la app
        dialog.setTitle("Puerto del Servidor - App Truco ");
        dialog.setLayout(new FlowLayout());
        //dialog.setIconImage(icono.getImage());

        ArrayList<String> ips = Util.getIpDisponibles();
        JTextField campoPuerto = new JTextField("", 10);
        JComboBox comboBox = new JComboBox();
        for(String ip : ips) {
            comboBox.addItem(ip);
        }

        dialog.add(new JLabel("Seleccione la IP:"));
        dialog.add(comboBox);
        //dialog.add(campoPuerto);

        JButton aceptar = new JButton("Aceptar");
        aceptar.addActionListener(e -> {
            String ip = (String) comboBox.getSelectedItem();
            dialog.dispose();
        });
        dialog.add(aceptar);

        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        */
    }
}


