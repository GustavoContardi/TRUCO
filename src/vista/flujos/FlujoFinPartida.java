package vista.flujos;

import controlador.Controlador;
import interfaces.IControlador;
import vista.vistaConsola;
import vista.vistaGrafica;

import java.io.Serializable;

public class FlujoFinPartida extends Flujo implements Serializable {


    public FlujoFinPartida(vistaConsola vista, IControlador controlador) {
        super(vista, controlador);
    }

    @Override
    public Flujo procesarEntrada(String string) {

        vista.salirDelJuego();

        return this; // que retorne cualquier cosa si total va a cerrar la ventana con dispose()
    }

    @Override
    public void mostrarSiguienteTexto() {
        vista.println("\nIngrese cualquier caracter para volver al menu principal...");
    }
}
