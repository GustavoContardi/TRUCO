package persistencia;

import interfaces.IModelo;
import modelo.Jugador;
import modelo.Partida;

import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;

public class PersistenciaPartida implements Serializable{
    private static Partida              partida;
    private static ArrayList<Partida>   listaPartidas;

    public PersistenciaPartida() {
    }

    // metodos publicos staticos

    public static void guardarPartida(Partida partida) throws RemoteException {
        listaPartidas = listaPartidasGuardadas();

        if(listaPartidas == null) listaPartidas = new ArrayList<>();

        listaPartidas.removeIf(p -> {
            try {
                return p.getIdPartida() == partida.getIdPartida();
            } catch (RemoteException e) {                               // elimina la partida
                throw new RuntimeException(e);
            }
        });

        listaPartidas.add(partida);

        try {
            FileOutputStream fos = new FileOutputStream("partidas.bin");
            var oos = new ObjectOutputStream(fos);
            oos.writeObject(listaPartidas);
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void eliminarPartida(int idPartida) throws RemoteException {
        listaPartidas = listaPartidasGuardadas();

        if(listaPartidas == null) return;

        listaPartidas.removeIf(p -> {
            try {
                return p.getIdPartida() == partida.getIdPartida();
            } catch (RemoteException e) {                               // elimina la partida
                throw new RuntimeException(e);
            }
        });

        // modifique la lista y la guardo de nuevo, sobreescribo el archivo;

        try {
            FileOutputStream fos = new FileOutputStream("partidas.bin");
            var oos = new ObjectOutputStream(fos);
            oos.writeObject(listaPartidas);
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Partida recuperarPartida(int idPartida) throws RemoteException {
        listaPartidas = listaPartidasGuardadas();

        for(Partida game : listaPartidas){
            if(game.getIdPartida() == idPartida) partida = game;
        }


        return partida;
    }

    public static ArrayList<Partida> listaPartidasGuardadas(){
        try {
            FileInputStream fos = new FileInputStream("partidas.bin");
            var oos = new ObjectInputStream(fos);
            listaPartidas = (ArrayList<Partida>) oos.readObject();
            fos.close();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if(listaPartidas == null) {
            listaPartidas = new ArrayList<>(); // si es null le incicializo la lista aca para que no de errores despues
            System.out.println("no arreglamos el null aca?");
        }

        return listaPartidas;
    }

}
