package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import enums.estadoTruco;
import enums.estadoEnvido;

public class Ronda implements Serializable {
    // nose si esto va a ser pero dejo por las dudas

    //
    // atributos
    //

    private   Jugador            j1;
    private   Jugador            j2;
    private   int                numeroMano, numeroRonda, turno;
    private   ArrayList<Carta>   cartasTiradasJ1;
    private   ArrayList<Carta>   cartasTiradasJ2;
    private   int                quienCantoTruco;
    private   int                quienCantoReTruco;
    private   int                hizoPrimera; // ID del jugador
    private   estadoTruco        estadoTruco;
    private   estadoEnvido       estadoEnvido;
    private   boolean            cantoEnvido, cantoEnvidoDoble,cantoRealEnvido, cantoFaltaEnvido;
    private   Anotador           anotador;
    private   int                puntajeRondaJ1, puntajeRondaJ2, puntajeRondaEnvido;
    private   boolean            finMano;


    //
    // constructor
    //

    public Ronda(Jugador j1, Jugador j2, Anotador anotador, int numeroRonda) {
        this.j1            =  j1;
        this.j2            =  j2;
        this.anotador      =  anotador;
        this.numeroRonda   =  numeroRonda;

        nuevaRonda();
    }


    //
    // metodos publicos
    //

    public void nuevaRonda(){
        // seteo los atributos de inicio

        numeroMano          = 1;
        estadoTruco         = enums.estadoTruco.NADA;
        estadoEnvido        = enums.estadoEnvido.NADA;
        puntajeRondaJ1      = 0;
        puntajeRondaJ2      = 0;
        puntajeRondaEnvido  = 0;
        quienCantoTruco     = 0;
        quienCantoReTruco   = 0;
        hizoPrimera         = 0;
        cantoEnvido         = false;
        cantoEnvidoDoble    = false;
        cantoRealEnvido     = false;
        cantoFaltaEnvido    = false;
        cartasTiradasJ1     = new ArrayList<>();
        cartasTiradasJ2     = new ArrayList<>();
        finMano             = false;

        if( (numeroRonda % 2) == 0) turno = j1.getIDJugador();
        else turno = j2.getIDJugador();

    }

    public void finDeLaRonda(){

    }

    public void siguienteTurno(){
        if(turno == j1.getIDJugador()) turno = j2.getIDJugador();
        else turno = j1.getIDJugador();
    }

    public int turnoActual(){
        return turno;
    }



    //
    // metodos privados
    //

    private int compararCartas(Carta cj1, Carta cj2){
        if(cj1.getPoderCarta() > cj2.getPoderCarta()) return j1.getIDJugador();
        else if(cj1.getPoderCarta() < cj2.getPoderCarta()) return j2.getIDJugador();

        return -1; // caso de que sean el mismo poder es PARDA
    }



    //
    // sets y gets
    //


}
