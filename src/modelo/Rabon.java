package modelo;

import enums.EstadoTruco;

import java.io.Serializable;

import static enums.EstadoTruco.*;

public class Rabon implements Serializable {
    private     int             quienCantoTruco, quienCantoReTruco,quienCantoValeCuatro;
    private     EstadoTruco     estadoDelTruco;


    //
    // constructor
    //

    public Rabon() {
        resetValores();
    }

    //
    // metodos publicos
    //

    public void resetValores(){
        quienCantoTruco = 0;
        quienCantoReTruco = 0;
        quienCantoValeCuatro = 0;
        estadoDelTruco = NADA;
    }

    public int calcularPuntosRabon(){
        int puntos = 0;

        if(estadoDelTruco == EstadoTruco.NADA) puntos = 1;
        else if(estadoDelTruco == TRUCO) puntos = 2;
        else if(estadoDelTruco == RE_TRUCO) puntos = 3;
        else if(estadoDelTruco == VALE_CUATRO) puntos = 4;

        return puntos;
    }


    //
    // gets y sets
    //


    public int getQuienCantoTruco() {
        return quienCantoTruco;
    }

    public void setQuienCantoTruco(int quienCantoTruco) {
        this.quienCantoTruco = quienCantoTruco;
    }

    public int getQuienCantoReTruco() {
        return quienCantoReTruco;
    }

    public void setQuienCantoReTruco(int quienCantoReTruco) {
        this.quienCantoReTruco = quienCantoReTruco;
    }

    public int getQuienCantoValeCuatro() {
        return quienCantoValeCuatro;
    }

    public void setQuienCantoValeCuatro(int quienCantoValeCuatro) {
        this.quienCantoValeCuatro = quienCantoValeCuatro;
    }

    public EstadoTruco getEstadoDelTruco() {
        return estadoDelTruco;
    }

    public void setEstadoDelTruco(EstadoTruco estadoDelTruco) {
        this.estadoDelTruco = estadoDelTruco;
    }
}
