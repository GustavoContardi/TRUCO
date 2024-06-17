package vista;

import javax.swing.*;

public class vistaGrafica {
    private JPanel ventana;
    private JButton ENVIDOButton;
    private JButton TRUCOButton;
    private JButton IRALMAZOButton;
    private JButton NOQUIEROButton;
    private JPanel panelCartasYo;
    private JPanel panelCartasOp;
    private JPanel CartasOP1;
    private JPanel CartasOP2;
    private JPanel CartasOP3;
    private JPanel CartasYo1;
    private JPanel CartasYo2;
    private JPanel CartasYo3;
    private JButton QUIEROButton;
    private JButton btnCarta1;
    private JButton btnCarta2;
    private JButton btnCarta3;
    private JLabel accionesJ2;
    private JLabel puntajesLabel;
    private JFrame frame;

    public vistaGrafica() {
        this.frame = new JFrame("TRUCONTARDI");
        frame.setContentPane(ventana);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(900, 650);
        frame.setVisible(true);
    }
}
