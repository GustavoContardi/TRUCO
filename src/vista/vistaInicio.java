package vista;

import controlador.Controlador;
import interfaces.IControlador;
import interfaces.IVistaEleccion;
import interfaces.IVistaInicio;
import interfaces.IVistaJuego;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class vistaInicio implements IVistaInicio {

    private JPanel ventana;
    private JLabel tituloLabel;
    private JButton btnIniciarNueva;
    private JButton btnReanudar;
    private JButton btnTopFive;
    private JButton btnSalir;
    private JButton btnAnotador;
    private JFrame frame;
    private anotadorGrafico anotadorG;
    private IControlador controlador;

    //
    //  constructor
    //

    public vistaInicio() {
        this.frame = new JFrame("TRUCONTARDI");
        frame.setContentPane(ventana);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(450, 500);
        frame.setVisible(true);
        anotadorG = new anotadorGrafico(this);

        setBotonesInicio();

        controlador = new Controlador();

    }

    //
    //  metodos publicos
    //

    public void setBotonesInicio(){
        eliminarTodosAcLis();

        btnSalir.setText(" SALIR ");
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
                anotadorG.iniciar();
            }
        });
    }

    public void setBotonesIniciar(){
        eliminarTodosAcLis();

        btnIniciarNueva.setVisible(true);
        btnIniciarNueva.setText("INICIAR NUEVA PARTIDA");
        btnReanudar.setText("  INGRESAR A PARTIDA  ");
        btnSalir.setText("   VOLVER   ");

        btnIniciarNueva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botonesElegirVista();
            }
        });
        btnReanudar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botonesElegirVista();
            }
        });

        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBotonesInicio();
            }
        });

    }

    public void botonesElegirVista(){
        eliminarTodosAcLis();

        btnSalir.setText(" VOLVER ");
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBotonesIniciar();
            }
        });

        btnIniciarNueva.setText(" VISTA GRAFICA ");
        btnIniciarNueva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IVistaEleccion vistaEleccion = new vistaEleccion();
                controlador.setVistaEleccion(vistaEleccion);
                vistaEleccion.setControlador(controlador);
                vistaEleccion.mostrarMenuPrincipal();

                frame.setVisible(false);
                IVistaJuego vistaJuego = new vistaGrafica();
                vistaJuego.setControlador(controlador);
            }
        });

        btnReanudar.setText(" VISTA CONSOLA ");
        btnReanudar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IVistaEleccion vistaEleccion = new vistaEleccion();
                controlador.setVistaEleccion(vistaEleccion);
                vistaEleccion.setControlador(controlador);
                vistaEleccion.mostrarMenuPrincipal();
                frame.setVisible(false);
                IVistaJuego vistaJuego = new vistaConsola();
                vistaJuego.setControlador(controlador);
            }
        });

    }

    public void iniciar(){
        frame.setVisible(true);
    }

    public void salir(){
        frame.setVisible(false);
    }

    //
    //  metodos privados
    //

    private void removeAllActionListeners(AbstractButton button) {
        ActionListener[] listeners = button.getActionListeners();
        for (ActionListener listener : listeners) {
            button.removeActionListener(listener);
        }
    }

    private void eliminarTodosAcLis(){
        removeAllActionListeners(btnAnotador);
        removeAllActionListeners(btnSalir);
        removeAllActionListeners(btnIniciarNueva);
        removeAllActionListeners(btnTopFive);
        removeAllActionListeners(btnReanudar);
    }
}
