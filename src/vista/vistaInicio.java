package vista;

import javax.swing.*;

public class vistaInicio {
    private JPanel ventana;
    private JLabel tituloLabel;
    private JButton DOWNPELOTUDSSSPButton;
    private JButton SALIRButton;
    private JButton RECUPERARPARTIDAButton;
    private JButton TABLADEJUGADORESButton;
    private JButton INICIARPARTIDAButton;
    private JFrame frame;

    public vistaInicio() {
        this.frame = new JFrame("TRUCONTARDI");
        frame.setContentPane(ventana);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 400);
        frame.setVisible(true);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
