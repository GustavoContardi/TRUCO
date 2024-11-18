package persistencia;

import enums.EstadoEnvido;
import enums.EstadoTruco;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class PersistenciaCantos implements Serializable {
    private static ArrayList<String> listaCantos;
    private static ArrayList<ArrayList<String>> listaCantosGeneral;

    public PersistenciaCantos() {
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
}
