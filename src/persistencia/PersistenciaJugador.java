package persistencia;

import modelo.Jugador;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class PersistenciaJugador implements Comparable<Jugador>, Serializable{
    private static Jugador jugador;
    private static ArrayList<Jugador> listaJugadores;

    public PersistenciaJugador() {
    }

    // metodos statics publicos

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
        if(listaJugadores == null) listaJugadores = new ArrayList<>();

        return listaJugadores;
    }

    public static void eliminarJugador(int idJugador){
        listaJugadores = listaJugadoresGuardados(false);

        if(listaJugadores != null) listaJugadores.removeIf(j -> j.getIDJugador() == idJugador);

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

    public static void actualizarNombre(int idJugador, String nuevoNombre){
        Jugador jugEliminado = null;

        for(Jugador j : listaJugadores){
            if(j.getIDJugador() == idJugador) {
                jugEliminado = j;
                eliminarJugador(idJugador);
            }
        }

        listaJugadores = listaJugadoresGuardados(false);
        jugEliminado.setNombre(nuevoNombre);
        listaJugadores.add(jugEliminado);

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
            if(j.getIDJugador() == idJugador) j.partidaGanada();
        }

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

    public static void delvolverJugadores(int idJ1, int idJ2){ // este metodo devuelve los jugadores que estaban en una partida y finalizó
        listaJugadores = listaJugadoresGuardados(false);

        if(listaJugadores != null){
            for(Jugador j : listaJugadores){
                if (j.getIDJugador() == idJ1 || j.getIDJugador() == idJ2) j.jugadorFueDevuelto();
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
