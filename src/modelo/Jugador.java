package modelo;

import persistencia.Persistencia;
import persistencia.PersistenciaJugador;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;
import java.util.Comparator;

public class Jugador implements Comparable<Jugador>, Serializable{
    private  String                      nombre               ;
    private  int                         IDJugador            ;
    private  ArrayList<Carta>            cartasObtenidas      ;
    private  int                         partidasGanadas      ;
    private  boolean                     electo               ;
    private  Envido                      envido               ;

    //
    // constructor
    //

    public Jugador(String nombre) {
        this.nombre = nombre;
        partidasGanadas = 0;
        electo = false;
        envido = new Envido();
        IDJugador = generarID();
        PersistenciaJugador.guardarJugador(this);
    }

    //
    // metodos publicos
    //

    // aca solo tiro una carta para la ronda
    public Carta tirarCarta(int carta){

        for(int i=0; i<cartasObtenidas.size(); i++){
            if(cartasObtenidas.get(i).getIdCarta() == carta) {
                cartasObtenidas.get(i).seTiroCarta();
                return cartasObtenidas.get(i);
            }
        }

        return null;
    }

    public int puntosEnvido(){
        return envido.calcularPuntosEnvido(this);
    }

    public void recibirCartas(ArrayList<Carta> lista){
        cartasObtenidas = lista;
    }

    public void devolverCartas(){
        if(cartasObtenidas != null) {
            for(Carta carta : cartasObtenidas){ // seteo el tirado de las cartas en falso para que se puedan volver a tirar
                carta.setFueTirada(false);
            }
            cartasObtenidas.clear();
        }
    }
    public void sumarPartidaGanada(){
        PersistenciaJugador.sumarPartidaGanadaJugador(IDJugador);
    }


    @Override
    public String toString(){
        if (electo) return nombre + " | " + "ID: " + IDJugador + " | WINS: " + partidasGanadas + " | NO DISPONIBLE (ELEGIDO)";
        return nombre + " | " + "ID: " + IDJugador + " | WINS: " + partidasGanadas;
    }

    //
    // metodos privados
    //

    private int generarID(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDate = now.format(formatter);
        String idString = nombre.substring(0, Math.min(3, nombre.length())).toUpperCase() + formattedDate;

        return Math.abs(idString.hashCode());
    }


    @Override
    public int compareTo(Jugador otroJugador) {
        return Integer.compare(otroJugador.partidasGanadas, this.partidasGanadas);
    }

    //
    // GETTERS y SETTERS
    //

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIDJugador() {
        return IDJugador;
    }

    public ArrayList<Carta> getCartasObtenidas() {
        return cartasObtenidas;
    }

    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    public void setElecto(boolean electo){
        PersistenciaJugador.jugadorElecto(IDJugador);
    }
    public boolean getElecto(){
        return electo;
    }

    public void jugadorFueElecto(){
        this.electo = true;
    }

    public void partidaGanada(){
        partidasGanadas += 1;
    }

    public void jugadorFueDevuelto(){
        this.electo = false;
    }
}
