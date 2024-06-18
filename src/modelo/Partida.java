package modelo;

import enums.estadoEnvido;
import enums.estadoTruco;
import interfaces.iControlador;
import interfaces.iModelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Partida implements Serializable, iModelo {
    //
    // atributos
    //

    private     Jugador             j1;
    private     Jugador             j2;
    private     Mazo                mazo;
    private     Ronda               ronda;
    private     int                 idPartida;
    private     Anotador            anotador;
    private     int                 numeroMano, numeroRonda, turno;
    private     ArrayList<Carta>    cartasTiradasJ1;
    private     ArrayList<Carta>    cartasTiradasJ2;
    private     int                 quienCantoTruco;
    private     int                 quienCantoReTruco;
    private     int                 hizoPrimera; // ID del jugador
    private     estadoTruco         estadoDelTruco;
    private     estadoEnvido        estadoDelEnvido;
    private     boolean             cantoEnvido, cantoEnvidoDoble,cantoRealEnvido, cantoFaltaEnvido;
    private     int                 puntajeRondaJ1, puntajeRondaJ2, puntajeRondaEnvido;
    private     boolean             finMano;
    private     int                 nroRondasGanadasJ1, nroRondasGanadasJ2;
    private     boolean             parda;

    private     iControlador        controlador;

    //
    // constructor
    //

    public Partida() {
        j1 = null;
        j2 = null;
        numeroMano=0;
        numeroRonda=1;
        mazo = new Mazo();
        //nuevaPartida();
    }


    //
    // metodos publicos
    //

    public void nuevaPartida(){
        nuevaRonda();
        notificarPartidaLista();
    }

    @Override
    public void nuevaRonda() {
        // seteo los atributos de inicio
        if(numeroMano == 0) {
            anotador = new Anotador(j2.getNombre(), j1.getNombre());
        }
        if (finMano){
            if(esFinDePartida()){
                numeroMano          = 1;
                estadoDelTruco = estadoDelTruco.NADA;
                estadoDelEnvido = estadoDelEnvido.NADA;
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
                nroRondasGanadasJ1  = 0;
                nroRondasGanadasJ2  = 0;
                parda               = false;

                if( (numeroMano % 2) == 0) turno = j1.getIDJugador();
                else turno = j2.getIDJugador();
                mazo.repartirCartas(j1, j2);
            }
            else finDePartida();
        }
    }

    @Override
    public void finDeLaRonda() {

    }

    @Override
    public void siguienteTurno() {
        if(turno == j1.getIDJugador()) turno = j2.getIDJugador();
        else turno = j1.getIDJugador();
    }

    @Override
    public int turnoActual() {
        return turno;
    }

    @Override
    public Carta tirarCarta(int idJugador, int idCarta) {
        Carta c = null;
        if(idJugador == j1.getIDJugador() && turno == idJugador){
            c = j1.tirarCarta(idCarta);
            if(numeroRonda == 1) cartasTiradasJ1.add(c);
            else if(numeroRonda == 2) cartasTiradasJ1.add(c);
            else if(numeroRonda == 3) cartasTiradasJ1.add(c);
            siguienteTurno();
        }
        else if(idJugador == j2.getIDJugador() && idJugador == turno){
            c = j2.tirarCarta(idCarta);
            if(numeroRonda == 1) cartasTiradasJ2.add(c);
            else if(numeroRonda == 2) cartasTiradasJ2.add(c);
            else if(numeroRonda == 3) cartasTiradasJ2.add(c);
            siguienteTurno();
        }

        // notificarCartaTirada(id, c); // notifico la carta tirada y despues sigo \\

        if((cartasTiradasJ1.size() == cartasTiradasJ2.size()) && !cartasTiradasJ2.isEmpty()) {
            if (!cantoEnvido) cantoEnvido = true;
            if (numeroRonda == 1) {
                if (j1.getIDJugador() == compararCartas(cartasTiradasJ1.get(0), cartasTiradasJ2.get(0))) {
                    nroRondasGanadasJ1 += 1;
                    hizoPrimera = j1.getIDJugador();
                    turno = j1.getIDJugador();
                } else if (j2.getIDJugador() == compararCartas(cartasTiradasJ1.get(0), cartasTiradasJ2.get(0))) {
                    nroRondasGanadasJ2 += 1;
                    hizoPrimera = j2.getIDJugador();
                    turno = j2.getIDJugador();
                } else { // si es null/-1 es porque es parda
                    nroRondasGanadasJ2 += 1;
                    nroRondasGanadasJ1 += 1;
                    parda = true;
                }
                numeroRonda += 1;

            } else if (numeroRonda == 2) {
                if (parda) {
                    if (j1.getIDJugador() == compararCartas(cartasTiradasJ1.get(1), cartasTiradasJ2.get(1))) {
                        nroRondasGanadasJ1 += 1;

                    } else if (j2.getIDJugador() == compararCartas(cartasTiradasJ1.get(1), cartasTiradasJ2.get(1))) {
                        nroRondasGanadasJ2 += 1;
                    } else {
                        // tambien es parda
                        parda = true;
                    }
                    numeroRonda += 1;

                } else if (j1.getIDJugador() == compararCartas(cartasTiradasJ1.get(1), cartasTiradasJ2.get(1))) {
                    nroRondasGanadasJ1 += 1;
                    turno = j1.getIDJugador();
                } else if (j2.getIDJugador() == compararCartas(cartasTiradasJ1.get(1), cartasTiradasJ2.get(1))) {
                    nroRondasGanadasJ2 += 1;
                    turno = j2.getIDJugador();
                } else { // si es null es porque es parda
                    if (j1.getIDJugador() == hizoPrimera) nroRondasGanadasJ1 += 1;
                    else if (j2.getIDJugador() == hizoPrimera) nroRondasGanadasJ2 += 1;
                }
                numeroRonda += 1;

            } else if (numeroRonda == 3) {
                if (parda) {
                    if (j1.getIDJugador() == compararCartas(cartasTiradasJ1.get(2), cartasTiradasJ2.get(2))) {
                        nroRondasGanadasJ1 += 1;
                    } else if (j2.getIDJugador() == compararCartas(cartasTiradasJ1.get(2), cartasTiradasJ2.get(2))) {
                        nroRondasGanadasJ1 += 1;
                    } else {
                        if (j1.getIDJugador() == hizoPrimera) nroRondasGanadasJ1 += 1;
                        else if (j2.getIDJugador() == hizoPrimera) nroRondasGanadasJ2 += 1;
                    }
                } else if (j1.getIDJugador() == compararCartas(cartasTiradasJ1.get(2), cartasTiradasJ2.get(2))) {
                    nroRondasGanadasJ1 += 1;
                    turno = j1.getIDJugador();
                } else if (j2.getIDJugador() == compararCartas(cartasTiradasJ1.get(2), cartasTiradasJ2.get(2))) {
                    nroRondasGanadasJ2 += 1;
                    turno = j2.getIDJugador();
                } else { // si es null es porque es parda
                    if (j1.getIDJugador() == hizoPrimera) nroRondasGanadasJ1 += 1;
                    else if (j2.getIDJugador() == hizoPrimera) nroRondasGanadasJ2 += 1;
                }

            }
        }

        return null;
    }

    @Override
    public void finDePartida() {
        actualizarPuntos();
    }

    @Override
    public String puntosActuales() {
        return anotador.toString();
    }

    @Override
    public void cantarRabon(int id, int opcion) {
        // levantar de archivo los cantos y poner uno aleatorio
    }

    @Override
    public void cantarEnvido(int id, int opcion) {
        // levantar de archivo los cantos y poner uno aleatorio
    }

    @Override
    public void meVoyAlMazo(int id) {
        finDeLaRonda();
    }

    @Override
    public boolean esFinDePartida() {
        return ( (anotador.getPuntosJ1() >= 30 ) || (anotador.getPuntosJ2() >= 30) );
    }

    @Override
    public void agregarJugador(Jugador jugador) {
        if(j1 == null && j2 == null){
            j1 = jugador;
        }
        else if(j2 == null){
            j2 = jugador;
        }

        if( (j1.getIDJugador() != 0) && (j2.getIDJugador() != 0) ) nuevaPartida();

        // actualizar lista etc etc
    }

    @Override
    public void actualizarPuntos() {
        anotador.sumarPuntosJ1(puntajeRondaJ1);
        anotador.sumarPuntosJ2(puntajeRondaJ2);
    }

    @Override
    public estadoTruco estadoRabon() {
        return estadoDelTruco;
    }

    @Override
    public estadoEnvido estadoTanto() {
        return estadoDelEnvido;
    }

    @Override
    public int numeroDeRonda() {
        return numeroRonda;
    }


    //
    // metodos privados
    //

    private int compararCartas(Carta cj1, Carta cj2){
        if(cj1.getPoderCarta() > cj2.getPoderCarta()) return j1.getIDJugador();
        else if(cj1.getPoderCarta() < cj2.getPoderCarta()) return j2.getIDJugador();

        return -1; // caso de que sean el mismo poder es PARDA
    }

    private boolean esUltimaCarta(int id){
        return true;
    }

    private void notificarPartidaLista(){

    }

    private int calcularPuntajeEnvidoQuerido(){
        int puntos = 0;
        int maximoPuntos = 1;

        if(anotador.getPuntosJ2() > anotador.getPuntosJ1()) maximoPuntos = 30 - anotador.getPuntosJ2();
        else maximoPuntos = 30 - anotador.getPuntosJ1();

        if(cantoFaltaEnvido){
            if(anotador.getPuntosJ2() < 15 && anotador.getPuntosJ1() < 15) puntos = 30;
            else puntos = maximoPuntos;

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

        System.out.println("Puntos Envido Querido: " + puntos);
        return puntos;
    }

    private int calcularEnvidoNoQuerido(){
        int puntos = 0;

        if(cantoEnvido){
            puntos += 1;
            if(cantoEnvidoDoble){
                puntos += 1;
                if(cantoRealEnvido){
                    puntos += 2;
                    if(cantoFaltaEnvido) puntos += 3;
                }
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


    //
    // sets y gets
    //




}
