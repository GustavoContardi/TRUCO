package persistencia;

import modelo.Jugador;
import modelo.Mazo;
import modelo.Partida;

import java.util.ArrayList;

public class Persistencia {

    private static ArrayList<String> listaCantos;
    private static ArrayList<Jugador> listaJugadores;
    private static Jugador jugador;
    private static Partida partida;
    private static Mazo mazo;

    public Persistencia() {
    }

    //
    // metodos publicos estaticos para no tener que instanciar
    //

    public static Partida partidaGuardada(){
        return null;
    }

    public static Mazo recuperarMazo(){
        return null;
    }

    public static String cantoTruco(){
        return "";
    }

    public static String cantoTanto(){
        return "";
    }

    public static String cantoQuiero(){
        return "";
    }

    public static String cantoNoQuiero(){
        return "";
    }

    public static Jugador recuperarJugador(int id){
        return jugador;
    }

    public static ArrayList<Jugador> listaJugadoresGuardados(boolean ordenado){
        return listaJugadores;
    }
}
