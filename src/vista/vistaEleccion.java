package vista;

import javax.swing.*;

public class vistaEleccion {
    private JPanel ventana;
    private JTabbedPane tabbedPane1;
    private JPanel JUGADORES;
    private JPanel CREARNUEVO;
    private JList list1;
    private JComboBox comboBox1;
    private JButton ELEGIRButton;
    private JTextField textRegistrar;
    private JButton CREARJUGADORButton;
    private final JFrame frame;

    public vistaEleccion() {
        this.frame = new JFrame("TRUCONTARDI");
        frame.setContentPane(ventana);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 450);

        frame.setVisible(true);
    }
}
