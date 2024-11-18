package vista;

import interfaces.IVistaInicio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;


public class anotadorGrafico implements Serializable {
    private JPanel ventana;
    private JButton btnSumarJ1;
    private JButton btnRestarJ1;
    private JButton btnSumarJ2;
    private JButton btnRestarJ2;
    private JLabel puntosJ1;
    private JLabel puntosJ2;
    private JLabel JUGADOR1Label;
    private JLabel JUGADOR2Label;
    private JFrame frame;
    private IVistaInicio inicio;

    private Integer puntosJugador1;
    private Integer puntosJugador2;

    public anotadorGrafico(IVistaInicio inicio) {
        this.frame = new JFrame("TRUCONTARDI");
        frame.setContentPane(ventana);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setSize(470, 500);

        this.inicio = inicio;
    }

    public void iniciar(){
        JMenuBar mnuPrincipal = new JMenuBar();
        JMenu mnuArchivo = new JMenu("Archivo");
        mnuPrincipal.add(mnuArchivo);
        JMenuItem mnuiSalir = new JMenuItem("Salir");
        mnuiSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                inicio.iniciar();
            }
        });
        mnuArchivo.add(mnuiSalir);
        frame.setJMenuBar(mnuPrincipal);

        puntosJugador1 = 0;
        puntosJugador2 = 0;

        btnSumarJ1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(puntosJugador1 < 30){
                    puntosJugador1 += 1;
                    puntosJ1.setText(puntosJugador1.toString());
                }
            }
        });

        btnRestarJ1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(puntosJugador1 > 0){
                    puntosJugador1 -= 1;
                    puntosJ1.setText(puntosJugador1.toString());
                }
            }
        });

        btnSumarJ2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(puntosJugador2 < 30){
                    puntosJugador2 += 1;
                    puntosJ2.setText(puntosJugador2.toString());
                }
            }
        });
        btnRestarJ2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(puntosJugador2 > 0){
                    puntosJugador2 -= 1;
                    puntosJ2.setText(puntosJugador2.toString());
                }
            }
        });
        frame.setVisible(true);
    }

    public void salir(){
        //frame.setVisible(false);
        frame.dispose();
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
