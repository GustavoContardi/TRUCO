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
    private static ArrayList<Jugador>    listaJugadores       ;
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


    public static ArrayList<Jugador> getListaJugadores() {
        try {
            FileInputStream fos = new FileInputStream("jugadores.bin");
            var oos = new ObjectInputStream(fos);
            listaJugadores = (ArrayList<Jugador>) oos.readObject();
            fos.close();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Collections.sort(listaJugadores); // los ordeno por partidas ganadas para el top 5

        return listaJugadores;
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
        this.electo = electo;
    }
    public boolean getElecto(){
        return electo;
    }

}
