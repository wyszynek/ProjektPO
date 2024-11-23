
package projektpo;

abstract class InterfejsBazaDanychCentrum {
    
    abstract void dodajFirme(Firma f);
    
    abstract void dodajBank(Banki b);

    abstract void dodajTransakcje(Transakcja t);

    abstract void usunFirme(Firma f);
    
    abstract void usunBank(Banki b);
    
    abstract void zapiszStan() throws Exception;
    
    abstract void wczytajStan() throws Exception;
}
