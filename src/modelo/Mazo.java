package modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Mazo implements Serializable {
    // atributos
    private ArrayList<Carta> mazoCartas = new ArrayList<>();

    // constructor

    public Mazo() {
        // levanto el mazo del archivo
        recuperarMazo();
    }



    // metodos publicos

    public void repartirCartas(Jugador j1, Jugador j2){
        ArrayList<Carta> cartasJ1 = new ArrayList<>();
        ArrayList<Carta> cartasJ2 = new ArrayList<>();
        Random random = new Random();
        int indiceAleatorio;

        mezclarCartas();
        while(cartasJ1.size() < 3){
            indiceAleatorio = random.nextInt(mazoCartas.size());
            if(cartasJ1.isEmpty()) cartasJ1.add(mazoCartas.get(indiceAleatorio));
            else if(!cartasJ1.contains(mazoCartas.get(indiceAleatorio))) cartasJ1.add(mazoCartas.get(indiceAleatorio));
        }

        mezclarCartas();

        while(cartasJ2.size() < 3){
            indiceAleatorio = random.nextInt(mazoCartas.size());
            if(cartasJ2.isEmpty() && !cartasJ1.contains(mazoCartas.get(indiceAleatorio))) {
                cartasJ2.add(mazoCartas.get(indiceAleatorio));
            }
            else if(!cartasJ1.contains(mazoCartas.get(indiceAleatorio)) && !cartasJ2.contains(mazoCartas.get(indiceAleatorio))) {
                cartasJ2.add(mazoCartas.get(indiceAleatorio));
            }
        }

        j1.recibirCartas(cartasJ1);
        j2.recibirCartas(cartasJ2);

    }

    public void mostrarMazo(){
        for(int i=0; i<mazoCartas.size(); i++){
            System.out.println(mazoCartas.get(i).toString());
        }
    }


    // metodos privados

    private void mezclarCartas(){
        Collections.shuffle(mazoCartas);
    }

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

    // GETTERS y SETTERS

    public ArrayList<Carta> getMazoCartas() {
        return mazoCartas;
    }

    public void setMazoCartas(ArrayList<Carta> mazoCartas) {
        this.mazoCartas = mazoCartas;
    } // ???
}
