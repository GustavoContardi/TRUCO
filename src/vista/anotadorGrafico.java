package vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class anotadorGrafico {
    private JPanel ventana;
    private JButton btnSumarJ1;
    private JButton btnRestarJ1;
    private JButton btnSumarJ2;
    private JButton btnRestarJ2;
    private JLabel puntosJ1;
    private JLabel puntosJ2;
    private JFrame frame;

    private Integer puntosJugador1;
    private Integer puntosJugador2;

    public anotadorGrafico(vistaInicio inicio) {
        this.frame = new JFrame("TRUCONTARDI");
        frame.setContentPane(ventana);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(450, 500);
        frame.setVisible(false);

        JMenuBar mnuPrincipal = new JMenuBar();
        JMenu mnuArchivo = new JMenu("Archivo");
        mnuPrincipal.add(mnuArchivo);
        JMenuItem mnuiSalir = new JMenuItem("Salir");
        mnuiSalir.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false);
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
    }

    public void iniciar(){
        frame.setVisible(true);
    }

    public void salir(){
        frame.setVisible(false);
    }


}
