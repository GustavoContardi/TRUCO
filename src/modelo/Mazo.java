package modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Mazo implements Serializable {

    //
    // Atributos
    //

    private ArrayList<Carta> mazoCartas = new ArrayList<>(); // mazo que contiene las 40 cartas

    //
    // constructor
    //

    public Mazo() {
        // levanto el mazo del archivo
        recuperarMazo();
    }


    //
    // metodos publicos
    //

    // le asigna las cartas a los jugadores pasados por parametros
    public void repartirCartas(Jugador j1, Jugador j2) {
        ArrayList<Carta> cartasJ1 = new ArrayList<>();
        ArrayList<Carta> cartasJ2 = new ArrayList<>();
        Random random = new Random();
        int indiceAleatorio;

        mezclarCartas();
        while (cartasJ1.size() < 3) {
            indiceAleatorio = random.nextInt(mazoCartas.size());
            if (cartasJ1.isEmpty()) cartasJ1.add(mazoCartas.get(indiceAleatorio));
            else if (!cartasJ1.contains(mazoCartas.get(indiceAleatorio))) cartasJ1.add(mazoCartas.get(indiceAleatorio));
        }

        mezclarCartas();

        while (cartasJ2.size() < 3) {
            indiceAleatorio = random.nextInt(mazoCartas.size());
            if (cartasJ2.isEmpty() && !cartasJ1.contains(mazoCartas.get(indiceAleatorio))) {
                cartasJ2.add(mazoCartas.get(indiceAleatorio));
            } else if (!cartasJ1.contains(mazoCartas.get(indiceAleatorio)) && !cartasJ2.contains(mazoCartas.get(indiceAleatorio))) {
                cartasJ2.add(mazoCartas.get(indiceAleatorio));
            }
        }

        j1.recibirCartas(cartasJ1);
        j2.recibirCartas(cartasJ2);

    }

    public void mostrarMazo() {
        for (int i = 0; i < mazoCartas.size(); i++) {
            System.out.println(mazoCartas.get(i).toString());
        }
    }

    // este metodo es pura y exclusivamente para hacer los TEST de la flor in game
    public void repartirFlor(Jugador j1, Jugador j2){
        ArrayList<Carta> lista1 = new ArrayList<>();
        ArrayList<Carta> lista2 = new ArrayList<>();

        lista1.add(mazoCartas.get(0));
        lista1.add(mazoCartas.get(1));
        lista1.add(mazoCartas.get(2));

        lista2.add(mazoCartas.get(11));
        lista2.add(mazoCartas.get(12));
        lista2.add(mazoCartas.get(14));

        j1.recibirCartas(lista1);
        j2.recibirCartas(lista2);
    }

    //
    // metodos privados
    //


    // mezcla las cartas dentro del ArrayList
    private void mezclarCartas() {
        Collections.shuffle(mazoCartas);
    }

    // levanto la lista del archivo
    private void recuperarMazo() {
        try {
            FileInputStream fos = new FileInputStream("mazoDeCartas.bin");
            var oos = new ObjectInputStream(fos);
            mazoCartas = (ArrayList<Carta>) oos.readObject();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //
    // GETTERS y SETTERS
    //

    public ArrayList<Carta> getMazoCartas() {
        return mazoCartas;
    }

}
