package modelo;

import java.io.Serializable;

public class Anotador implements Serializable {
    // atributos
    private     String   nombreJ1    ;
    private     String   nombreJ2    ;
    private     int      puntosJ1    ;
    private     int      puntosJ2    ;


    // constructor

    public Anotador(String j2, String j1) {
        this.nombreJ2 =  j2  ;
        this.nombreJ1 =  j1  ;
        puntosJ1  =  0   ;
        puntosJ2  =  0   ;
    }

    // metodos publicos

    @Override
    public String toString(){
        return "PUNTOS:  " + nombreJ1 + " : " + puntosJ1 + " || " + nombreJ2 + " : " + puntosJ2;
    }

    public void sumarPuntosJ1(int puntos){
        puntosJ1 += puntos;
    }

    public void sumarPuntosJ2(int puntos){
        puntosJ2 += puntos;
    }


    // metodos privados


    // GETS y SETS


    public void setNombreJ1(String nombreJ1) {
        this.nombreJ1 = nombreJ1;
    }

    public void setNombreJ2(String nombreJ2) {
        this.nombreJ2 = nombreJ2;
    }

    public void setPuntosJ1(int puntosJ1) {
        this.puntosJ1 = puntosJ1;
    }

    public void setPuntosJ2(int puntosJ2) {
        this.puntosJ2 = puntosJ2;
    }
}
