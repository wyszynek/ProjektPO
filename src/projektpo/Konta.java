package projektpo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Konta implements Serializable {

    private static final long serialVersionUID = 9050610004011962894L;
    private String numerKonta;
    List<Karty> listaKart;

    public Konta() {
        this.numerKonta = generujNumerKonta();
        listaKart = new ArrayList<Karty>();
    }

    public void dodajKarte(Karty karta) {
        this.listaKart.add(karta);
    }

    public void usunKarte(String numerKarty) {
        for (Karty k : listaKart) {
            if (k.getNumerKarty().equals(numerKarty)) {
                listaKart.remove(k);
                break;
            }
        }
    }
    
    public Karty znajdzKartePoNumerze(String numerKarty) {
        for(Karty k : listaKart) {
            if(k.getNumerKarty().equals(numerKarty)){
                return k;
            }
        }
        return null;
    }

    public String getNumerKonta() {
        return numerKonta;
    }

    private String generujNumerKonta() {

        int number = (int) Math.floor(Math.random() * (999999 - 100000) + 100000);
        return String.format("%06d", number);
    }

    @Override
    public String toString() {
        String karty = " ";
        for (Karty k : listaKart) {
            karty += k.toString();
        }
        return "Numer konta: " + numerKonta + karty;
    }

}
