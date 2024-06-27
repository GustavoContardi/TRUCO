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

    public static void guardarPartida(){

    }

    public static Mazo recuperarMazo(){
        return null;
    }

    public static String mensajeCantoTruco(EstadoTruco estado){
        return "";
    }

    public static String mensajeCantoTanto(EstadoEnvido estado){
        return "";
    }

    public static String mensajeCantoQuiero(){
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
        return jugador;
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

    public void eliminarJugador(int id){
        listaJugadores = listaJugadoresGuardados(false);

        for(Jugador j : listaJugadores){
            if(j.getIDJugador() == id) listaJugadores.remove(j);
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
    public void removeAllJugadores(){
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

        if(ordenado) Collections.sort(listaJugadores);

        return listaJugadores;
    }

    @Override
    public int compareTo(Jugador o) {
        return Integer.compare(o.getPartidasGanadas(), jugador.getPartidasGanadas());
    }
}
