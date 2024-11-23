package projektpo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Klient implements Serializable {

    private static final long serialVersionUID = 1801515047947680994L;
    public List<Konta> listaKont;
    private String imie;
    private String nazwisko;
    private String PESEL;
    private String ID;
    private String IDBankuKlienta;  

    public Klient(String imie, String nazwisko, String PESEL, String IDBankuKlienta) {
        if (PESEL.length() != 11) {
            System.out.println("Niepoprawny PESEL!");
        } else {
            this.imie = imie;
            this.nazwisko = nazwisko;
            this.PESEL = PESEL;
            Random random = new Random();
            this.ID = String.valueOf(random.nextInt(999999 - 100000) + 100000);
        }
        this.IDBankuKlienta = IDBankuKlienta;
        this.listaKont = new ArrayList<>();
    }

    public void dodajKonto(Konta k) {
        listaKont.add(k);
    }

    public void usunKonto(String numerKonta) {
        for (Konta konto : listaKont) {
            if (konto.getNumerKonta().equals(numerKonta)) {
                listaKont.remove(konto);
                break;
            }
        }
    }

    public String getIDBankuKlienta() {
        return this.IDBankuKlienta;
    }
    
    public String getPESEL() {
        return this.PESEL;
    }

    public String getImie() {
        return this.imie;
    }

    public String getNazwisko() {
        return this.nazwisko;
    }

    public String getID() {
        return this.ID;
    }
    
    @Override
    public String toString() {
        String konta = new String();
        for(Konta k : listaKont) {
            konta += k.toString();
        }
        return this.imie + " " + this.nazwisko + " ID Klienta: " + this.ID + " PESEL: " + this.PESEL + " " + konta;
    }
}
