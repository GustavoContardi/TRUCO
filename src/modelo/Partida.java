package modelo;

import ar.edu.unlu.rmimvc.observer.ObservableRemoto;
import enums.EstadoEnvido;
import enums.EstadoTruco;
import enums.Eventos;
import interfaces.IModelo;
import persistencia.Persistencia;
import persistencia.PersistenciaJugador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

import static enums.EstadoEnvido.*;
import static enums.EstadoTruco.*;
import static enums.Eventos.*;
import static enums.Eventos.NADA;

public class Partida extends ObservableRemoto implements Serializable, IModelo {

    //
    // atributos
    //

    private     Jugador             j1;
    private     Jugador             j2;
    private     Mazo                mazo;
    private     int                 idPartida;
    private     Anotador            anotador;
    private     int                 numeroMano, numeroRonda, turno;
    private     ArrayList<Carta>    cartasTiradasJ1;
    private     ArrayList<Carta>    cartasTiradasJ2;
    private     int                 quienCantoTruco;
    private     int                 quienCantoReTruco;
    private     int                 quienCantoEnvido;
    private     int                 puntosRabon;
    private     int                 quienCantoEnvidoDoble;
    private     int                 quienCantoRealEnvido;
    private     int                 quienCantoFaltaEnvido;
    private     int                 quienCantoValeCuatro;
    private     int                 hizoPrimera; // ID del jugador
    private     EstadoTruco         estadoDelTruco;
    private     EstadoEnvido        estadoDelEnvido;
    private     boolean             cantoEnvido, cantoEnvidoDoble,cantoRealEnvido, cantoFaltaEnvido;
    private     int                 puntajeRondaJ1, puntajeRondaJ2, puntajeRondaEnvido;
    private     boolean             finMano;
    private     int                 nroRondasGanadasJ1, nroRondasGanadasJ2;
    private     int                 idJugadorNoQuizoCanto;
    private     boolean             parda;
    private     boolean             partidaRecuperada;
    private     Eventos             mensajesOb;
    private     Carta               ultimaCartaJ1, ultimaCartaJ2;
    private     String              ultimoMensaje;
    private     String              resultadoTanto;


    //
    // constructor
    //

    public Partida() throws RemoteException {
        j1              = null;
        j2              = null;
        numeroMano      = -1;
        numeroRonda     =  1;
        finMano         = true;
        mazo            = new Mazo();
        anotador        = new Anotador("", "");
    }


    //
    // metodos publicos
    //

    public void nuevaPartida() throws RemoteException {
        idPartida = generarIdPartida();
        nuevaRonda();
    }

    @Override
    public void nuevaRonda() throws RemoteException {
        // seteo los atributos de inicio
        if(numeroMano == 0) {
            anotador = new Anotador(j2.getNombre(), j1.getNombre());
            notificarPuntos();
        }
        if (finMano){
            if(!esFinDePartida()){
                numeroMano         += 1;
                numeroRonda         = 1;
                estadoDelTruco      = EstadoTruco.NADA;
                estadoDelEnvido     = EstadoEnvido.NADA;
                puntajeRondaJ1      = 0;
                puntajeRondaJ2      = 0;
                puntajeRondaEnvido  = 0;
                quienCantoTruco     = 0;
                quienCantoReTruco   = 0;
                puntosRabon         = 0;
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

                j1.devolverCartas();
                j2.devolverCartas();

                mazo.repartirCartas(j1, j2);

            }
            else finDePartida();
        }
        notificarNuevaRonda();
    }

    @Override
    public void finDeLaRonda() throws RemoteException{
        finMano = true;

        if(nroRondasGanadasJ1 > nroRondasGanadasJ2) puntajeRondaJ1 += puntosRabon;
        else if(nroRondasGanadasJ1 < nroRondasGanadasJ2) puntajeRondaJ2 += puntosRabon;

        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código que se ejecutará después de 3 segundos
                try {
                    notificarFinMano();
                    actualizarPuntos(); // espero 2 segundos antes de notificar para que se pueda ver lo que paso antes y no sea tan rapido
                    nuevaRonda();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
        timer.setRepeats(false); // Asegura que el timer solo se ejecute una vez
        timer.start();

    }

    @Override
    public void siguienteTurno() throws RemoteException{
        if(turno == j1.getIDJugador()) turno = j2.getIDJugador();
        else turno = j1.getIDJugador();
    }

    @Override
    public int turnoActual() throws RemoteException {
        return turno;
    }

    @Override
    public void tirarCarta(int idJugador, int idCarta) throws RemoteException{
        Carta c = null;
        if(idJugador == j1.getIDJugador() && turno == idJugador){
            c = j1.tirarCarta(idCarta);
            if(numeroRonda == 1) cartasTiradasJ1.add(c);
            else if(numeroRonda == 2) cartasTiradasJ1.add(c);
            else if(numeroRonda == 3) cartasTiradasJ1.add(c);
            ultimaCartaJ1 = c;
            siguienteTurno();
            notificarCartaTirada(1);
        }
        else if(idJugador == j2.getIDJugador() && idJugador == turno){
            c = j2.tirarCarta(idCarta);
            if(numeroRonda == 1) cartasTiradasJ2.add(c);
            else if(numeroRonda == 2) cartasTiradasJ2.add(c);
            else if(numeroRonda == 3) cartasTiradasJ2.add(c);
            ultimaCartaJ2 = c;
            siguienteTurno();
            notificarCartaTirada(2);
        }

        //notificarCartaTirada(id, c); // notifico la carta tirada y despues sigo \\

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

        if(nroRondasGanadasJ1 >= 2  && nroRondasGanadasJ1 > nroRondasGanadasJ2) finMano = true;
        else if (nroRondasGanadasJ2 >= 2  && nroRondasGanadasJ1 < nroRondasGanadasJ2) finMano = true;

        if(finMano){
            finDeLaRonda();
        }

    }

    @Override
    public void finDePartida() throws RemoteException {
        if(anotador.getPuntosJ2() > anotador.getPuntosJ1()) j2.sumarPartidaGanada();
        else j1.sumarPartidaGanada();

        PersistenciaJugador.delvolverJugadores(j1.getIDJugador(), j2.getIDJugador());
        actualizarPuntos();

        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código que se ejecutará después de 2 segundos
                try {
                    // espero 2 segundos antes de notificar para que se pueda ver lo que paso antes y no sea tan rapido
                    notificarFinPartida();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
        timer.setRepeats(false); // Asegura que el timer solo se ejecute una vez
        timer.start();

    }

    @Override
    public String puntosActuales()  throws RemoteException {
        return anotador.toString();
    }

    @Override
    public void cantarRabon(int idJugadorCanto, EstadoTruco estado) throws RemoteException{
        Eventos evento = NADA;

        switch(estado){ // el estado es el que se tiene que cantar no el actual
            case TRUCO ->{
                if(!cantoEnvido) cantoEnvido = true;
                quienCantoTruco = idJugadorCanto;
                evento = CANTO_TRUCO;
                estadoDelTruco = TRUCO;
                puntosRabon = 2;
            }
            case RE_TRUCO -> {
                quienCantoReTruco = idJugadorCanto;
                evento = CANTO_RETRUCO;
                estadoDelTruco = RE_TRUCO;
                puntosRabon = 3;
            }
            case VALE_CUATRO -> {
                quienCantoValeCuatro = idJugadorCanto;
                evento = CANTO_VALE_CUATRO;
                estadoDelTruco = VALE_CUATRO;
                puntosRabon = 4;
            }
        }

        notificarRabon(evento);
    }

    public void enviarMensaje(int idDestinatario, String mensaje) throws RemoteException {

    }

    @Override
    public void cantarEnvido(int idJugadorCanto, EstadoEnvido estado) throws RemoteException {
        // levantar de archivo los cantos y poner uno aleatorio
        Eventos evento = NADA;

        switch (estado){
            case ENVIDO -> {
                quienCantoEnvido = idJugadorCanto;
                estadoDelEnvido = ENVIDO;
                cantoEnvido = true;
                evento = CANTO_ENVIDO;
            }
            case ENVIDO_DOBLE -> {
                quienCantoEnvidoDoble = idJugadorCanto;
                estadoDelEnvido = ENVIDO_DOBLE;
                cantoEnvidoDoble = true;
                evento = CANTO_ENVIDO_DOBLE;
            }
            case REAL_ENVIDO -> {
                quienCantoRealEnvido = idJugadorCanto;
                estadoDelEnvido = REAL_ENVIDO;
                cantoRealEnvido = true;
                evento = CANTO_REAL_ENVIDO;
            }
            case FALTA_ENVIDO -> {
                quienCantoFaltaEnvido = idJugadorCanto;
                estadoDelEnvido = FALTA_ENVIDO;
                cantoFaltaEnvido = true;
                evento = CANTO_FALTA_ENVIDO;
            }
        }

        notificarCantoTanto(evento);
    }

    @Override
    public void meVoyAlMazo(int idJugSeFue) throws RemoteException {
        int puntos = 0;

        if(numeroRonda == 1 && !cantaronEnvido()) puntos = 1; // por reglas si se va al mazo en la primer mano sin cantar nada son 2 puntos para el contrario
        if(numeroRonda == 1 && estadoDelTruco == EstadoTruco.NADA) puntos += 1;

        if(idJugSeFue != j1.getIDJugador()) puntajeRondaJ1 += puntos;
        else if(idJugSeFue != j2.getIDJugador()) puntajeRondaJ2 += puntos;

        finDeLaRonda();
    }

    @Override
    public boolean esFinDePartida() throws RemoteException {
        return ( (anotador.getPuntosJ1() >= 30 ) || (anotador.getPuntosJ2() >= 30) );
    }

    @Override
    public void agregarJugador(Jugador jugador) throws RemoteException {
        if(j1 == null && j2 == null){
            j1 = jugador;
            j1.setElecto(true);
            notificarJugadorElecto();
        }
        else if(j1 != null && j2 == null){
            j2 = jugador;
            j2.setElecto(true);
        }


        if( (j1 != null) && (j2 != null) ) {
            nuevaPartida();
        }
    }



    @Override
    public void altaJugador(String nombre) throws RemoteException {
        if(nombre != null) new Jugador(nombre);
        notificarJugadorElecto(); // es lo mismo notificar con este pq actualiza la lista en eleccion
    }

    @Override
    public void actualizarPuntos() throws RemoteException {
        anotador.sumarPuntosJ1(puntajeRondaJ1);
        anotador.sumarPuntosJ2(puntajeRondaJ2);
        notificarPuntos();
    }

    @Override
    public EstadoTruco estadoRabon() throws RemoteException {
        return estadoDelTruco;
    }

    @Override
    public EstadoEnvido estadoTanto() throws RemoteException {
        return estadoDelEnvido;
    }

    @Override
    public int numeroDeRonda() throws RemoteException {
        return numeroRonda;
    }

    @Override
    public boolean cantaronEnvido() throws RemoteException {
        return cantoEnvido;
    }

    @Override
    public boolean cantaronEnvidoDoble() throws RemoteException {
        return cantoRealEnvido;
    }

    @Override
    public boolean cantaronRealEnvido() throws RemoteException {
        return cantoRealEnvido;
    }

    @Override
    public boolean cantaronFaltaEnvido() throws RemoteException {
        return cantoFaltaEnvido;
    }

    @Override
    public String toString(){
        return "ID: " + idPartida + " | Jugadores: " + j1.getNombre() + " y " + j2.getNombre();
    }


    //
    // metodos privados
    //

    private int compararCartas(Carta cj1, Carta cj2) throws RemoteException{
        if(cj1.getPoderCarta() > cj2.getPoderCarta()) return j1.getIDJugador();
        else if(cj1.getPoderCarta() < cj2.getPoderCarta()) return j2.getIDJugador();

        return -1; // caso de que sean el mismo poder es PARDA
    }

    private void notificarPartidaLista() throws RemoteException {
        mensajesOb = INICIO_PARTIDA;
        notificarObservadores(mensajesOb);
    }

    private void notificarMensaje(int idDestinatario) throws RemoteException {
        if(idDestinatario == j1.getIDJugador()) mensajesOb = MENSAJEJ1; // mensaje para j1
        else mensajesOb = MENSAJEJ2; // mensaje para j2
        // Y EL MENSAJE (??????????
        notificarObservadores(mensajesOb);
    }

    private void notificarPuntos() throws RemoteException {
        mensajesOb = PUNTAJES;
        notificarObservadores(mensajesOb);
    }

    private void notificarJugadorElecto() throws RemoteException {
        mensajesOb = LISTA_JUGADORES_DISPONIBLES;
        notificarObservadores(mensajesOb);
    }

    private void notificarNuevaRonda() throws RemoteException {
        mensajesOb = NUEVA_RONDA;
        notificarObservadores(mensajesOb);
    }

    private void notificarFinMano() throws RemoteException {
        mensajesOb = FIN_MANO;
        notificarObservadores(mensajesOb);
    }

    private void notificar() throws RemoteException {
        notificarObservadores(mensajesOb);
    }

    private void notificarCantoQuerido(int idDestinatario) throws RemoteException {
        mensajesOb = CANTO_QUERIDO;
        idJugadorNoQuizoCanto = idDestinatario;

        notificarObservadores(mensajesOb);
    }

    private void notificarCantoNoQuerido(int idDestinatario) throws RemoteException {
        mensajesOb = CANTO_NO_QUERIDO;
        idJugadorNoQuizoCanto = idDestinatario;

        notificarObservadores(mensajesOb);
    }

    private void notificarPartidaReanudada() throws RemoteException {
        mensajesOb = RESTABLECER_PARTIDA;
        notificarObservadores(mensajesOb);
    }

    private void notificarCantoTanto(Eventos e) throws RemoteException {
        mensajesOb = e;
        notificarObservadores(mensajesOb);
    }

    private void notificarTantoQuerido() throws RemoteException {
        mensajesOb = TANTO_QUERIDO;
        notificarObservadores(mensajesOb);
    }


        private void notificarRabon(Eventos e) throws RemoteException {
        mensajesOb = e;
        notificarObservadores(mensajesOb);
    }

    private void notificarCartaTirada(int jugador) throws RemoteException {
        Eventos e = NADA;
        if(jugador == 1){
            e = CARTA_TIRADAJ1;
        }
        else e = CARTA_TIRADAJ2;

        notificarObservadores(e);
    }

    private void notificarFinPartida() throws RemoteException {
        notificarObservadores(FIN_PARTIDA);
    }


    private int calcularPuntajeEnvidoQuerido() throws RemoteException{
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

    private int calcularEnvidoNoQuerido() throws RemoteException{
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

    private int generarIdPartida(){
        Random random = new Random();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDate = now.format(formatter);

        String nombre = " ";
        if(j1 != null) nombre = j1.getNombre();

        String idString = nombre.substring(0, Math.min(3, nombre.length())).toUpperCase() + formattedDate;

        return Math.abs(idString.hashCode() / random.nextInt(10));

    }


    //
    // sets y gets
    //


    public Jugador getJ1() throws RemoteException {
        return j1;
    }

    public Jugador getJ2() throws RemoteException {
        return j2;
    }

    public int getQuienCantoTruco() throws RemoteException{
        return quienCantoTruco;
    }

    public int getQuienCantoReTruco() throws RemoteException{
        return quienCantoReTruco;
    }

    @Override
    public int getQuienCantoValeCuatro() throws RemoteException {
        return quienCantoValeCuatro;
    }

    @Override
    public int getQuienCantoEnvido() throws RemoteException{
        return quienCantoEnvido;
    }

    @Override
    public int getQuienCantoEnvidoDoble() throws RemoteException{
        return quienCantoEnvidoDoble;
    }

    @Override
    public int getQuienCantoRealEnvido() throws RemoteException{
        return quienCantoRealEnvido;
    }

    @Override
    public int getQuienCantoFaltaEnvido() throws RemoteException{
        return quienCantoFaltaEnvido;
    }

    @Override
    public Carta ultimaCartaTiradaJ1() throws RemoteException{
        return ultimaCartaJ1;
    }

    @Override
    public Carta ultimaCartaTiradaJ2() throws RemoteException{
        return ultimaCartaJ2;
    }

    @Override
    public int getIdJ1() throws RemoteException{
        return j1.getIDJugador();
    }

    @Override
    public int getIdJ2() throws RemoteException{
        return j2.getIDJugador();
    }

    @Override
    public int getIdJugadorNoQuizoCanto() throws RemoteException {
        return idJugadorNoQuizoCanto;
    }

    @Override
    public String getUltimoMensaje() throws RemoteException{
        return ultimoMensaje;
    }

    @Override
    public ArrayList<Carta> getCartasJ1() throws RemoteException{
        return j1.getCartasObtenidas();
    }
    @Override
    public ArrayList<Carta> getCartasJ2() throws RemoteException{
        return j2.getCartasObtenidas();
    }

    // .................................................................................................................
    // -- ACTUALIZAR ESTO Y ORDENAR --
    // .................................................................................................................

    @Override
    public void tantoQuerido(int idJugador) throws RemoteException {
        int puntos = calcularPuntajeEnvidoQuerido();

        if(j1.puntosEnvido() > j2.puntosEnvido()) {
            anotador.sumarPuntosJ1(puntos);
            resultadoTanto = "El ganador del Envido es: " + j1.getNombre() + " con " + j1.puntosEnvido() + " puntos";
        }
        else if(j1.puntosEnvido() < j2.puntosEnvido()) {
            anotador.sumarPuntosJ2(puntos);
            resultadoTanto = "El ganador del Envido es: " + j2.getNombre() + " con " + j2.puntosEnvido() + " puntos";
        }
        else{ // si tienen el mismo tanto se decide por el que es mano (el que no repartió)
            if( (numeroMano % 2) == 0) {
                anotador.sumarPuntosJ1(puntos);
                resultadoTanto = "El ganador del Envido es: " + j1.getNombre() + " con " + j1.puntosEnvido() + " puntos";
            }
            else if( (numeroMano % 2) == 1) {
                anotador.sumarPuntosJ2(puntos);
                resultadoTanto = "El ganador del Envido es: " + j2.getNombre() + " con " + j2.puntosEnvido() + " puntos";
            }
        }
        notificarTantoQuerido();
        notificarPuntos();

        if(esFinDePartida()) {
            finDePartida();
        }
    }

    @Override
    public void tantoNoQuerido(int idjugNoQuizo) throws RemoteException {
        int puntos = calcularEnvidoNoQuerido();

        if(idjugNoQuizo == j1.getIDJugador()) anotador.sumarPuntosJ2(puntos);
        else if(idjugNoQuizo == j2.getIDJugador()) anotador.sumarPuntosJ1(puntos);

        notificarPuntos();

    }

    @Override
    public void rabonQuerido(int idJugadorQuizo) throws RemoteException {
        if(idJugadorQuizo != j1.getIDJugador()) notificarCantoQuerido(j1.getIDJugador());
        else notificarCantoQuerido(j2.getIDJugador());
    }

    @Override
    public void rabonNoQuerido(int idjugNoQuizo) throws RemoteException {
        puntosRabon -= 1;
        if(idjugNoQuizo != j1.getIDJugador()) puntajeRondaJ1 = puntosRabon;
        else if(idjugNoQuizo != j2.getIDJugador()) puntajeRondaJ2 = puntosRabon;

        notificarCantoNoQuerido(idjugNoQuizo);

        meVoyAlMazo(idjugNoQuizo);
    }

    @Override
    public String getResultadoTanto() throws RemoteException {
        return resultadoTanto;
    }

    @Override
    public String getJugadorGanador() throws RemoteException {
        return anotador.getGanador();
    }

    @Override
    public String getNombreRival(int idJugador) throws RemoteException {
        if(j1.getIDJugador() != idJugador) return j1.getNombre();
        else return j2.getNombre();
    }

    @Override
    public ArrayList<Carta> getCartasTiradasJ1() throws RemoteException {
        return cartasTiradasJ1;
    }

    @Override
    public ArrayList<Carta> getCartasTiradasJ2() throws RemoteException {
        return cartasTiradasJ2;
    }

    @Override
    public int getIdPartida() throws RemoteException {
        return idPartida;
    }

    @Override
    public Partida getObjeto() throws RemoteException {
        return this;
    }

    @Override
    public ArrayList<Jugador> jugadoresDeLaPartida() throws RemoteException {
        ArrayList<Jugador> jugadores = new ArrayList<>(); // por si no hay jugadores que la devuelva vacia para evitar null pointer except

        if(j1 != null && j2 != null){
            jugadores.add(j1);
            jugadores.add(j2);
        }
        return jugadores;
    }

    @Override
    public void reanudarPartida() throws RemoteException {
        // toda la logica para reanudar la partida

        notificarPartidaReanudada();
    }

    @Override
    public boolean getPartidaRecuperada() throws RemoteException {
        return false;
    }

    @Override
    public boolean setPartidaRecuperada() throws RemoteException {
        return false;
    }

    @Override
    public void actualizarListaJugadores() throws RemoteException {
        notificarJugadorElecto();
    }


}
