package modelo;

import java.io.Serializable;

public class Carta implements Serializable {

    // atributos
    private int         numeroCarta ;
    private String      paloCarta   ;
    private int         poderCarta  ;
    private boolean     fueTirada   ;
    private int         idCarta     ;
    // el id de la carta van a ser del 1 al 40, 1-10=espada, 11-20=basto, 21-30=copa, 31-40=oro
    //
    // constructor
    public Carta(int numeroCarta, String paloCarta, int poderCarta, int idCarta) {
        this.numeroCarta  =  numeroCarta    ;
        this.paloCarta    =  paloCarta      ;
        this.poderCarta   =  poderCarta     ;
        this.fueTirada    =  false          ;
        this.idCarta      =  idCarta        ;
    }

    // metodos
    @Override
    public String toString(){
        return numeroCarta + " de " + paloCarta;
    }

    public String fotoCarta(){
        return numeroCarta + "de" + paloCarta.toLowerCase();
    }



    // GETTERS Y SETTERS
    public int getNumeroCarta() {
        return numeroCarta;
    }

    public void setNumeroCarta(int numeroCarta) {
        this.numeroCarta = numeroCarta;
    }

    public String getPaloCarta() {
        return paloCarta;
    }

    public void setPaloCarta(String paloCarta) {
        this.paloCarta = paloCarta;
    }

    public int getPoderCarta() {
        return poderCarta;
    }

    public void setPoderCarta(int poderCarta) {
        this.poderCarta = poderCarta;
    }

    public boolean isFueTirada() {
        return fueTirada;
    }

    public void seTiroCarta(){
        fueTirada = true;
    }

    public int getIdCarta() {
        return idCarta;
    }

    public void setIdCarta(int idCarta) {
        this.idCarta = idCarta;
    }

    public void setFueTirada(boolean fueTirada) {
        this.fueTirada = fueTirada;
    }
}
