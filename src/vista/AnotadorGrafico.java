package vista;

import interfaces.IVistaInicio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;


public class AnotadorGrafico implements Serializable {
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
    private VistaInicio inicio;
    private Image icono;

    private Integer puntosJugador1;
    private Integer puntosJugador2;

    public AnotadorGrafico(VistaInicio inicio) {
        this.frame = new JFrame("App Truco");
        frame.setLocationRelativeTo(null);
        frame.setContentPane(ventana);
        frame.pack();
        frame.setResizable(false);
        frame.setSize(470, 500);

        this.inicio = inicio;

        initIcono();
        frame.setIconImage(icono);
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



    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    private void initIcono() {
        icono = new ImageIcon("src/recursos/imagen/icono.jpeg").getImage();
        Image originalImage = icono;
        Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icono = new ImageIcon(scaledImage).getImage();
    }
}
