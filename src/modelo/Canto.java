package modelo;

import persistencia.PersistenciaCantos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Canto implements Serializable {

    private     ArrayList<String>   listaCantos;

    public Canto() {
        listaCantos = new ArrayList<>();
    }

    //
    // metodos publicos
    //

    //retorna algun canto aleatorio que incluya QUIERO
    public String getCantoQuiero(){
        listaCantos = PersistenciaCantos.mensajeCantoQuiero();
        Random random = new Random();

        return listaCantos.get(random.nextInt(listaCantos.size()));
    }

    public String getCantoNoQuiero(){
        listaCantos = PersistenciaCantos.mensajeCantoNoQuiero();
        Random random = new Random();

        return listaCantos.get(random.nextInt(listaCantos.size()));
    }

    public String getCantoFlor(){
        listaCantos = PersistenciaCantos.mensajeCantoFlor();
        Random random = new Random();

        return listaCantos.get(random.nextInt(listaCantos.size()));
    }

    public String getCantoContraFlor(){
        listaCantos = PersistenciaCantos.mensajeCantoContraFlor();
        Random random = new Random();

        return listaCantos.get(random.nextInt(listaCantos.size()));
    }

    public String getCantoContraFlorAlResto(){
        listaCantos = PersistenciaCantos.mensajeCantoContraFlorResto();
        Random random = new Random();

        return listaCantos.get(random.nextInt(listaCantos.size()));
    }

    public String getCantoTruco(){
        listaCantos = PersistenciaCantos.mensajeCantoTruco();
        Random random = new Random();

        return listaCantos.get(random.nextInt(listaCantos.size()));
    }

    public String getCantoReTruco(){
        listaCantos = PersistenciaCantos.mensajeCantoReTruco();
        Random random = new Random();

        return listaCantos.get(random.nextInt(listaCantos.size()));
    }

    public String getCantoValeCuatro(){
        listaCantos = PersistenciaCantos.mensajeCantoValeCuatro();
        Random random = new Random();

        return listaCantos.get(random.nextInt(listaCantos.size()));
    }

    public String getCantoEnvido(){
        listaCantos = PersistenciaCantos.mensajeCantoEnvido();
        Random random = new Random();

        return listaCantos.get(random.nextInt(listaCantos.size()));
    }

    public String getCantoEnvidoDoble(){
        listaCantos = PersistenciaCantos.mensajeCantoEnvido();
        Random random = new Random();

        return listaCantos.get(random.nextInt(listaCantos.size()));
    }

    public String getCantoRealEnvido(){
        listaCantos = PersistenciaCantos.mensajeCantoRealEnvido();
        Random random = new Random();

        return listaCantos.get(random.nextInt(listaCantos.size()));
    }

    public String getCantoFaltaEnvido(){
        listaCantos = PersistenciaCantos.mensajeCantoFaltaEnvido();
        Random random = new Random();

        return listaCantos.get(random.nextInt(listaCantos.size()));
    }
}
