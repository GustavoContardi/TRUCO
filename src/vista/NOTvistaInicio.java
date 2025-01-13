package vista;

import interfaces.IVistaInicio;

import javax.swing.*;


public class NOTvistaInicio extends JFrame implements IVistaInicio {

    private JPanel ventana;
    private JLabel tituloLabel;
    private JButton btnIniciarNueva;
    private JButton btnReanudar;
    private JButton btnAnotador;
    private JButton btnSalir;
    private JButton btnReglas;
    private JLabel instrucciones;
    private JButton btnTop;
    private anotadorGrafico anotadorG;

    //
    //  constructor
    //

    public NOTvistaInicio() {
        setSize(500, 500);
        setContentPane(ventana);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("TRUCONTARDI - MENU PRINCIPAL");
        anotadorG = new anotadorGrafico(this);


        // Eventos

    }
    //
    //  metodos publicos
    //


    public void iniciar(){
        setVisible(true);
    }

    public void salir(){
        setVisible(false);
    }
//
//    //
//    //  metodos privados
//    //
//
//    private void abrirURL(String url){
//        if (Desktop.isDesktopSupported()) {
//            try {
//                Desktop.getDesktop().browse(new URI(url));
//            } catch (IOException | URISyntaxException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void pantallaTopJugadores(){
//        JFrame frame2 = new JFrame("TRUCONTARDI");
//        frame2.setResizable(false);
//        frame2.setSize(500, 600);
//        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        JPanel ventana = new JPanel();
//        ventana.setLayout(new BorderLayout()); // Usar BorderLayout para que el JScrollPane ocupe todo el espacio
//        JList<String> list = new JList<>();
//        DefaultListModel<String> listModel = new DefaultListModel<>();
//        list.setModel(listModel);
//        JScrollPane scroll = new JScrollPane(list); // Agregar el JList al JScrollPane
//
//        frame2.setContentPane(ventana);
//        ventana.add(scroll, BorderLayout.CENTER); // Agregar el JScrollPane al centro del BorderLayout
//
//        for (Jugador j : PersistenciaJugador.listaJugadoresGuardados(true)) {
//            if(j != null) listModel.addElement(j.toString());
//        }
//
//        frame2.setVisible(true);
//    }
//


}
