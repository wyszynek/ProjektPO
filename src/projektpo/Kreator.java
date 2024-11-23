package projektpo;

public class Kreator {

    public static Klient kreatorKlienta(String imie, String nazwisko, String PESEL, String IDBankuKlienta) {
        return new Klient(imie, nazwisko, PESEL, IDBankuKlienta);
    }

    public static Klient kreatorKonta(Klient k) {
        k.dodajKonto(new Konta());
        return k;
    }

    public static Karty kreatorKarty(Klient klient, Konta konto, String IDBanku, int typKarty) {
        Karty k = new Karty(klient, konto, IDBanku, typKarty);
        konto.dodajKarte(k);
        return k;
    }
}
