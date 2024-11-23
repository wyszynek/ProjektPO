package projektpo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Banki implements Serializable {
    private List<String> listaIdBankow = new ArrayList<>();
    private static final long serialVersionUID = -835990571009573058L;
    private String nazwaBanku;
    private String IDBanku;
    public BazaDanych bazaDanych;
    public CentrumObslugi centrum;
    // ustawic private

    public Banki(String nazwaBanku, CentrumObslugi centrum) {
        bazaDanych = new BazaDanych();
        Random random = new Random();
        this.IDBanku = generujNumerBanku();
        this.nazwaBanku = nazwaBanku;
        this.centrum = centrum;
        centrum.dodajBank(this);
    }

    public void stworzKlienta(String imie, String nazwisko, String PESEL, String IDBanku) {
        Klient klient = Kreator.kreatorKlienta(imie, nazwisko, PESEL, IDBanku);
        this.bazaDanych.dodajKlienta(klient);
    }

    public void stworzKonto(String PESEL) {
        if (this.bazaDanych.czyKlientIstnieje(PESEL)) {
            Klient k = this.bazaDanych.getKlient(PESEL);
            Klient k1 = Kreator.kreatorKonta(k);
            //this.bazaDanych.podmienKlienta(k1);
        } else {
            System.out.println("Brak klienta o takim PESELu w naszym banku!");
        }
    }
    
    public void stworzKarte(String PESEL, String NumerKonta, int typKarty) {
        if (this.bazaDanych.czyKlientIstnieje(PESEL)) {
            Klient k = this.bazaDanych.getKlient(PESEL);
            for (Konta konto : k.listaKont) {
                if (konto.getNumerKonta().equals(NumerKonta)) {
                    Karty karta = Kreator.kreatorKarty(k, konto, this.getIDBanku(), typKarty);
                    this.bazaDanych.dodajKarte(karta);
                } else {
                    System.out.println("Ten klient nie ma konta o takim numerze!");
                }
            }
        } else {
            System.out.println("Nie istnieje taki klient!");
        }
    }
    
    public String getIDBanku() {
        return this.IDBanku;
    }

    public String getNazwaBanku() {
        return this.nazwaBanku;
    }
    public String generujNumerBanku() {
        Random random = new Random();
        String idBanku;
        File plikTransakcje = new File("IdBankow.txt");

        try {
            Scanner scanner = new Scanner(plikTransakcje);
            while (scanner.hasNextLine()) {
                String idTransakcji = scanner.nextLine();
                listaIdBankow.add(idTransakcji);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Plik nie istnieje");
        }
        do {
            idBanku = String.format("%03d", random.nextInt(1000));
        } while (listaIdBankow.contains(idBanku));
        listaIdBankow.add(idBanku);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("IdBankow.txt", true));
            writer.write(idBanku);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.err.println("ERROR");
        }

        return idBanku;
    }
    public boolean WeryfikujTransakcje(String numerKarty, String CVV, double kwota, String dataWygasniecia) {
      
        if (this.bazaDanych.czyKartaIstnieje(numerKarty)) {
            Karty k = bazaDanych.getKarta(numerKarty);
            
            return Autoryzer.Autoryzuj(k, CVV, kwota, dataWygasniecia);
        }
        return false;
    }
    
    public void wplac(String numerKarty, String numerKonta, double kwota) {
        for(Klient k : bazaDanych.listaKlientow) {
           for(Konta konto : k.listaKont) {
               if(konto.getNumerKonta().equals(numerKonta)) {
                   Karty karta = konto.znajdzKartePoNumerze(numerKarty);
                   karta.wplacNaKarte(kwota);
               }
           } 
        }
    }

    public void usunKarte(String PESEL, String numerKonta, String numerKarty) {

        if (bazaDanych.czyKartaIstnieje(numerKarty) && bazaDanych.czyKontoIstnieje(PESEL, numerKonta)) {
            Konta konto = bazaDanych.getKonto(PESEL, numerKonta);
            konto.usunKarte(numerKarty);
            Karty karta = bazaDanych.getKarta(numerKarty);
            bazaDanych.listaKart.remove(karta);
        }
    }

    public void usunKonto(String PESEL, String numerKonta) {
        if (bazaDanych.czyKontoIstnieje(PESEL, numerKonta)) {
            Klient klient = bazaDanych.getKlient(PESEL);
            klient.usunKonto(numerKonta);
        }
    }

    public void usunKlienta(String PESEL) {
        if (bazaDanych.czyKlientIstnieje(PESEL)) {
            Klient klient = bazaDanych.getKlient(PESEL);
            for (Konta konto : klient.listaKont) {
                for (Karty karta : konto.listaKart) {
                    bazaDanych.usunKarte(karta);
                }
            }
            bazaDanych.usunKlienta(klient);
        }
    }

    @Override
    public String toString() {
        return nazwaBanku + " " + IDBanku;
    }

}
