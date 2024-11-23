package projektpo;

import java.io.Serializable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Karty implements Serializable {

    private static final long serialVersionUID = -7189485186183783207L;
    private String numerKarty;
    private String CVV;
    private double saldoKarty;
    private int typKarty;
    private String dataWygasniecia;

    // ogarnac przypisywanie daty biezacej i typ karty
    public Karty(Klient klient, Konta konto, String IDBanku, int typKarty) {
        Random random = new Random();
        this.saldoKarty = 0;
        this.CVV = String.valueOf(random.nextInt(999 - 100) + 100);
        this.numerKarty = IDBanku + klient.getID() + konto.getNumerKonta() + typKarty;
        this.typKarty = typKarty;
        this.dataWygasniecia = generujDateWygasniecia();
    }

    private String generujDateWygasniecia() {
        LocalDate obecnyDzien = LocalDate.now();
        LocalDate dzienWygasniecia = obecnyDzien.plusYears(3);
        return dzienWygasniecia.format(DateTimeFormatter.ofPattern("MM/yy"));
    }
    
    public void wplacNaKarte(double kwota) {
        this.saldoKarty += kwota;
    }

    public String getNumerKarty() {
        return this.numerKarty;
    }

    public String getCVV() {
        return this.CVV;
    }

    public double getSaldo() {
        return this.saldoKarty;
    }

    public int getTypKarty() {
        return this.typKarty;
    }

    public void setSaldoKarty(double saldoKarty) {
        this.saldoKarty = saldoKarty;
    }

    public String getDataWygasniecia() {
        return this.dataWygasniecia;
    }

    @Override
    public String toString() {
        return "Numer karty: " + this.numerKarty + " CVV: " + this.CVV + " Saldo: " + this.saldoKarty + " Data wygasniecia: " + this.dataWygasniecia;
    }
}
