package modelo;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Mesa implements Serializable {

    private ArrayList<Carta> cartasTiradasJ1, cartasTiradasJ2;
    private int idJugador1, idJugador2;
    private int rondasGanadasJ1, rondasGanadasJ2;
    private int turno;
    private int hizoPrimera;
    private int nroMano, nroRonda;
    boolean parda, finMano;
    Carta ultimaCartaTiradaJ1, ultimaCartaTiradaJ2;

    //
    // constructores
    //

    public Mesa(){
        this.idJugador1         =  0;
        this.idJugador2         =  0;
        cartasTiradasJ1         =  new ArrayList<>();
        cartasTiradasJ2         =  new ArrayList<>();
        nroMano                 =  -1;
        nroRonda                =  1;
        rondasGanadasJ1         =  0;
        rondasGanadasJ2         =  0;
        finMano                 = true;
    }

    public Mesa(int idJugador1, int idJugador2) {
        this.idJugador1         =  idJugador1;
        this.idJugador2         =  idJugador2;
        cartasTiradasJ1         =  new ArrayList<>();
        cartasTiradasJ2         =  new ArrayList<>();
        nroMano                 =  -1;
        nroRonda                =  1;
        rondasGanadasJ1         =  0;
        rondasGanadasJ2         =  0;
        finMano                 =  true;
    }

    public void nuevaMano() {
        if(nroMano % 2 == 0) turno  = idJugador2;
        else turno                  =  idJugador1; // esto es para ver quien empieza a jugar en la mano nueva
        parda                       =  false;
        finMano                     =  false;
        hizoPrimera                 =  0;
        rondasGanadasJ1             =  0;
        rondasGanadasJ2             =  0;
        nroMano                     += 1;
        nroRonda                    =  1;
        ultimaCartaTiradaJ1         =  null;
        ultimaCartaTiradaJ2         =  null;
        cartasTiradasJ2.clear();
        cartasTiradasJ1.clear();
    }

    public void tirarCarta(int idJugador, Carta carta) throws RemoteException {
        // agregar las cartas tiradas al array

        if(idJugador == idJugador1) {
            cartasTiradasJ1.add(carta);
            ultimaCartaTiradaJ1 = carta;
        }
        else if(idJugador == idJugador2) {
            cartasTiradasJ2.add(carta);
            ultimaCartaTiradaJ2 = carta;
        }

        siguienteTurno();

        // recien valido cuando los dos tengan las mismas cartas tiradas para poder compararlas
        if((cartasTiradasJ1.size() == cartasTiradasJ2.size()) && !cartasTiradasJ2.isEmpty()) {
            if (nroRonda == 1) {
                // comparo las cartas y le doy el punto de la ronda al jugador que la hizo, ademas es importante guardar quien hizo primera
                if (idJugador1 == compararCartas(cartasTiradasJ1.get(0), cartasTiradasJ2.get(0))) {
                    rondasGanadasJ1 += 1;
                    hizoPrimera = idJugador1;
                    turno = idJugador1;
                } else if (idJugador2 == compararCartas(cartasTiradasJ1.get(0), cartasTiradasJ2.get(0))) {
                    rondasGanadasJ2 += 1;
                    hizoPrimera = idJugador2;
                    turno = idJugador2;
                } else { // si es null/-1 es porque es parda
                    // parda quiere decir que las 2 cartas que tiraron tienen el mismo poder, osea un empate
                    rondasGanadasJ2 += 1;
                    rondasGanadasJ1 += 1;
                    parda = true;
                }
               nroRonda = 2; // sumo ronda para que la proxima valide abajo

            } else if (nroRonda == 2) {
                // primero pregunto si venimos de una parda, tiene prioridad, aca el que tira la carta mas alta gana la mano
                if (parda) {
                    if (idJugador1 == compararCartas(cartasTiradasJ1.get(1), cartasTiradasJ2.get(1))) {
                        rondasGanadasJ1 += 1;

                    } else if (idJugador2 == compararCartas(cartasTiradasJ1.get(1), cartasTiradasJ2.get(1))) {
                        rondasGanadasJ2 += 1;
                    }
                    else {
                        // tambien es parda
                        parda = true;
                        rondasGanadasJ1 += 1;
                        rondasGanadasJ2 += 1;
                    }
                }
                // si no es parda comparo las cartas y sumo a quien gane la ronda
                else if (idJugador1 == compararCartas(cartasTiradasJ1.get(1), cartasTiradasJ2.get(1))) {
                    rondasGanadasJ1 += 1;
                    turno = idJugador1;
                } else if (idJugador2 == compararCartas(cartasTiradasJ1.get(1), cartasTiradasJ2.get(1))) {
                    rondasGanadasJ2 += 1;
                    turno = idJugador2;
                }
                // si es parda de nuevo vamos a la tercer mano y ahi es lo mismo que esta, la carta mas alta gana
                else { // si es null es porque es parda
                    if (idJugador1 == hizoPrimera) rondasGanadasJ1 += 1;
                    else if (idJugador2 == hizoPrimera) rondasGanadasJ2 += 1;
                }
                nroRonda = 3;

            } else if (nroRonda == 3) {
                // lo mismo que la ronda 2
                if (parda) {
                    if (idJugador1 == compararCartas(cartasTiradasJ1.get(2), cartasTiradasJ2.get(2))) {
                        rondasGanadasJ1 += 1;
                    } else if (idJugador2 == compararCartas(cartasTiradasJ1.get(2), cartasTiradasJ2.get(2))) {
                        rondasGanadasJ2 += 1;
                    } else {
                        if (idJugador1 == hizoPrimera) rondasGanadasJ1 += 1;
                        else if (idJugador2 == hizoPrimera) rondasGanadasJ2 += 1;
                    }
                } else if (idJugador1 == compararCartas(cartasTiradasJ1.get(2), cartasTiradasJ2.get(2))) {
                    rondasGanadasJ1 += 1;
                    turno = idJugador1;
                } else if (idJugador2 == compararCartas(cartasTiradasJ1.get(2), cartasTiradasJ2.get(2))) {
                    rondasGanadasJ2 += 1;
                    turno = idJugador2;
                } else { // si es null es porque es parda
                    // si 3era mano tambien es parda, gana el jugador que es mano (el que no repartio)
                    if (idJugador1 == hizoPrimera) rondasGanadasJ1 += 1;
                    else if (idJugador2 == hizoPrimera) rondasGanadasJ2 += 1;
                }


            }
        }

        if(rondasGanadasJ1 >= 2  && rondasGanadasJ1 > rondasGanadasJ2) finMano = true;
        else if (rondasGanadasJ2 >= 2  && rondasGanadasJ1 < rondasGanadasJ2) finMano = true;

    }

    // esto es para manjear los turnos dentro de la mano
    public void siguienteTurno(){
        if(turno == idJugador1) turno = idJugador2;
        else turno = idJugador1;
    }


    //
    // sets y gets
    //

    public boolean esFinDeMano(){
        return finMano;
    }
    public void setFinDeMano(boolean finMano){
        this.finMano = finMano;
    }
    public ArrayList<Carta> getCartasTiradasJ1() {
        return cartasTiradasJ1;
    }

    public ArrayList<Carta> getCartasTiradasJ2() {
        return cartasTiradasJ2;
    }

    public int getIdJugador1() {
        return idJugador1;
    }

    public int getIdJugador2() {
        return idJugador2;
    }

    public void setRondasGanadasJ1(int rondas){ // para cuando se va al mazo el jugador
        this.rondasGanadasJ1 = rondas;
    }

    public void setRondasGanadasJ2(int rondas){ // para cuando se va al mazo el jugador
        this.rondasGanadasJ2 = rondas;
    }

    public int getRondasGanadasJ1() {
        return rondasGanadasJ1;
    }

    public int getRondasGanadasJ2() {
        return rondasGanadasJ2;
    }

    public Carta getUltimaCartaTiradaJ1() {
        return ultimaCartaTiradaJ1;
    }

    public Carta getUltimaCartaTiradaJ2() {
        return ultimaCartaTiradaJ2;
    }

    public int getTurno(){
        return turno;
    }

    public int getNroMano(){
        return nroMano;
    }

    public void setUltimaCartaTiradaJ1(Carta carta){
        ultimaCartaTiradaJ1 = carta;
    }
    public void setUltimaCartaTiradaJ2(Carta carta){
        ultimaCartaTiradaJ2 = carta;
    }

    public int getNroRonda(){
        return nroRonda;
    }

    //
    // metodos privados
    //

    private int compararCartas(Carta cj1, Carta cj2) throws RemoteException {
        if(cj1.getPoderCarta() > cj2.getPoderCarta()) return idJugador1;
        else if(cj1.getPoderCarta() < cj2.getPoderCarta()) return idJugador2;

        return -1; // caso de que sean el mismo poder es PARDA
    }

}
