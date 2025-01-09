import cliente.ClienteTruco;
import servidor.ServidorTruco;
import vista.inicio;
import vista.vistaInicio;

import java.rmi.RemoteException;

public class Main {

    public static void main(String[] args) throws RemoteException {
       /*
        ArrayList<ArrayList<String>> listas = new ArrayList<>();

        ArrayList<String> listaQuiero = new ArrayList<>();
        ArrayList<String> listaNoQuiero = new ArrayList<>();
        ArrayList<String> listaTruco = new ArrayList<>();
        ArrayList<String> listaReTruco = new ArrayList<>();
        ArrayList<String> listaValeCuatro = new ArrayList<>();
        ArrayList<String> listaEnvido = new ArrayList<>();
        ArrayList<String> listaRealEnvido = new ArrayList<>();
        ArrayList<String> listaFaltalEnvido = new ArrayList<>();

        listaQuiero.add("A ver, QUIERO!");
        listaQuiero.add("¡QUIERO!");
        listaQuiero.add("Vos me estás mintiendo, QUIEROOO!");
        listaQuiero.add("QUIERO y hasta ahí nomás!");
        listaQuiero.add("A mi no me vas a correr, ¡QUIERO!");

        listaNoQuiero.add("¡NO QUIERO!");
        listaNoQuiero.add("NO QUIERO NI LOCO!");
        listaNoQuiero.add("NAA NO SE QUIERE");
        listaNoQuiero.add("me estás mintiendo, pero yo tampoco tengo, ¡NO QUIERO!");
        listaNoQuiero.add("Lleve lleve, NO SE QUIERE");

        listaTruco.add("¡TRUUUUCO!");
        listaTruco.add("¿Estás para un TRUCO?");
        listaTruco.add("¿Para el TRUCO tenés?");
        listaTruco.add("TRUCO");
        listaTruco.add("¿Estamos jugando al TRUCO, no?");

        listaReTruco.add("Si tenias para el truco tenés que tener para el RE TRUCO");
        listaReTruco.add("¡QUIERO RE TRUCO!");
        listaReTruco.add("RE TRUCO");
        listaReTruco.add("¿Estás para el RE TRUCO?");
        listaReTruco.add("¡Si te digo RE TRUCO que me decís?");

        listaValeCuatro.add("¡VALE CUATRO!");
        listaValeCuatro.add("¿Estás para el VALE CUATRO?");
        listaValeCuatro.add("VALE CUATRO Y NOS VAMOS");
        listaValeCuatro.add("¿Si te digo VALE CUATRO te vas?");
        listaValeCuatro.add("Me la juego, VALE CUATRO");

        listaEnvido.add("ENVIDOO");
        listaEnvido.add("¿ENVIDO?");
        listaEnvido.add("¿Tenés para el ENVIDO?");
        listaEnvido.add("¿ENVIDO no, no?");
        listaEnvido.add("Cuando vine de La Isla traiba un lazo retorcido; con él enlacé dos cartas y con dos le digo ENVIDO.");

        listaRealEnvido.add("REAL ENVIDO");
        listaRealEnvido.add("Tengo para el REAL ENVIDO");
        listaRealEnvido.add("¿REAL ENVIDO tenés?");
        listaRealEnvido.add("Con su boquita de grana y su pelo renegrido, no envidia a la mañana este hermoso REAL ENVIDO.");
        listaRealEnvido.add("Me cansé, REAL ENVIDO");

        listaFaltalEnvido.add("¡FALTA ENVIDO!");
        listaFaltalEnvido.add("LISTO, NOS VAMOS... FALTA ENVIDO");
        listaFaltalEnvido.add("NO ME ACHICO YO, ¡FALTA ENVIDO DIJE!");
        listaFaltalEnvido.add("Si te digo FALTA ENVIDO te me vas al mazo...");
        listaFaltalEnvido.add("Una vez una paloma ofreció darme su nido, y yo creyendo una broma no le eché la Falta envido.");

        listas.add(listaQuiero);
        listas.add(listaNoQuiero);
        listas.add(listaTruco);
        listas.add(listaReTruco);
        listas.add(listaValeCuatro);
        listas.add(listaEnvido);
        listas.add(listaRealEnvido);
        listas.add(listaFaltalEnvido);


        try {
            FileOutputStream fos = new FileOutputStream("Cantos.bin");
            var oos = new ObjectOutputStream(fos);
            oos.writeObject(listas);
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //vistaGrafica vista = new vistaGrafica();

        //ServidorTruco serv = new ServidorTruco();
        //


        Mazo mazo = new Mazo();

        Jugador jugador = new Jugador("Lito");
        Jugador jugador2 = new Jugador("Liti");

        mazo.repartirCartas(jugador, jugador2);

        System.out.println(jugador.getCartasObtenidas().get(0).toString()+ " " + jugador.getCartasObtenidas().get(1).toString() + " " +jugador.getCartasObtenidas().get(2).toString());
        System.out.println(jugador.getNombre() + " puntos de envido: " + jugador.puntosEnvido());
        System.out.println(jugador2.getCartasObtenidas().get(0).toString()+ " " + jugador2.getCartasObtenidas().get(1).toString()+ " " +jugador2.getCartasObtenidas().get(2).toString());
        System.out.println(jugador2.getNombre() + " puntos de envido: " + jugador2.puntosEnvido());
    }*/
        //PersistenciaJugador.delvolverTodosJugadores();


        new inicio();
    }


}