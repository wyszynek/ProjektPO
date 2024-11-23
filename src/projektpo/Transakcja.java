package projektpo;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Transakcja implements Serializable{

    private static final long serialVersionUID = -8984419798375734112L;
    List<String> listaIdTransakcji = new ArrayList<>();
    private String nazwaFirmy;
    private String KRS;
    private String RodzajUslugi;
    private String stanTranskacji;
    private String numerKarty;
    private String CVV;
    private double kwota;
    private String idTransakcji;
    private String DataTransakcji;

    public Transakcja(Firma f, boolean stanTranskacji, String numerKarty, String CVV, double kwota) {
        this.nazwaFirmy = f.getNazwaFirmy();
        this.KRS = f.getKRS();
        this.RodzajUslugi = f.getRodzajUslug();
        if (stanTranskacji == true) {
            this.stanTranskacji = "Udana";
        } else {
            this.stanTranskacji = "Nieudana";
        }
        this.numerKarty = numerKarty;
        this.CVV = CVV;
        this.kwota = kwota;
        this.idTransakcji = generujNumerTransakcji();
        this.DataTransakcji = generujDateTransakcji();
    }

    public String generujNumerTransakcji() {
        Random random = new Random();
        String id;
        File plikTransakcje = new File("IdTransakcji.txt");

        try {
            Scanner scanner = new Scanner(plikTransakcje);
            while (scanner.hasNextLine()) {
                String idTransakcji = scanner.nextLine();
                listaIdTransakcji.add(idTransakcji);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Plik nie istnieje");
        }
        do {
            id = String.format("%08d", random.nextInt(100000000));
        } while (listaIdTransakcji.contains(id));
        listaIdTransakcji.add(id);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("IdTransakcji.txt", true));
            writer.write(id);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.err.println("ERROR");
        }

        return id;
    }

    public String getIdTransakcji() {
        return idTransakcji;
    }

    public String getNazwaFirmy() {
        return nazwaFirmy;
    }

    public String getKRS() {
        return KRS;
    }

    public String getRodzajUslugi() {
        return RodzajUslugi;
    }

    public String getStanTranskacji() {
        return stanTranskacji;
    }

    public String getNumerKarty() {
        return numerKarty;
    }

    public String getCVV() {
        return CVV;
    }

    public double getKwota() {
        return kwota;
    }
    
    private String generujDateTransakcji() {
        LocalDate obecnyDzien = LocalDate.now();
        return obecnyDzien.format(DateTimeFormatter.ofPattern("dd/MM/yy"));
    }
    
    @Override
    public String toString() {
        return this.nazwaFirmy + " " + this.KRS + " " + this.RodzajUslugi + " " + this.stanTranskacji + " " + this.numerKarty + " " + this.CVV + " " + String.valueOf(this.kwota) + " " + this.idTransakcji + " " + this.DataTransakcji;
    }
}
