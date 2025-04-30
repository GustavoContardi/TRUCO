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
        listaJugadoresActivos = getJugadoresGuardados(false);

        for(Jugador j : listaJugadoresActivos){
            if(j.getIDJugador() == id) return j;
        }

        return null;
    }

    public static void guardarJugador(Jugador j){
        listaJugadoresActivos = getJugadoresGuardados(false);

        if(listaJugadoresActivos == null) listaJugadoresActivos = new ArrayList<>();

        listaJugadoresActivos.add(j);
        guardarJugadorHistorico(j);

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

    public static ArrayList<Jugador> getJugadoresGuardados(boolean ordenado){
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

        if(listaJugadoresActivos == null) listaJugadoresActivos = new ArrayList<>();
        Collections.sort(listaJugadoresActivos);

        return listaJugadoresActivos;
    }

    public static void eliminarJugador(int idJugador){
        listaJugadoresActivos = getJugadoresGuardados(false);

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

        listaJugadoresActivos = getJugadoresGuardados(false);
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
        listaJugadoresActivos = getJugadoresGuardados(false);

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
        listaJugadoresActivos = getJugadoresGuardados(false);
        listaJugadoresHistoricos = getJugadoresHistoricos();

        if(listaJugadoresActivos != null){
            for(Jugador j : listaJugadoresActivos){
                j.jugadorFueDevuelto();
            }
        }

        if(listaJugadoresHistoricos != null){
            for(Jugador j : listaJugadoresHistoricos){
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

        try {
            FileInputStream fos = new FileInputStream("jugadoresHistoricos.bin");
            var oos = new ObjectInputStream(fos);
            listaJugadoresHistoricos = (ArrayList<Jugador>) oos.readObject();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    //
    // jugadores historicos
    //

    public static void guardarJugadorHistorico(Jugador jugador){
        listaJugadoresHistoricos = getJugadoresHistoricos();

        if(listaJugadoresHistoricos == null) listaJugadoresHistoricos = new ArrayList<>();

        listaJugadoresHistoricos.add(jugador);

        try {
            FileOutputStream fos = new FileOutputStream("jugadoresHistoricos.bin");
            var oos = new ObjectOutputStream(fos);
            oos.writeObject(listaJugadoresActivos);
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static ArrayList<Jugador> getJugadoresHistoricos(){
        try {
            FileInputStream fos = new FileInputStream("jugadoresHistoricos.bin");
            var oos = new ObjectInputStream(fos);
            listaJugadoresHistoricos = (ArrayList<Jugador>) oos.readObject();
            fos.close();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if(listaJugadoresHistoricos == null) listaJugadoresHistoricos = new ArrayList<>();
        Collections.sort(listaJugadoresHistoricos); // lo devuelvo si o si ordenado sino no tiene sentido

        return listaJugadoresHistoricos;
    }



    @Override
    public int compareTo(Jugador o) {
        return Integer.compare(o.getPartidasGanadas(), jugador.getPartidasGanadas());
    }
}
