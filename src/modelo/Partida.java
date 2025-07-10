package modelo;

import ar.edu.unlu.rmimvc.observer.ObservableRemoto;
import enums.EstadoEnvido;
import enums.EstadoFlor;
import enums.EstadoTruco;
import enums.Eventos;
import interfaces.IModelo;
import persistencia.PersistenciaJugador;
import persistencia.PersistenciaPartida;

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
import static enums.EstadoFlor.*;
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
    private     ArrayList<Carta>    cartasTiradasJ1, cartasTiradasJ2;
    private     ArrayList<Carta>    cartasJ1, cartasJ2;
    private     int                 quienCantoTruco;
    private     int                 quienCantoReTruco;
    private     int                 quienCantoEnvido;
    private     int                 puntosRabon;
    private     int                 quienCantoEnvidoDoble;
    private     int                 quienCantoRealEnvido;
    private     int                 quienCantoFaltaEnvido;
    private     int                 quienCantoValeCuatro;
    private     int                 quienCantoFlor;
    private     int                 hizoPrimera; // ID del jugador
    private     EstadoTruco         estadoDelTruco;
    private     EstadoEnvido        estadoDelEnvido;
    private     EstadoFlor          estadoDeLaFlor;
    private     boolean             cantoEnvido, cantoEnvidoDoble,cantoRealEnvido, cantoFaltaEnvido;
    private     int                 puntajeRondaJ1, puntajeRondaJ2, puntajeRondaEnvido;
    private     boolean             finMano;
    private     int                 nroRondasGanadasJ1, nroRondasGanadasJ2;
    private     int                 idJugadorNoQuizoCanto, idJugadorQuiereCantar;
    private     boolean             parda;
    private     boolean             partidaRecuperada;
    private     Eventos             mensajesOb;
    private     Carta               ultimaCartaJ1, ultimaCartaJ2;
    private     String              ultimoMensaje;
    private     String              resultadoTanto;
    private     boolean             reanudoJ1, reanudoJ2;
    private     int                 puntosParaGanar; // la partida se puede jugar a 15 (rapida) o 30 (lenta) puntos
    private     boolean             seJuegaConFlor;
    private     int                 idJugadorSalio, idUltimoJugCanto;
    private     boolean             seEstabaCantandoEnvido, seEstabaCantandoTruco, seEstabaCantandoFlor; // banderas para saber si se estaba cantando algo antes de que se posponga la partida


    //               \\
    //  constructor  \\
    //               \\

    public Partida(int puntosGanar, boolean flor) throws RemoteException {
        j1               =  null;
        j2               =  null;
        numeroMano       =  -1;
        numeroRonda      =   1;
        finMano          =  true;
        mazo             =  new Mazo();
        idPartida        =  generarIdPartida();
        puntosParaGanar  =  puntosGanar;
        seJuegaConFlor   =  flor;
        reanudoJ1        =  false;
        reanudoJ2        =  false;
    }


    //
    // metodos publicos
    //

    // metodo que se ejecuta 1 vez, cuando se inicia la partida. Llama a iniciar la ronda
    public void nuevaPartida() throws RemoteException {
        nuevaRonda();
    }

    @Override
    public void nuevaRonda() throws RemoteException {
        // seteo los atributos de inicio
        if(numeroMano == -1) {
            anotador = new Anotador(j2.getNombre(), j1.getNombre(), puntosParaGanar);
            notificarEvento(PUNTAJES);
        }

        actualizarPuntos();

        if (finMano){
            if(!esFinDePartida()){
                numeroMano              += 1;
                numeroRonda             = 1;
                estadoDelTruco          = EstadoTruco.NADA;
                estadoDelEnvido         = EstadoEnvido.NADA;
                puntajeRondaJ1          = 0;
                puntajeRondaJ2          = 0;
                puntajeRondaEnvido      = 0;
                quienCantoTruco         = 0;
                quienCantoReTruco       = 0;
                puntosRabon             = 0;
                hizoPrimera             = 0;
                cantoEnvido             = false;
                cantoEnvidoDoble        = false;
                cantoRealEnvido         = false;
                cantoFaltaEnvido        = false;
                cartasTiradasJ1         = new ArrayList<>();
                cartasTiradasJ2         = new ArrayList<>();
                finMano                 = false;
                nroRondasGanadasJ1      = 0;
                nroRondasGanadasJ2      = 0;
                parda                   = false;
                cartasJ1                = new ArrayList<>();
                cartasJ2                = new ArrayList<>();
                idJugadorNoQuizoCanto   = 0;
                idJugadorQuiereCantar   = 0;
                idJugadorSalio          = 0;
                idUltimoJugCanto = 0;
                seEstabaCantandoEnvido  = false;
                seEstabaCantandoTruco   = false;
                seEstabaCantandoFlor    = false;


                if( (numeroMano % 2) == 0) turno = j1.getIDJugador();
                else turno = j2.getIDJugador();

                j1.devolverCartas();
                j2.devolverCartas();

                mazo.repartirCartas(j1, j2);
                //mazo.repartirFlor(j1, j2); pruebas para cuando se juega con flor, no va en el normal, el de arriba si
                replicarCartasJugadores();
            }
            else finDePartida();
        }
        PersistenciaPartida.guardarPartida(this);
        PersistenciaJugador.delvolverTodosJugadores(); // devuelvo por si quieren jugar otra partida, ya quedan guardados porque se guarda la partida.
        notificarEvento(NUEVA_RONDA);
    }

    // se llama a este metodo cuando finalizo la ronda
    @Override
    public void finDeLaRonda() throws RemoteException{
        finMano = true;

        puntosRabon = calcularPuntosTruco();

        if(nroRondasGanadasJ1 > nroRondasGanadasJ2) puntajeRondaJ1 += puntosRabon;
        else if (nroRondasGanadasJ2 > nroRondasGanadasJ1) puntajeRondaJ2 += puntosRabon;

        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código que se ejecutará después de 3 segundos
                try {
                    notificarEvento(FIN_MANO);
                    // espero 2 segundos antes de notificar para que se pueda ver lo que paso antes y no sea tan rapido
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
        if(turno == j1.getIDJugador())  turno = j2.getIDJugador();
        else                            turno = j1.getIDJugador();
    }

    @Override
    public int turnoActual() throws RemoteException {
        return turno;
    }

    // este metodo recibe el jugador que tiro la carta y que carta tiro, con los IDs alcanza para identificar ambas.
    @Override
    public void tirarCarta(int idJugador, int idCarta) throws RemoteException{
        Carta c = null;
        if(idJugador == j1.getIDJugador() && turno == idJugador){
            c = j1.tirarCarta(idCarta);
            cartasTiradasJ1.add(c);
            ultimaCartaJ1 = c;
            siguienteTurno();
            notificarEvento(CARTA_TIRADAJ1);
        }
        else if(idJugador == j2.getIDJugador() && idJugador == turno){
            c = j2.tirarCarta(idCarta);
            cartasTiradasJ2.add(c);
            ultimaCartaJ2 = c;
            siguienteTurno();
            notificarEvento(CARTA_TIRADAJ2);
        }


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
                        nroRondasGanadasJ1 += 1;
                        nroRondasGanadasJ2 += 1;
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

        PersistenciaPartida.guardarPartida(this);

        if(finMano){
            finDeLaRonda();
        }

    }

    // se llama a este metodo cuando finalizo la partida.
    @Override
    public void finDePartida() throws RemoteException {
        if(anotador.getPuntosJ2() > anotador.getPuntosJ1()) j2.sumarPartidaGanada();
        else j1.sumarPartidaGanada();

        actualizarPuntos();
        PersistenciaPartida.eliminarPartida(this.idPartida); // elimino la partida para que no quede y se pueda reanudar

        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // espero 2 segundos antes de notificar para que se pueda ver lo que paso antes y no sea tan rapido
                    notificarEvento(FIN_PARTIDA);
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
        timer.setRepeats(false); // para que el timer se ejecute una vez
        timer.start();

    }

    // devuelve el puntaje actual de la partida
    @Override
    public String puntosActuales()  throws RemoteException {
        return anotador.toString();
    }

    // metodo que activa el controlador cuando algun jugador quiere cantar el rabon (truco, retruco, vale cuatro), actualizo los atributos segun que canto y notifico
    @Override
    public void cantarRabon(int idJugadorCanto, EstadoTruco estado) throws RemoteException{
        // rabon == truco
        Eventos evento = NADA;

        switch(estado){ // el estado es el que se tiene que cantar no el actual
            case TRUCO ->{
                cantoEnvido = true;
                quienCantoTruco = idJugadorCanto;
                evento = CANTO_TRUCO;
                estadoDelTruco = TRUCO;
            }
            case RE_TRUCO -> {
                quienCantoReTruco = idJugadorCanto;
                evento = CANTO_RETRUCO;
                estadoDelTruco = RE_TRUCO;
            }
            case VALE_CUATRO -> {
                quienCantoValeCuatro = idJugadorCanto; // tengo que identificar quien canto que cosa porque no podes cantar dos veces seguidas (por regla)
                evento = CANTO_VALE_CUATRO;
                estadoDelTruco = VALE_CUATRO;
            }
        }
        idUltimoJugCanto = idJugadorCanto;
        seEstabaCantandoTruco = true;
        notificarEvento(evento);
    }

    // metodo que activa el controlador cuando algún jugador quiere cantar el TANTO (envido, envido2, real envido, falta envido), actualizo los atributos segun que cantó y notifico
    @Override
    public void cantarEnvido(int idJugadorCanto, EstadoEnvido estado) throws RemoteException {
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
        idUltimoJugCanto = idJugadorCanto;
        seEstabaCantandoEnvido = true;
        notificarEvento(evento);
    }

    @Override
    public void cantarFlor(int id, EstadoFlor estado) throws RemoteException {
        Eventos evento = NADA;
        cantoEnvido = true; // la FLOR anula el envido por REGLA
        quienCantoFlor = id;

        switch (estado){
            case FLOR -> {
                evento = CANTO_FLOR;
                estadoDeLaFlor = FLOR;
            }
            case CONTRA_FLOR -> {
                evento = CANTO_CONTRAFLOR;
                estadoDeLaFlor = CONTRA_FLOR;
            }
            case CONTRA_FLOR_AL_RESTO -> {
                evento = CANTO_CONTRAFLOR_ALRESTO;
                estadoDeLaFlor = CONTRA_FLOR_AL_RESTO;
            }
        }

        idUltimoJugCanto = id;
        seEstabaCantandoFlor = true;
        notificarEvento(evento);
    }

    // cuando un jugador se va al mazo termina la ronda como este, el controlador llama a este metodo y automaticamente termina la ronda
    @Override
    public void meVoyAlMazo(int idJugSeFue) throws RemoteException {

        if(idJugSeFue != j1.getIDJugador()) {
            nroRondasGanadasJ1 += 2;
        }
        else if(idJugSeFue != j2.getIDJugador()) {
            nroRondasGanadasJ2 += 2;
        }

        finDeLaRonda();
    }

    @Override
    public void abandonoPartida(int idAbandono) throws RemoteException {


        if(j1.getIDJugador() != idAbandono) {
            anotador.sumarPuntosJ1(puntosParaGanar-anotador.getPuntosJ1());
            j1.sumarPartidaGanada();
        }
        else if (j2.getIDJugador() != idAbandono) {
            anotador.sumarPuntosJ2(puntosParaGanar-anotador.getPuntosJ1()); // sumo los que le falten para ganar al que no abandono asi termina la partida
            j2.sumarPartidaGanada();
        }
        actualizarPuntos();

        Timer timer = new Timer(2500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código que se ejecutará después de 3 segundos
                try {
                    notificarEvento(ABANDONO_PARTIDA);
                    // espero 2,5 segundos antes de notificar para que se pueda ver lo que paso antes y no sea tan rapido
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
        timer.setRepeats(false); // Asegura que el timer solo se ejecute una vez
        timer.start();

        PersistenciaPartida.eliminarPartida(this.idPartida); // elimino la partida para que no quede y se pueda reanudar

    }

    // pregunto si es fin de partida
    @Override
    public boolean esFinDePartida() throws RemoteException {
        return ( (anotador.getPuntosJ1() >= puntosParaGanar ) || (anotador.getPuntosJ2() >= puntosParaGanar) );
    }

    // este metodo lo llama el controlador desde la 'vista elección' cuando se estan uniendo los jugadores a la partida por primera vez
    @Override
    public void agregarJugador(Jugador jugador) throws RemoteException {
        if(j1 == null && j2 == null){
            j1 = jugador;
            j1.setElecto(true);
            notificarEvento(LISTA_JUGADORES_DISPONIBLES);
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
        notificarEvento(LISTA_JUGADORES_DISPONIBLES);// es lo mismo notificar con este pq actualiza la lista en eleccion
    }

    // se cantó el tanto y dijieron que si (quiero), entonces notifico quién ganó
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
        else { // si tienen el mismo tanto se decide por el que es mano (el que no repartió)
            if( (numeroMano % 2) == 0) {
                anotador.sumarPuntosJ1(puntos);
                resultadoTanto = "El ganador del Envido es: " + j1.getNombre() + " con " + j1.puntosEnvido() + " puntos";
            }
            else if( (numeroMano % 2) == 1) {
                anotador.sumarPuntosJ2(puntos);
                resultadoTanto = "El ganador del Envido es: " + j2.getNombre() + " con " + j2.puntosEnvido() + " puntos";
            }
        }
        seEstabaCantandoEnvido = false;
        notificarEvento(TANTO_QUERIDO);
        notificarEvento(PUNTAJES);

        if(esFinDePartida()) { // si con los puntos sumados alguno alcanzo los 30 puntos,
            finDePartida();
        }
    }

    @Override
    public void tantoNoQuerido(int idjugNoQuizo) throws RemoteException {
        int puntos = calcularEnvidoNoQuerido();

        if(idjugNoQuizo == j1.getIDJugador()) anotador.sumarPuntosJ2(puntos);
        else if(idjugNoQuizo == j2.getIDJugador()) anotador.sumarPuntosJ1(puntos);

        if(esFinDePartida()) { // si con los puntos sumados alguno alcanzo los 30 puntos,
            finDePartida();
        }
        seEstabaCantandoEnvido = false;
        idJugadorNoQuizoCanto = idjugNoQuizo;
        notificarEvento(CANTO_NO_QUERIDO);
        notificarEvento(PUNTAJES);

    }

    @Override
    public void rabonQuerido(int idJugadorQuizo) throws RemoteException {
        seEstabaCantandoTruco = false;
        idJugadorQuiereCantar = idJugadorQuizo;
        notificarEvento(CANTO_QUERIDO);
    }

    @Override
    public void rabonNoQuerido(int idjugNoQuizo) throws RemoteException {

        if(idjugNoQuizo != j1.getIDJugador()) puntajeRondaJ1 -= 1; // le resto uno al que canto y no le quisieron para despues en el metodo de calcular me de bien
        else if(idjugNoQuizo != j2.getIDJugador()) puntajeRondaJ2 -= 1;

        seEstabaCantandoTruco = false;

        idJugadorNoQuizoCanto = idjugNoQuizo;
        notificarEvento(CANTO_NO_QUERIDO);

        meVoyAlMazo(idjugNoQuizo);
    }

    @Override
    public void florQuerida(int idjugQuizo) throws RemoteException {
        int puntos = calcularPuntajeFlorQuerida();

        if(j1.puntosFlor() > j2.puntosFlor()) {
            anotador.sumarPuntosJ1(puntos);
            resultadoTanto = "El ganador de la FLOR es: " + j1.getNombre() + " con " + j1.puntosFlor() + " puntos";
        }
        else if(j1.puntosFlor() < j2.puntosFlor()) {
            anotador.sumarPuntosJ2(puntos);
            resultadoTanto = "El ganador de la FLOR es: " + j2.getNombre() + " con " + j2.puntosFlor() + " puntos";
        }
        else { // si tienen el mismo puntaje de flor se decide por el que es mano (el que no repartió)
            if( (numeroMano % 2) == 0) {
                anotador.sumarPuntosJ1(puntos);
                resultadoTanto = "El ganador de la FLOR es: " + j1.getNombre() + " con " + j1.puntosFlor() + " puntos";
            }
            else if( (numeroMano % 2) == 1) {
                anotador.sumarPuntosJ2(puntos);
                resultadoTanto = "El ganador de la FLOR es: " + j2.getNombre() + " con " + j2.puntosFlor() + " puntos";
            }
        }
        seEstabaCantandoFlor = false;
        notificarEvento(PUNTAJES);

        if(esFinDePartida()) { // si con los puntos sumados alguno alcanzo los 30 puntos,
            finDePartida();
        }

    }

    @Override
    public void florNoQuerida(int idjugNoQuizo) throws RemoteException {

        int puntos = calcularPuntajeFlorNoQuerida();

        if(idjugNoQuizo == j1.getIDJugador()) anotador.sumarPuntosJ2(puntos);
        else if(idjugNoQuizo == j2.getIDJugador()) anotador.sumarPuntosJ1(puntos);

        if(esFinDePartida()) { // si con los puntos sumados alguno alcanzo los 30 puntos,
            finDePartida();
        }
        seEstabaCantandoFlor = false;
        idJugadorNoQuizoCanto = idjugNoQuizo;
        notificarEvento(CANTO_NO_QUERIDO);
        notificarEvento(PUNTAJES);
    }

    @Override
    public boolean tieneFlor(int idJug) throws RemoteException {
        if(j1.getIDJugador() == idJug) return j1.tengoFlor();
        else return j2.tengoFlor();
    }


    @Override
    public ArrayList<Jugador> jugadoresDeLaPartida() throws RemoteException {
        ArrayList<Jugador> jugadores = new ArrayList<>(); // por si no hay jugadores que la devuelva vacia para evitar excepcion null

        if(j1 != null && j2 != null){
            if(!reanudoJ1) jugadores.add(j1);
            if(!reanudoJ2) jugadores.add(j2);
        }
        return jugadores;
    }

    // cuando quieren reanudar la partida, recien cuando se unan los dos notifico que puedan empezar, sino inician y les salta la advertencia
    @Override
    public void reanudoPartida(int idJugador) throws RemoteException {

        if(idJugador == j1.getIDJugador()) {
            reanudoJ1 = true;
            notificarEvento(RESTABLECIO_J1);
        }
        else if(idJugador == j2.getIDJugador()) {
            reanudoJ2 = true;
            notificarEvento(RESTABLECIO_J2);
        }

        if(reanudoJ1 && reanudoJ2) {
            reanudoJ1 = false;
            reanudoJ2 = false;  // reanudo la partida y despues seteo de vuelta que no reanudaron, por si se salen de vuelta y quieren reanudar.
            idJugadorSalio = 0;
            notificarEvento(RESTABLECER_PARTIDA);
        }
    }

    @Override
    public void jugadorSalioDePartida(int idJugador) throws RemoteException {
        idJugadorSalio = idJugador;
        notificarEvento(SALIO_DE_PARTIDA);;
    }

    @Override
    public void actualizarPuntos() throws RemoteException {
        anotador.sumarPuntosJ1(puntajeRondaJ1);
        anotador.sumarPuntosJ2(puntajeRondaJ2);
        notificarEvento(PUNTAJES);
    }

    @Override
    public ArrayList<Carta> obtenerCartas(int idJugador) throws RemoteException {
        if(idJugador == j1.getIDJugador()) return cartasJ1;
        else return cartasJ2;
    }

    @Override
    public void actualizarListaJugadores() throws RemoteException {
        notificarEvento(LISTA_JUGADORES_DISPONIBLES);
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
    public EstadoFlor estadoFlor() throws RemoteException {
        return estadoDeLaFlor;
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
        if(anotador == null) return "ID: " + idPartida;
        return "ID: " + idPartida + " | " + anotador.toString();
    }


    //
    // SETS y GETS
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
    public int getQuienCantoFlor() throws RemoteException {
        return quienCantoFlor;
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
    public int getIdJugadorQuiereCantar() throws RemoteException {
        return idJugadorQuiereCantar;
    }

    @Override
    public String getUltimoMensaje() throws RemoteException{
        return ultimoMensaje;
    }

    @Override
    public ArrayList<Carta> getCartasJ1() throws RemoteException{
        return cartasJ1;
    }
    @Override
    public ArrayList<Carta> getCartasJ2() throws RemoteException{
        return cartasJ2;
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
    public ArrayList<Jugador> getJugadores() throws RemoteException{
        ArrayList<Jugador> lista = new ArrayList<>();
        if(j1 != null && !reanudoJ1){
            lista.add(j1);
        }                       // si no son nulos los agrega y listo, para el recuperar partida se borren. Ademas si estan elegidos
                                // que no los lleve a la lista que actualiza y da la sensacion de borrado cuando se elige
        if(j2 != null && !reanudoJ2){
            lista.add(j2);
        }
        return lista;
    }

    @Override
    public boolean getSeJuegaConFlor() throws RemoteException {
        return seJuegaConFlor;
    }

    @Override
    public boolean getReanudoJ1() throws RemoteException {
        return reanudoJ1;
    }

    @Override
    public boolean getReanudoJ2() throws RemoteException {
        return reanudoJ2;
    }

    @Override
    public boolean getSeEstabaCantandoTruco() throws RemoteException {
        return seEstabaCantandoTruco;
    }

    @Override
    public boolean getSeEstabaCantandoTanto() throws RemoteException {
        return seEstabaCantandoEnvido;
    }

    @Override
    public boolean getSeEstabaCantandoFlor() throws RemoteException {
        return seEstabaCantandoFlor;
    }

    @Override
    public int getUltimoJugadorCanto() throws RemoteException {
        return idUltimoJugCanto;
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
    public int getIDJugadorGanador() throws RemoteException {
        if(anotador.getPuntosJ1() > anotador.getPuntosJ2()) return j1.getIDJugador();
        else return j2.getIDJugador();
    }

    @Override
    public int getIDJugadorSalio() throws RemoteException {
        return idJugadorSalio;
    }

    @Override
    public String getNombreRival(int idJugador) throws RemoteException {
        if(j1.getIDJugador() != idJugador) return j1.getNombre();
        else return j2.getNombre();
    }

    @Override
    public ArrayList<Carta> getCartasTiradasJ1() throws RemoteException {
        if(cartasTiradasJ1 == null) cartasTiradasJ1 = new ArrayList<>();
        return cartasTiradasJ1;
    }

    @Override
    public ArrayList<Carta> getCartasTiradasJ2() throws RemoteException {
        if(cartasTiradasJ2 == null) cartasTiradasJ2 = new ArrayList<>();
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



    //
    // metodos privados
    //

    // le paso dos cartas y las compara, devuelve el ID del jugador que tiene la carta mas alta, si son de igual poder devuelve -1 que indica que es PARDA
    private int compararCartas(Carta cj1, Carta cj2) throws RemoteException{
        if(cj1.getPoderCarta() > cj2.getPoderCarta()) return j1.getIDJugador();
        else if(cj1.getPoderCarta() < cj2.getPoderCarta()) return j2.getIDJugador();

        return -1; // caso de que sean el mismo poder es PARDA
    }


    // notificar general para el notificar un evento, que este se le pasa por parametro
    private void notificarEvento(Eventos e) throws RemoteException {
        notificarObservadores(e);
        PersistenciaPartida.guardarPartida(this); // guardo la partida aca porque actualizan cosas que necesito persistir en cada actualizacion
    }


    // me calcula cuantos puntos gana el que ganó el envido según que se cantó
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

    // Se canto el envido y se dijo que no, calcula cuantos puntos son para el que canto (contrario al que dijo que no)
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

    private int calcularPuntajeFlorQuerida(){
        int puntos = 0;

        switch (estadoDeLaFlor){
            case FLOR -> puntos = 3;
            case CONTRA_FLOR -> puntos = 6;
            case CONTRA_FLOR_AL_RESTO  -> puntos = puntosParaGanar;
        }

        return puntos;
    }

    private int calcularPuntajeFlorNoQuerida(){
        int puntos = 0;

        switch (estadoDeLaFlor){
            case FLOR -> puntos = 3;
            case CONTRA_FLOR -> puntos = 4;
            case CONTRA_FLOR_AL_RESTO  -> puntos = 6;
        }

        return puntos;
    }

    private int calcularPuntosTruco(){
        int puntos = 0;

        // por reglas si se va al mazo en la primer mano sin cantar nada son 2 puntos para el contrario
        if(numeroRonda == 1 && !cantoEnvido) puntos = 2;
        //si no es primera ronda o si se canto envido entra en el de abajo
        else if(estadoDelTruco == EstadoTruco.NADA) puntos = 1;
        else if(estadoDelTruco == TRUCO) puntos = 2;
        else if(estadoDelTruco == RE_TRUCO) puntos = 3;
        else if(estadoDelTruco == VALE_CUATRO) puntos = 2;

        return puntos;
    }

    // Genera el ID de la partida trantando que sea lo mas unico posible
    private int generarIdPartida(){
        Random random = new Random();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDate = now.format(formatter);

        String nombre = " ";
        if(j1 != null) nombre = j1.getNombre();

        String idString = nombre.substring(0, Math.min(3, nombre.length())).toUpperCase() + formattedDate;

        return Math.abs(idString.hashCode() / random.nextInt(1, 18));

    }

    private void replicarCartasJugadores(){
        if(j1.getCartasObtenidas() == null || j2.getCartasObtenidas() == null) return;
        cartasJ1.add(j1.getCartasObtenidas().get(0));
        cartasJ1.add(j1.getCartasObtenidas().get(1));
        cartasJ1.add(j1.getCartasObtenidas().get(2));

        cartasJ2.add(j2.getCartasObtenidas().get(0));
        cartasJ2.add(j2.getCartasObtenidas().get(1));
        cartasJ2.add(j2.getCartasObtenidas().get(2));
    }

}
