package modelo;

import enums.EstadoEnvido;
import enums.EstadoFlor;

import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Envido implements Serializable {
    // atributos
    private         int                 puntosEnvido;
    private         int                 quienCantoEnvido, quienCantoEnvidoDoble, quienCantoRealEnvido, quienCantoFaltaEnvido;
    private         int                 quienCantoFlor;
    private         boolean             cantoEnvido, cantoEnvidoDoble,cantoRealEnvido, cantoFaltaEnvido;
    private         EstadoEnvido        estadoEnvido;
    private         EstadoFlor          estadoDeLaFlor;
    private static  ArrayList<Integer>  jerarquiaCartas  = new ArrayList<>();

    // constructor
    public Envido() {
        puntosEnvido = 0;
        recuperarListaJerarquia();
    }

    //
    // metodos publicos
    //

    // este metodo se ejecuta cuando hay una mano nueva y reseteo los valores
    public void resetValores(){
        estadoEnvido            =   EstadoEnvido.NADA;
        estadoDeLaFlor          =   EstadoFlor.FLOR;
        cantoEnvido             =   false;
        cantoEnvidoDoble        =   false;
        cantoRealEnvido         =   false;
        cantoFaltaEnvido        =   false;
        quienCantoFlor          =   0;
        quienCantoEnvido        =   0;
        quienCantoEnvidoDoble   =   0;
        quienCantoRealEnvido    =   0;
        quienCantoFaltaEnvido   =   0;
        puntosEnvido            =   0;
    }

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

    public int calcularPuntajeEnvidoQuerido(int puntosMaximos, int puntosJ1, int puntosJ2) throws RemoteException {
        int puntos = 0;

        // si algun jugador esta en las "malas" (< 15 puntos) el falta envido es por el partido, sino es por los puntos que le faltan al que mas cerca este de ganar
        if(cantoFaltaEnvido){
            if(puntosJ1 < 15 && puntosJ2 < 15) puntos = 30;
            else puntos = puntosMaximos;

            return puntos;
        }

        if(cantoEnvido){
            puntos += 2;
            if(cantoEnvidoDoble){
                puntos += 2;
                if(cantoRealEnvido){
                    puntos += 3;
                }
            }
            else if(cantoRealEnvido){
                puntos += 3;
            }
        }
        else if(cantoRealEnvido){
            puntos += 3;
        }

        return puntos;
    }

    public int calcularEnvidoNoQuerido() throws RemoteException{
        int puntos = 0;

        if(cantoEnvido){
            puntos += 1;
            if(cantoEnvidoDoble){
                puntos += 1;
                if(cantoRealEnvido){
                    puntos += 2;
                    if(cantoFaltaEnvido) puntos += 3;
                }
                else if(cantoFaltaEnvido) puntos += 2;
            }
            else if (cantoRealEnvido){
                puntos += 1;
                if(cantoFaltaEnvido) puntos += 3;
            }
            else if(cantoFaltaEnvido) puntos += 1;
        }
        else if(cantoRealEnvido){
            puntos += 1;
            if(cantoFaltaEnvido) puntos += 2;
        }
        else puntos = 1;

        return puntos;
    }

    public int calcularPuntajeFlorQuerida(int puntosParaGanar){
        int puntos = 0;

        switch (estadoDeLaFlor){
            case FLOR -> puntos = 3;
            case CONTRA_FLOR -> puntos = 6;
            case CONTRA_FLOR_AL_RESTO  -> puntos = puntosParaGanar;
        }

        return puntos;
    }

    public int calcularPuntajeFlorNoQuerida(){
        int puntos = 0;

        switch (estadoDeLaFlor){
            case FLOR -> puntos = 3;
            case CONTRA_FLOR -> puntos = 4;
            case CONTRA_FLOR_AL_RESTO  -> puntos = 6;
        }

        return puntos;
    }

    public void seCantoEnvido(){
        cantoEnvido = true;
    }

    public void seCantoEnvidoDoble(){
        cantoEnvidoDoble = true;
    }

    public void seCantoRealEnvido(){
        cantoRealEnvido = true;
    }

    public void seCantoFaltaEnvido(){
        cantoFaltaEnvido = true;
    }

    //
    // sets y gets
    //

    public int getQuienCantoEnvido() {
        return quienCantoEnvido;
    }

    public int getQuienCantoEnvidoDoble() {
        return quienCantoEnvidoDoble;
    }

    public int getQuienCantoRealEnvido() {
        return quienCantoRealEnvido;
    }

    public int getQuienCantoFaltaEnvido() {
        return quienCantoFaltaEnvido;
    }

    public boolean getCantoEnvido() {
        return cantoEnvido;
    }

    public boolean getCantoEnvidoDoble() {
        return cantoEnvidoDoble;
    }

    public boolean getCantoRealEnvido() {
        return cantoRealEnvido;
    }

    public boolean getCantoFaltaEnvido() {
        return cantoFaltaEnvido;
    }

    public EstadoEnvido getEstadoEnvido() {
        return estadoEnvido;
    }

    public int getQuienCantoFlor(){
        return quienCantoFlor;
    }

    public EstadoFlor getEstadoDeLaFlor(){
        return estadoDeLaFlor;
    }

    public void setQuienCantoEnvido(int quienCantoEnvido) {
        this.quienCantoEnvido = quienCantoEnvido;
    }

    public void setQuienCantoEnvidoDoble(int quienCantoEnvidoDoble) {
        this.quienCantoEnvidoDoble = quienCantoEnvidoDoble;
    }

    public void setQuienCantoRealEnvido(int quienCantoRealEnvido) {
        this.quienCantoRealEnvido = quienCantoRealEnvido;
    }

    public void setQuienCantoFaltaEnvido(int quienCantoFaltaEnvido) {
        this.quienCantoFaltaEnvido = quienCantoFaltaEnvido;
    }

    public void setEstadoEnvido(EstadoEnvido estadoEnvido) {
        this.estadoEnvido = estadoEnvido;
    }

    public void setQuienCantoFlor(int quienCantoFlor){
        this.quienCantoFlor = quienCantoFlor;
    }

    public void setEstadoDeLaFlor(EstadoFlor estado){
        estadoDeLaFlor = estado;
    }

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
            FileInputStream fos = new FileInputStream("src/recursos/archivo/listaJerarquiaEnvido.bin");
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
