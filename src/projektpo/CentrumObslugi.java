package projektpo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CentrumObslugi implements Serializable {
    
    private static final long serialVersionUID = -5855614762471595528L;  
    public List<Firma> listaFirm = new ArrayList<Firma>();
    public List<Banki> listaBankow = new ArrayList<Banki>();
    public BazaDanychCentrum bazaDanychCentrum = new BazaDanychCentrum();

    public CentrumObslugi() {
    }

    public void AutoryzujTransakcje(Firma f, String numerKarty, String CVV, double kwota, String dataWygasniecia) {
        String CzytajID = numerKarty.substring(0, 3);
        for (Banki b : listaBankow) {
            if (b.getIDBanku().equals(CzytajID)) {
                boolean czy_udana = b.WeryfikujTransakcje(numerKarty, CVV, kwota, dataWygasniecia);
                Transakcja transakcja = new Transakcja(f, czy_udana, numerKarty, CVV, kwota);
                bazaDanychCentrum.dodajTransakcje(transakcja);
                
            }
        }
    }

    // Zmienione parametry metody
    public void dodajFirme(Firma f) {
        boolean poprawnyKRS = false;
        String KRS = f.getKRS();
        if (KRS.length() != 10) {
            System.out.println("Nieprawidłowy KRS firmy!");
        } else {
            poprawnyKRS = true;
        }
        if (poprawnyKRS) {
            if (czyFirma(KRS)) {
                System.out.println("Istnieje juz firma o takim KRS!");
            } else {
                listaFirm.add(f);
                bazaDanychCentrum.dodajFirme(f);
            }
        }
    }

    public Banki getBankPoNazwie(String nazwaBanku) {
        for (Banki b : listaBankow) {
            if (b.getNazwaBanku().equals(nazwaBanku)) {
                return b;
            }
        }
        return null;
    }
    
    public void wplacNaKarte(String numerKarty, double kwota) {
        String IDBanku = numerKarty.substring(0,3);
        String numerKonta = numerKarty.substring(9,15);
        Banki b = getBankPoID(IDBanku);
        b.wplac(numerKarty, numerKonta, kwota);
    }

    public Banki znajdzBankPoPeseluOrazIDKlienta(String PESEL, String IDKlienta) {
        for (Banki b : listaBankow) {
            for (Klient k : b.bazaDanych.listaKlientow) {
                if(k.getPESEL().equals(PESEL) && k.getID().equals(IDKlienta)) {
                    return b;
                }
            }
        }
        return null;
    }
    
    public Banki znajdzBankPoPeseluKlienta(String PESEL) {
        for (Banki b : listaBankow) {
            for (Klient k : b.bazaDanych.listaKlientow) {
                if(k.getPESEL().equals(PESEL)) {
                    return b;
                }
            }
        }
        return null;
    }
    
    public Banki getBankPoID(String IDBanku) {
        for (Banki b : listaBankow) {
            if (b.getIDBanku().equals(IDBanku)) {
                return b;
            }
        }
        return null;
    }
    
    public ArrayList<Konta> getListeWszystkichKont() {
        ArrayList<Konta> lista = new ArrayList<Konta>();
        for(Banki b : listaBankow) {
            for(Klient k : b.bazaDanych.listaKlientow) {
                lista.addAll(k.listaKont);
            }
        }
        return lista;
    }
    
    public Firma getFirmaPoNazwie(String nazwaFirmy) {
        for (Firma f : listaFirm) {
            if(f.getNazwaFirmy().equals(nazwaFirmy)) {
                return f;
            }
        }
        return null;
    }
    
    public void zapisywanie() throws Exception{
        bazaDanychCentrum.zapiszStan();
    }
    
     public void odczyt() throws Exception{
        bazaDanychCentrum.wczytajStan();
        listaFirm.addAll(bazaDanychCentrum.listaFirmBazy);
        listaBankow.addAll(bazaDanychCentrum.listaBankowBazy);
    }

    public boolean czyFirma(String KRS) {
        for (Firma f : listaFirm) {
            if (f.getKRS().equals(KRS)) {
                return true;
            }
        }
        return false;
    }

    public void dodajBank(Banki bank) {
        if (!czyIstniejeBank(bank)) {
            listaBankow.add(bank);
            bazaDanychCentrum.dodajBank(bank);
        } else {
            System.out.println("Istnieje juz taki bank w naszej bazie!");
        }
    }

    public void usunBank(String IDBanku) {
        for (Banki b : listaBankow) {
            if (b.getIDBanku().equals(IDBanku)) {
                listaBankow.remove(b);
                bazaDanychCentrum.usunBank(b);
                break;
            } else {
                System.out.println("Brak banku o takim ID!");
            }
        }
    }

    public boolean czyIstniejeBank(Banki b) {
        for (Banki bank : listaBankow) {
            if (bank.getNazwaBanku().equals(b.getNazwaBanku())) {
                return true;
            }
        }
        return false;
    }

    public void usunFirme(String KRS) {
        for (Firma firma : listaFirm) {
            if (firma.getKRS().equals(KRS)) {
                listaFirm.remove(firma);
                bazaDanychCentrum.usunFirme(firma);
                System.out.println("Usunięto firmę o numerze KRS: " + KRS);
                break;
            }
        }
    }
    
    public Firma getInfoFirmy(int i) {
        if(i >= listaFirm.size()) {
            return null;
        } else {
            Firma f = listaFirm.get(i);
            return f;
        }
    }
    
    public Banki getInfoBanku(int i) {
        if(i >= listaBankow.size()) {
            return null;
        } else {
            Banki b = listaBankow.get(i);
            return b;
        }
    }

    public void wyswietlFirmy() {
        System.out.println("Lista firm:");
        for (Firma firma : listaFirm) {
            System.out.println(firma.toString());
        }
    }

    public void wyswietlBanki() {
        System.out.println("Lista bankow:");
        for (Banki bank : listaBankow) {
            System.out.println(bank.toString());
        }
    }

}
