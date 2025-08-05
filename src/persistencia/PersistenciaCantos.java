package persistencia;

import enums.EstadoEnvido;
import enums.EstadoTruco;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class PersistenciaCantos implements Serializable {
    private static ArrayList<String> listaCantos;
    private static ArrayList<ArrayList<String>> listaCantosGeneral;

    public PersistenciaCantos() {
    }

    public static ArrayList<String> mensajeCantoTruco(){

        try {
            FileInputStream fos = new FileInputStream("src/recursos/archivo/Cantos.bin");
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

        return listaCantosGeneral.get(2);
    }

    public static ArrayList<String> mensajeCantoReTruco(){

        try {
            FileInputStream fos = new FileInputStream("src/recursos/archivo/Cantos.bin");
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

        return listaCantosGeneral.get(3);
    }

    public static ArrayList<String> mensajeCantoValeCuatro(){

        try {
            FileInputStream fos = new FileInputStream("src/recursos/archivo/Cantos.bin");
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

        return listaCantosGeneral.get(4);
    }

    public static ArrayList<String> mensajeCantoEnvido(){
        try {
            FileInputStream fos = new FileInputStream("src/recursos/archivo/Cantos.bin");
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

        return listaCantosGeneral.get(5);
    }

    public static ArrayList<String> mensajeCantoRealEnvido(){

        try {
            FileInputStream fos = new FileInputStream("src/recursos/archivo/Cantos.bin");
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

        return listaCantosGeneral.get(6);
    }

    public static ArrayList<String> mensajeCantoFaltaEnvido(){

        try {
            FileInputStream fos = new FileInputStream("src/recursos/archivo/Cantos.bin");
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

        return listaCantosGeneral.get(7);
    }



    public static ArrayList<String> mensajeCantoQuiero(){
        try {
            FileInputStream fos = new FileInputStream("src/recursos/archivo/Cantos.bin");
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

        return listaCantosGeneral.get(0);
   }

    public static ArrayList<String> mensajeCantoNoQuiero(){
        try {
            FileInputStream fos = new FileInputStream("src/recursos/archivo/Cantos.bin");
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

        return listaCantosGeneral.get(1);
    }

    public static ArrayList<String> mensajeCantoFlor(){;

        try {
            FileInputStream fos = new FileInputStream("src/recursos/archivo/Cantos.bin");
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

        return listaCantosGeneral.get(8);
    }

    public static ArrayList<String> mensajeCantoContraFlor(){

        try {
            FileInputStream fos = new FileInputStream("src/recursos/archivo/Cantos.bin");
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

        return listaCantosGeneral.get(9);
    }

    public static ArrayList<String> mensajeCantoContraFlorResto(){

        try {
            FileInputStream fos = new FileInputStream("src/recursos/archivo/Cantos.bin");
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

        return listaCantosGeneral.get(10);
    }
}
