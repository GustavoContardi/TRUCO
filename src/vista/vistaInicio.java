package vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class vistaInicio {
    private JPanel ventana;
    private JLabel tituloLabel;
    private JButton btnIniciarNueva;
    private JButton btnReanudar;
    private JButton btnTopFive;
    private JButton btnSalir;
    private JButton btnAnotador;
    private JFrame frame;
    private anotadorGrafico anotadorG = new anotadorGrafico(this);

    public vistaInicio() {
        this.frame = new JFrame("TRUCONTARDI");
        frame.setContentPane(ventana);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(450, 500);
        frame.setVisible(true);

        setBotonesInicio();
    }

    public void setBotonesInicio(){


        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnIniciarNueva.setVisible(false);

        btnReanudar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBotonesIniciar();
            }
        });

        btnAnotador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                //anotadorGrafico.inciar();
            }
        });
    }

    public void setBotonesIniciar(){



        btnIniciarNueva.setVisible(true);
        btnIniciarNueva.setText("INICIAR NUEVA PARTIDA");
        btnReanudar.setText("  REANUDAR PARTIDA  ");


    }

    public void iniciar(){
        frame.setVisible(true);
    }

    public void salir(){
        frame.setVisible(false);
    }

}
