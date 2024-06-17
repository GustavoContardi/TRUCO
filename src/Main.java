import interfaces.iModelo;
import interfaces.iVistaInicio;
import modelo.*;
import vista.vistaConsola;
import vista.vistaEleccion;
import vista.vistaGrafica;
import vista.vistaInicio;

import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
       /* Jugador j1 = new Jugador("Lito");
        Jugador j2 = new Jugador("Pepe");
        Carta c1 = new Carta(1, "Espada", 100);
        Carta c2 = new Carta(1, "Basto", 99);
        Carta c3 = new Carta(1, "Oro", 11);

        System.out.println(c1.toString());
        System.out.println(c2.toString());
        System.out.println(c3.toString());
        System.out.println();

        Mazo mazo = new Mazo();
        //mazo.mostrarMazo();

        mazo.repartirCartas(j1, j2);

        ArrayList<Carta> cartasj1 = j1.getCartasObtenidas();
        ArrayList<Carta> cartasj2 = j2.getCartasObtenidas();

        System.out.println("Jugador Lito");
        for (int i = 0; i < cartasj1.size(); i++) {
            System.out.println(cartasj1.get(i).toString());
        }

        System.out.println("\n");

        System.out.println("Jugador Pepe");
        for (int i = 0; i < cartasj2.size(); i++) {
            System.out.println(cartasj2.get(i).toString());
        }

        System.out.println();

        Envido envido = new Envido();
        int puntosLito = envido.calcularPuntosEnvido(j1);
        System.out.printf("Lito tiene %d tantos", puntosLito);




        Mazo mazo = new Mazo();


        Jugador j1 = new Jugador("wos");
        Jugador j2 = new Jugador("duki");
        Anotador anotador = new Anotador(j1.getNombre(), j2.getNombre());

        System.out.println("prueba inicio: " + anotador.toString());

        Ronda ronda = new Ronda(j1, j2, anotador);
        ronda.pruebaAnotador();

        System.out.println("prueba fin: " + anotador.toString());

*/
        //vistaInicio inicio = new iVistaInicio();

        vistaInicio vista = new vistaInicio();

    }
}