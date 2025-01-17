package vista;

import javax.swing.*;
import java.awt.*;

public class PantallaCarga extends JWindow {

    private JProgressBar barraProgreso;

    public PantallaCarga() {
        // Configuración del JWindow
        setSize(500, 450);
        setLocationRelativeTo(null); // Centrar en la pantalla

        // Crear un panel para la pantalla de carga
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel etiquetaImagen = new JLabel();
        etiquetaImagen.setHorizontalAlignment(JLabel.CENTER);
        etiquetaImagen.setVerticalAlignment(JLabel.CENTER);


        ImageIcon iconoOriginal = new ImageIcon("icono.jpeg");
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(
                this.getWidth(), this.getHeight() - 50, // Tamaño ajustado al JWindow
                Image.SCALE_SMOOTH // Escalado suave para mejor calidad
        );
        etiquetaImagen.setIcon(new ImageIcon(imagenEscalada));
        panel.add(etiquetaImagen, BorderLayout.CENTER);

        // Etiqueta para el logo o mensaje
        JLabel etiqueta = new JLabel("Cargando la aplicación...", JLabel.CENTER);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(etiqueta, BorderLayout.NORTH);

        // Barra de progreso
        barraProgreso = new JProgressBar(0, 100);
        barraProgreso.setStringPainted(true);
        barraProgreso.setValue(0);
        panel.add(barraProgreso, BorderLayout.SOUTH);



        // Agregar el panel al JWindow
        add(panel);
    }

    public void mostrarPantalla() {
        setVisible(true);

        // Simular carga (puedes reemplazar esto con tus procesos reales)
        for (int i = 0; i <= 100; i++) {
            try {
                Thread.sleep(50); // Simular tiempo de carga
                barraProgreso.setValue(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Cierro la pantalla de carga y abro la aplicacion
        dispose();
    }


}
