package modelo;

import persistencia.Persistencia;

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
        IDJugador = generarID();
        Persistencia.guardarJugador(this);
    }

    //
    // metodos publicos
    //

    // aca solo tiro una carta para la ronda
    public Carta tirarCarta(int carta){
        Carta cartaTirada = null;

        for(int i=0; i<cartasObtenidas.size(); i++){
            if(cartasObtenidas.get(i).getIdCarta() == carta) {
                cartasObtenidas.get(i).seTiroCarta();
                cartaTirada = cartasObtenidas.get(i);
            }
        }

        return cartaTirada;
    }

    public int puntosEnvido(){
        return envido.calcularPuntosEnvido(this);
    }

    public void recibirCartas(ArrayList<Carta> lista){
        cartasObtenidas = lista;
    }

    public void devolverCartas(){
        cartasObtenidas.clear();
    }

    public ArrayList<Jugador> listaJugadoresGuardados(){
        return null;
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

    public void setIDJugador(int IDJugador) {
        this.IDJugador = IDJugador;
    }

    public ArrayList<Carta> getCartasObtenidas() {
        return cartasObtenidas;
    }

    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    public void setPartidasGanadas(int partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    public void setElecto(boolean electo){
        Persistencia.jugadorElecto(IDJugador);
    }
    public boolean getElecto(){
        return electo;
    }

    public void jugadorFueElecto(){
        this.electo = true;
    }

    public void jugadorFueDevuelto(){
        this.electo = false;
    }
}
