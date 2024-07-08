package modelo;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Envido implements Serializable {
    // atributos
    private         int                 puntosEnvido    ;
    private static  ArrayList<Integer>  jerarquiaCartas  = new ArrayList<>();

    // constructor
    public Envido() {
        puntosEnvido = 0;
        if (jerarquiaCartas.isEmpty()) {
            jerarquiaCartas.add(7);
            jerarquiaCartas.add(6);
            jerarquiaCartas.add(5);
            jerarquiaCartas.add(4);
            jerarquiaCartas.add(3);
            jerarquiaCartas.add(2);
            jerarquiaCartas.add(1);
            jerarquiaCartas.add(12);
            jerarquiaCartas.add(11);
            jerarquiaCartas.add(10);
        }
    }

    // metodos publicos

    public int calcularPuntosEnvido(Jugador j) {
        ArrayList<Carta> cartasIguales;
        ArrayList<Carta> cartasJugador = j.getCartasObtenidas();
        puntosEnvido = 0; // para resetear cada vez que le pido los puntos

        cartasIguales = calcularCartasIguales(cartasJugador);
        if (cartasIguales.size() == 1) {
            puntosEnvido = cartasIguales.get(0).getNumeroCarta();
        }
        else {
            if ((cartasIguales.get(0).getNumeroCarta() >= 10) && (cartasIguales.get(1).getNumeroCarta() >= 10)) {
                puntosEnvido = 20;
            } else if (cartasIguales.get(0).getNumeroCarta() >= 10 || cartasIguales.get(1).getNumeroCarta() >= 10) {
                puntosEnvido = 20 + masAlta(cartasIguales).getNumeroCarta();
            } else {
                puntosEnvido = cartasIguales.get(0).getNumeroCarta() + cartasIguales.get(1).getNumeroCarta() + 20;
            }
        }
        return puntosEnvido;
    }

    //
    // metodos privados
    //

    private ArrayList<Carta> calcularCartasIguales(ArrayList<Carta> lista){
        ArrayList<Carta> iguales = new ArrayList<>();

        if(lista.get(0).getPaloCarta().equals(lista.get(1).getPaloCarta()) && lista.get(0).getPaloCarta().equals(lista.get(2).getPaloCarta())){
            // si las tres son iguales entonces tengo que elegir las dos mas altas
            Carta c1 = masAlta(lista);
            Carta c2 = masAlta(lista, c1);
            iguales.add(c1);
            iguales.add(c2);
        }
        else if(lista.get(0).getPaloCarta().equals(lista.get(1).getPaloCarta())){
            iguales.add(lista.get(0));
            iguales.add(lista.get(1));
        }
        else if(lista.get(2).getPaloCarta().equals(lista.get(1).getPaloCarta())){
            iguales.add(lista.get(2));
            iguales.add(lista.get(1));
        }
        else if(lista.get(0).getPaloCarta().equals(lista.get(2).getPaloCarta())) {
            iguales.add(lista.get(0));
            iguales.add(lista.get(2));
        }
        else{ // basicamente si no tiene cartas iguales le retorno la mas alta
            iguales.add(masAlta(lista));
        }

        return iguales;
    }

    private Carta masAlta(ArrayList<Carta> lista){
        Carta cartaAlta = null;
        int max = 99;

        for(int i=0; i<lista.size(); i++){
            if(jerarquiaCartas.indexOf(lista.get(i).getNumeroCarta()) < max) {
                max = jerarquiaCartas.indexOf(lista.get(i).getNumeroCarta());
                cartaAlta = lista.get(i);
            }
        }

        return cartaAlta;
    }

    // que me elija la carta mas alta pero que no sea la carta que se pasa por parametro
    private Carta masAlta(ArrayList<Carta> lista, Carta noElegir){
        Carta cartaAlta = null;
        int max = 99;

        for(int i=0; i<lista.size(); i++){
            if( (jerarquiaCartas.indexOf(lista.get(i).getNumeroCarta()) < max) && (noElegir.getIdCarta() != lista.get(i).getIdCarta()) ) {
                max = jerarquiaCartas.indexOf(lista.get(i).getNumeroCarta());
                cartaAlta = lista.get(i);
            }
        }

        return cartaAlta;
    }


}
