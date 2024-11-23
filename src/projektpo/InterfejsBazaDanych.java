package projektpo;

abstract class InterfejsBazaDanych {

    abstract void dodajKlienta(Klient klient);
    
    abstract boolean czyKlientIstnieje(String PESEL);
  
    abstract void dodajKarte(Karty karta);
  
}
