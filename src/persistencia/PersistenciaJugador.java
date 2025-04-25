package persistencia;

import modelo.Jugador;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class PersistenciaJugador implements Comparable<Jugador>, Serializable{
    private static Jugador jugador;
    private static ArrayList<Jugador> listaJugadoresActivos;
    private static ArrayList<Jugador> listaJugadoresHistoricos;

    public PersistenciaJugador() {
    }

    // metodos statics publicos

    public static Jugador recuperarJugador(int id){
        listaJugadoresActivos = listaJugadoresGuardados(false);

        for(Jugador j : listaJugadoresActivos){
            if(j.getIDJugador() == id) return j;
        }

        return null;
    }

    public static void guardarJugador(Jugador j){
        listaJugadoresActivos = listaJugadoresGuardados(false);

        if(listaJugadoresActivos == null) listaJugadoresActivos = new ArrayList<>();

        listaJugadoresActivos.add(j);

        try {
            FileOutputStream fos = new FileOutputStream("jugadoresActivos.bin");
            var oos = new ObjectOutputStream(fos);
            oos.writeObject(listaJugadoresActivos);
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Jugador> listaJugadoresGuardados(boolean ordenado){
        try {
            FileInputStream fos = new FileInputStream("jugadoresActivos.bin");
            var oos = new ObjectInputStream(fos);
            listaJugadoresActivos = (ArrayList<Jugador>) oos.readObject();
            fos.close();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if(ordenado && (listaJugadoresActivos != null) ) Collections.sort(listaJugadoresActivos);
        if(listaJugadoresActivos == null) listaJugadoresActivos = new ArrayList<>();

        return listaJugadoresActivos;
    }

    public static void eliminarJugador(int idJugador){
        listaJugadoresActivos = listaJugadoresGuardados(false);

        if(listaJugadoresActivos != null) listaJugadoresActivos.removeIf(j -> j.getIDJugador() == idJugador);

        try {
            FileOutputStream fos = new FileOutputStream("jugadoresActivos.bin");
            var oos = new ObjectOutputStream(fos);
            oos.writeObject(listaJugadoresActivos);
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void actualizarNombre(int idJugador, String nuevoNombre){
        Jugador jugEliminado = null;

        for(Jugador j : listaJugadoresActivos){
            if(j.getIDJugador() == idJugador) {
                jugEliminado = j;
                eliminarJugador(idJugador);
            }
        }

        listaJugadoresActivos = listaJugadoresGuardados(false);
        jugEliminado.setNombre(nuevoNombre);
        listaJugadoresActivos.add(jugEliminado);

        try {
            FileOutputStream fos = new FileOutputStream("jugadoresActivos.bin");
            var oos = new ObjectOutputStream(fos);
            oos.writeObject(listaJugadoresActivos);
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void jugadorElecto(int idJugador){
        listaJugadoresActivos = listaJugadoresGuardados(false);

        for(Jugador j : listaJugadoresActivos){
            if(j.getIDJugador() == idJugador) j.jugadorFueElecto();
        }

        // modifico la lista y la sobreescribo con el atributo electo modificado
        try {
            FileOutputStream fos = new FileOutputStream("jugadoresActivos.bin");
            var oos = new ObjectOutputStream(fos);
            oos.writeObject(listaJugadoresActivos);
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sumarPartidaGanadaJugador(int idJugador){
        for(Jugador j : listaJugadoresActivos){
            if(j.getIDJugador() == idJugador) j.partidaGanada();
        }

        // modifico la lista y la sobreescribo con el atributo partidasGanadas modificado (1 +)
        try {
            FileOutputStream fos = new FileOutputStream("jugadoresActivos.bin");
            var oos = new ObjectOutputStream(fos);
            oos.writeObject(listaJugadoresActivos);
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void delvolverTodosJugadores(){
        listaJugadoresActivos = listaJugadoresGuardados(false);

        if(listaJugadoresActivos != null){
            for(Jugador j : listaJugadoresActivos){
                j.jugadorFueDevuelto();
            }
        }

        // modifico la lista y la sobreescribo con el atributo electo modificado

        try {
            FileOutputStream fos = new FileOutputStream("jugadoresActivos.bin");
            var oos = new ObjectOutputStream(fos);
            oos.writeObject(listaJugadoresActivos);
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
