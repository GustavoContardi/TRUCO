package vista.flujos;

import controlador.Controlador;
import vista.vistaGrafica;

public abstract class Flujo {
    protected final vistaGrafica vista;
    protected final Controlador controlador;

    public Flujo(vistaGrafica vista, Controlador controlador) {
        this.vista = vista;
        this.controlador = controlador;
    }

    public abstract Flujo procesarEntrada(String string);

    public abstract void mostrarSiguienteTexto();
}
