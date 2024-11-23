package projektpo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BazaDanych extends InterfejsBazaDanych implements Serializable {

    private static final long serialVersionUID = 1606938656558035623L;
    List<Klient> listaKlientow = new ArrayList<>();
     List<Karty> listaKart = new ArrayList<>();

    public BazaDanych() {
    }

    @Override
    public void dodajKlienta(Klient klient) {
        if (!czyKlientIstnieje(klient.getPESEL())) {
            listaKlientow.add(klient);
        } else {
            System.out.println("Istnieje juz klient o takim PESELu w naszym banku!");
        }
    }

    public void dodajKarte(Karty k) {
        listaKart.add(k);
    }

    public void podmienKlienta(Klient k) {
        for (Klient k1 : listaKlientow) {
            if (k1.getPESEL().equals(k.getPESEL())) {
                listaKlientow.set(listaKlientow.indexOf(k1), k);
            }
        }
    }
    
    public Klient getInfoKlienta(int i) {
        if(i >= listaKlientow.size()) {
            return null;
        } else {
            Klient k = listaKlientow.get(i);
            return k;
        }
    }
    
    public boolean czyKartaIstnieje(String numerKarty) {
        System.out.println(numerKarty);
        System.out.println(listaKart);
        for (Karty k : listaKart) {
            if (k.getNumerKarty().equals(numerKarty)) {
                
                return true;
            }
        }
        return false;
    }

    public boolean czyKontoIstnieje(String PESEL, String numerKonta) {
        if (czyKlientIstnieje(PESEL)) {
            Klient k = getKlient(PESEL);
            for (Konta konto : k.listaKont) {
                if (konto.getNumerKonta().equals(numerKonta)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean czyKlientIstnieje(String PESEL) {
        for (Klient k : listaKlientow) {
            if (k.getPESEL().equals(PESEL)) {
                return true;
            }
        }
        return false;
    }

    public Klient getKlient(String PESEL) {
        for (Klient k : listaKlientow) {
            if (k.getPESEL().equals(PESEL)) {
                return k;
            }
        }
        return null;
    }

    public Konta getKonto(String PESEL, String numerKonta) {
        Klient k = getKlient(PESEL);
        for (Konta konto : k.listaKont) {
            if (konto.getNumerKonta().equals(numerKonta)) {
                return konto;
            }
        }
        return null;
    }

    public Karty getKarta(String numerKarty) {
        return listaKart
                .stream()
                .filter((karta) -> karta.getNumerKarty().equals(numerKarty))
                .findFirst()
                .orElse(null);
    }


    public void usunKarte(Karty karta) {
        listaKart.remove(karta);
    }

    public void usunKlienta(Klient klient) {
        listaKlientow.remove(klient);
    }


}
