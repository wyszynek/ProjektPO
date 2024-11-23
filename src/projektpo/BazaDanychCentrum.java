package projektpo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BazaDanychCentrum extends InterfejsBazaDanychCentrum implements Serializable{

    private static final long serialVersionUID = 7135155318655342507L;
    public List<Firma> listaFirmBazy = new ArrayList<Firma>();
    public List<Banki> listaBankowBazy = new ArrayList<Banki>();
    public List<Transakcja> listaTransakcjiBazy = new ArrayList<Transakcja>();

    @Override
    public void dodajFirme(Firma f) {
        listaFirmBazy.add(f);
    }

    @Override
    public void dodajBank(Banki b) {
        listaBankowBazy.add(b);
    }

    @Override
    public void dodajTransakcje(Transakcja t) {
        listaTransakcjiBazy.add(t);
    }

    @Override
    public void usunFirme(Firma f) {
        listaFirmBazy.remove(f);
    }

    @Override
    public void usunBank(Banki b) {
        listaBankowBazy.remove(b);
    }

    // TO TUTAJ WINOWAJCA
    public Transakcja getInfoTransakcji(int i) {
        if(i >= listaTransakcjiBazy.size()) {
            return null;
        } else {
            Transakcja t = listaTransakcjiBazy.get(i);
            return t;
        }
    }

    @Override
    public void zapiszStan() throws Exception {
        File plikFirmy = new File("bazaDanychFirmy.txt");
        File plikBanki = new File("bazaDanychBanki.txt");
        File plikTransakcje = new File("bazaDanychTranskacje.txt");
        ObjectOutputStream zapisFirm = new ObjectOutputStream(new FileOutputStream(plikFirmy));
        ObjectOutputStream zapisBankow = new ObjectOutputStream(new FileOutputStream(plikBanki));
        ObjectOutputStream zapisTransakcji = new ObjectOutputStream(new FileOutputStream(plikTransakcje));
        for (Firma firma : listaFirmBazy) {
            zapisFirm.writeObject(firma);
        }
        for (Banki bank : listaBankowBazy) {
            zapisBankow.writeObject(bank);
        }

        for (Transakcja transakcja : listaTransakcjiBazy) {
            zapisTransakcji.writeObject(transakcja);
        }
        zapisFirm.close();
        zapisBankow.close();
        zapisTransakcji.close();
    }

    @Override
    public void wczytajStan() throws Exception {
        File plikFirmy = new File("bazaDanychFirmy.txt");
        File plikBanki = new File("bazaDanychBanki.txt");
        File plikTransakcje = new File("bazaDanychTranskacje.txt");
        ObjectInputStream odczytFirm = new ObjectInputStream(new FileInputStream(plikFirmy));
        ObjectInputStream odczytBankow = new ObjectInputStream(new FileInputStream(plikBanki));
        ObjectInputStream odczytTransakcji = new ObjectInputStream(new FileInputStream(plikTransakcje));
        while (true) {
            try {
                Object obj = odczytFirm.readObject();
                if (!czyFirmaObiekt(listaFirmBazy, (Firma) obj)) {
                    listaFirmBazy.add((Firma) obj);
                }
            } catch (EOFException e) {
                break;
            }
        }
        while (true) {
            try {
                Object obj = odczytBankow.readObject();
                if (!czyBankiObiekt(listaBankowBazy, (Banki) obj)) {
                    listaBankowBazy.add((Banki) obj);
                }
            } catch (EOFException e) {
                break;
            }
        }
        while (true) {
            try {
                Object obj = odczytTransakcji.readObject();
                if (!czyTransakcjaObiekt(listaTransakcjiBazy, (Transakcja) obj)) {
                    listaTransakcjiBazy.add((Transakcja) obj);
                }
            } catch (EOFException e) {
                break;
            }
        }

        odczytFirm.close();
        odczytBankow.close();
        odczytTransakcji.close();
    }

    public boolean czyFirmaObiekt(List<Firma> listaFirmBazy, Firma obj) {
        for (Firma firma : listaFirmBazy) {
            if (firma.getKRS().equals(obj.getKRS())) {
                return true;
            }
        }
        return false;
    }

    public boolean czyBankiObiekt(List<Banki> listaBankowBazy, Banki obj) {
        for (Banki banki : listaBankowBazy) {
            if (banki.getIDBanku().equals(obj.getIDBanku())) {
                return true;
            }
        }
        return false;
    }

    //Dodac IDTransakcji !!!
    public boolean czyTransakcjaObiekt(List<Transakcja> listaTransakcjiBazy, Transakcja obj) {
        for (Transakcja transakcja : listaTransakcjiBazy) {
            if (transakcja.equals(obj.getNumerKarty()) && transakcja.getKwota() == obj.getKwota() && transakcja.getIdTransakcji().equals(obj.getIdTransakcji())) {
                return true;
            }
        }
        
        return false;
    }

    public void wyswietlTransakcje() {
        for (Transakcja t : listaTransakcjiBazy) {
            System.out.println(t.toString());
        }
    }

    public void wyswietlFirmy() {
        System.out.println("Lista firm:");
        for (Firma firma : listaFirmBazy) {
            System.out.println(firma.toString());
        }
    }

    public void wyswietlBanki() {
        System.out.println("Lista bankow:");
        for (Banki bank : listaBankowBazy) {
            System.out.println(bank.toString());
        }
    }
}
