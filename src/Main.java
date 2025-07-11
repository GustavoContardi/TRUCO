import modelo.Jugador;
import modelo.Mazo;
import org.w3c.dom.ls.LSOutput;
import persistencia.PersistenciaJugador;
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

        // ACORDARSE A LO ULTIMO PONER LAS PANTALLAS DE CARGA
        inicio.iniciar();
        inicio2.iniciar();

    }
}


