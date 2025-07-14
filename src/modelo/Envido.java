package modelo;

import java.io.*;
import java.util.ArrayList;

public class Envido implements Serializable {
    // atributos
    private         int                 puntosEnvido    ;
    private static  ArrayList<Integer>  jerarquiaCartas  = new ArrayList<>();

    // constructor
    public Envido() {
        puntosEnvido = 0;
        recuperarListaJerarquia();
    }

    // metodos publicos

    // calcula los puntos del envido segun que cartas iguales tiene el jugador
    public int calcularPuntosEnvido(ArrayList<Carta> cartasJugador) {
        ArrayList<Carta> cartasIguales;
        recuperarListaJerarquia();

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

    // si tiene flor ===> si la 1 = 2 & 1 = 3 => entonces => 1 = 2 = 3
    public boolean tieneFlor(ArrayList<Carta> cartas){
        return ( ( cartas.get(0).getPaloCarta().equals(cartas.get(1).getPaloCarta()) ) && ( cartas.get(0).getPaloCarta().equals(cartas.get(2).getPaloCarta()) ) );
    }

    // retorna cuantos puntos tiene en caso de contra flor
    public int puntosFlor(ArrayList<Carta> cartas){
        int puntos = 20;

        if(cartas.get(0).getNumeroCarta() < 8) puntos += cartas.get(0).getNumeroCarta();
        if(cartas.get(1).getNumeroCarta() < 8) puntos += cartas.get(1).getNumeroCarta();    // los 10, 11 y 12 suman 0 entonces solo sumo si no son esas cartas
        if(cartas.get(2).getNumeroCarta() < 8) puntos += cartas.get(2).getNumeroCarta();

        return puntos;
    }

    //
    // metodos privados
    //

    // este metodo devuelve un ArrayList con las cartas que tienen el mismo palo
    private ArrayList<Carta> calcularCartasIguales(ArrayList<Carta> lista){
        ArrayList<Carta> iguales = new ArrayList<>();

        if(lista.get(0).getPaloCarta().equals(lista.get(1).getPaloCarta()) && lista.get(0).getPaloCarta().equals(lista.get(2).getPaloCarta())){
            // si las tres son iguales entonces tengo que elegir las dos mas altas
            /*Carta c1 = masAlta(lista);
            Carta c2 = masAlta(lista, c1);
            iguales.add(c1);
            iguales.add(c2);*/

            ArrayList<Carta> ordenadas = cartasOrdenadasPorJerarquia(lista);
            Carta c1 = ordenadas.get(0);
            Carta c2 = ordenadas.get(1);

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

    // devuelve la carta mas alta en la jerarquia
    private Carta masAlta(ArrayList<Carta> lista){
        Carta cartaAlta = null;
        int max = 99;

        for(int i=0; i<lista.size(); i++){
            int indice = jerarquiaCartas.indexOf(lista.get(i).getNumeroCarta());

            if(indice < max && indice != -1) {
                max = indice;
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

    private ArrayList<Carta> cartasOrdenadasPorJerarquia(ArrayList<Carta> cartas) {
        ArrayList<Carta> resultado = new ArrayList<>(cartas);

        // Ordenar según la jerarquía de envido

        resultado.sort((c1, c2) -> Integer.compare(
                jerarquiaCartas.indexOf(c1.getNumeroCarta()),
                jerarquiaCartas.indexOf(c2.getNumeroCarta())
        ));

        return resultado;
    }

    private void recuperarListaJerarquia() {
        try {
            FileInputStream fos = new FileInputStream("listaJerarquiaEnvido.bin");
            var oos = new ObjectInputStream(fos);
            jerarquiaCartas = (ArrayList<Integer>) oos.readObject();
            fos.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


}
