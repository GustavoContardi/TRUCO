import modelo.Mazo;
import org.w3c.dom.ls.LSOutput;
import persistencia.PersistenciaJugador;
import vista.VistaInicio;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;

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
        ArrayList<String> listaFlor = new ArrayList<>();
        ArrayList<String> listaContraFlor = new ArrayList<>();
        ArrayList<String> listaContraFlorAlResto = new ArrayList<>();

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

        listaFlor.add("¡Flor!");
        listaFlor.add("¡Tengo la Flor más linda del jardín!");
        listaFlor.add("¡Flor de las flores, que te caes de la silla!!");
        listaFlor.add("¿Te canto una canción? ¡No! ¡Te canto la Flor!");
        listaFlor.add("¡Tengo la Flor! ¡Y no es de Arjona, eh!");

        listaContraFlor.add("¡Contra Flor!");
        listaContraFlor.add("Mi Flor es mas linda que la tuya, ¡CONTRA FLOR!");
        listaContraFlor.add("No te asustes, pero ¡CONTRA FLOR!");
        listaContraFlor.add("¿Te canto una canción? ¡No! ¡Te canto la Contra Flor!");
        listaContraFlor.add("6 puntos fáciles... Contra Flor");

        listaContraFlorAlResto.add("¡Contra Flor al Resto!");
        listaContraFlorAlResto.add("Mi Flor es mas linda que la tuya, ¡CONTRA FLOR AL RESTO!");
        listaContraFlorAlResto.add("Necesito los puntos, CONTRA FLOR AL RESTO");
        listaContraFlorAlResto.add("¿Te canto una canción de Arjona? ¡No! ¡Te canto Contra Flor al Resto!");
        listaContraFlorAlResto.add("Y bueno... ¡Contra Flor al Resto y cada uno pa' su casa!");

        listas.add(listaQuiero);
        listas.add(listaNoQuiero);
        listas.add(listaTruco);
        listas.add(listaReTruco);
        listas.add(listaValeCuatro);
        listas.add(listaEnvido);
        listas.add(listaRealEnvido);
        listas.add(listaFaltalEnvido);
        listas.add(listaFlor);
        listas.add(listaContraFlor);
        listas.add(listaContraFlorAlResto);

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

*/
        //vistaGrafica vista = new vistaGrafica();

        //ServidorTruco serv = new ServidorTruco();
        //

/*
        Mazo mazo = new Mazo();

        Jugador jugador = new Jugador("Lito");
        Jugador jugador2 = new Jugador("Liti");

        mazo.repartirCartas(jugador, jugador2);

        System.out.println(jugador.getCartasObtenidas().get(0).toString()+ " " + jugador.getCartasObtenidas().get(1).toString() + " " +jugador.getCartasObtenidas().get(2).toString());
        System.out.println(jugador.getNombre() + " puntos de envido: " + jugador.puntosEnvido());
        System.out.println(jugador2.getCartasObtenidas().get(0).toString()+ " " + jugador2.getCartasObtenidas().get(1).toString()+ " " +jugador2.getCartasObtenidas().get(2).toString());
        System.out.println(jugador2.getNombre() + " puntos de envido: " + jugador2.puntosEnvido());*/

        //PersistenciaJugador.delvolverTodosJugadores();
        VistaInicio inicio = new VistaInicio();
        VistaInicio inicio2 = new VistaInicio();
        inicio.iniciar();
        inicio2.iniciar();

        JOptionPane.showMessageDialog(null, "AVISO PARROQUIAL");

    }


}