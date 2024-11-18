package persistencia;

import modelo.Mazo;

import java.io.*;

public class PersistenciaMazo implements Serializable {
    private static Mazo mazo;

    public PersistenciaMazo() {
    }

    // metodos statics publicos

    public static Mazo recuperarMazo(){
        try {
            FileInputStream fos = new FileInputStream("mazoDeCartas.bin");
            var oos = new ObjectInputStream(fos);
            mazo = (Mazo) oos.readObject();
            fos.close();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return mazo;
    }
}
