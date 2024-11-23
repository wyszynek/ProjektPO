package projektpo;

import java.io.Serializable;


public class Firma implements Serializable{

    private static final long serialVersionUID = -7050987269853704815L;
    private String nazwaFirmy;
    private String KRS;
    private String RodzajUslug;
    

    public Firma(String nazwaFirmy, String RodzajUslug, String KRS, CentrumObslugi centrumObslugi) {
        this.nazwaFirmy = nazwaFirmy;
        this.RodzajUslug = RodzajUslug;
        this.KRS = KRS;
        
    }

    public void Transakcja(String numerKarty, String CVV, double kwota, String dataWygasniecia, CentrumObslugi centrum) {
        centrum.AutoryzujTransakcje(this, numerKarty, CVV, kwota, dataWygasniecia);
        System.out.println("firma dziala");
    }

    public String getNazwaFirmy() {
        return nazwaFirmy;
    }

    public String getKRS() {
        return KRS;
    }

    public String getRodzajUslug() {
        return RodzajUslug;
    }

    @Override
    public String toString() {
        return "Nazwa: " + this.nazwaFirmy + "\n" + "Rodzaj usługi: " + this.RodzajUslug + "\n" + "KRS: " + KRS + "\n"
                + "----------------------------" + "\n";
    }

}
