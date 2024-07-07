package persistencia;

import enums.EstadoEnvido;
import enums.EstadoTruco;
import modelo.Jugador;
import modelo.Mazo;
import modelo.Partida;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class Persistencia implements Comparable<Jugador>, Serializable {

    private static ArrayList<ArrayList<String>> listaCantosGeneral;
    private static ArrayList<String> listaCantos;
    private static ArrayList<Jugador> listaJugadores;
    private static ArrayList<Partida> listaPartidas;
    private static Jugador jugador;
    private static Partida partida;
    private static Mazo mazo;

    public Persistencia() {
    }

    //
    // metodos publicos estaticos para no tener que instanciar
    //

    public static Partida recuperarPartidaGuardada(int id){
        return null;
    }

    public static void guardarPartida(Partida partida){

    }

    public static Mazo recuperarMazo(){
        try {
            FileInputStream fos = new FileInputStream("mazoDeCartas.bin");
            var oos = new ObjectInputStream(fos);
            mazo = (Mazo) oos.readObject();
            fos.close();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return mazo;
    }

    public static String mensajeCantoTruco(EstadoTruco estado){
        Random random = new Random();

        try {
            FileInputStream fos = new FileInputStream("Cantos.bin");
            var oos = new ObjectInputStream(fos);
            listaCantosGeneral = (ArrayList<ArrayList<String>>) oos.readObject();
            fos.close();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        switch (estado){
            case TRUCO -> listaCantos = listaCantosGeneral.get(2);
            case RE_TRUCO -> listaCantos = listaCantosGeneral.get(3);
            case VALE_CUATRO ->  listaCantos = listaCantosGeneral.get(4);
        }

        return listaCantos.get(random.nextInt(listaCantos.size()));
    }

    public static String mensajeCantoTanto(EstadoEnvido estado){
        Random random = new Random();


        try {
            FileInputStream fos = new FileInputStream("Cantos.bin");
            var oos = new ObjectInputStream(fos);
            listaCantosGeneral = (ArrayList<ArrayList<String>>) oos.readObject();
            fos.close();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        switch (estado){
            case ENVIDO -> listaCantos = listaCantosGeneral.get(5);
            case ENVIDO_DOBLE -> listaCantos = listaCantosGeneral.get(5);
            case REAL_ENVIDO ->  listaCantos = listaCantosGeneral.get(6);
            case FALTA_ENVIDO ->  listaCantos = listaCantosGeneral.get(7);

        }

        return listaCantos.get(random.nextInt(listaCantos.size()));
    }

    public static String mensajeCantoQuiero(){
        Random random = new Random();


        try {
            FileInputStream fos = new FileInputStream("Cantos.bin");
            var oos = new ObjectInputStream(fos);
            listaCantosGeneral = (ArrayList<ArrayList<String>>) oos.readObject();
            fos.close();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        listaCantos = listaCantosGeneral.get(0);

        return listaCantos.get(random.nextInt(listaCantos.size()));
    }

    public static String mensajeCantoNoQuiero(){
        int numeroALeatorio = 0;
        Random random = new Random();


        try {
            FileInputStream fos = new FileInputStream("Cantos.bin");
            var oos = new ObjectInputStream(fos);
            listaCantosGeneral = (ArrayList<ArrayList<String>>) oos.readObject();
            fos.close();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        listaCantos = listaCantosGeneral.get(1);

        return listaCantos.get(random.nextInt(listaCantos.size()));
    }

    public static Jugador recuperarJugador(int id){
        listaJugadores = listaJugadoresGuardados(false);

        for(Jugador j : listaJugadores){
            if(j.getIDJugador() == id) return j;
        }

        return null;
    }

    public static void guardarJugador(Jugador j){
        listaJugadores = listaJugadoresGuardados(false);

        if(listaJugadores == null) listaJugadores = new ArrayList<>();

        listaJugadores.add(j);

        try {
            FileOutputStream fos = new FileOutputStream("jugadores.bin");
            var oos = new ObjectOutputStream(fos);
            oos.writeObject(listaJugadores);
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void eliminarJugador(int idJugador){
        listaJugadores = listaJugadoresGuardados(false);

        for(Jugador j : listaJugadores){
            if(j.getIDJugador() == idJugador) listaJugadores.remove(j);
        }

        try {
            FileOutputStream fos = new FileOutputStream("jugadores.bin");
            var oos = new ObjectOutputStream(fos);
            oos.writeObject(listaJugadores);
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void removeAllJugadores(){
        listaJugadores = listaJugadoresGuardados(false);

        listaJugadores.clear();

        try {
            FileOutputStream fos = new FileOutputStream("jugadores.bin");
            var oos = new ObjectOutputStream(fos);
            oos.writeObject(listaJugadores);
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Jugador> listaJugadoresGuardados(boolean ordenado){
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

        if(ordenado && (listaJugadores != null) ) Collections.sort(listaJugadores);

        return listaJugadores;
    }

    public static void jugadorElecto(int idJugador){
        listaJugadores = listaJugadoresGuardados(false);

        for(Jugador j : listaJugadores){
            if(j.getIDJugador() == idJugador) j.jugadorFueElecto();
        }

        // modifico la lista y la sobreescribo con el atributo electo modificado
        try {
            FileOutputStream fos = new FileOutputStream("jugadores.bin");
            var oos = new ObjectOutputStream(fos);
            oos.writeObject(listaJugadores);
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sumarPartidaGanadaJugador(int idJugador){
        for(Jugador j : listaJugadores){
            if(j.getIDJugador() == idJugador) j.jugadorFueElecto();
        }

        jugador.partidaGanada();

        // modifico la lista y la sobreescribo con el atributo partidasGanadas modificado (1 +)
        try {
            FileOutputStream fos = new FileOutputStream("jugadores.bin");
            var oos = new ObjectOutputStream(fos);
            oos.writeObject(listaJugadores);
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void delvolverTodosJugadores(){
        listaJugadores = listaJugadoresGuardados(false);

        if(listaJugadores != null){
            for(Jugador j : listaJugadores){
                j.jugadorFueDevuelto();
            }
        }

        // modifico la lista y la sobreescribo con el atributo electo modificado

        try {
            FileOutputStream fos = new FileOutputStream("jugadores.bin");
            var oos = new ObjectOutputStream(fos);
            oos.writeObject(listaJugadores);
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int compareTo(Jugador o) {
        return Integer.compare(o.getPartidasGanadas(), jugador.getPartidasGanadas());
    }
}
