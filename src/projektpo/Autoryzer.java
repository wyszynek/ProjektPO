package projektpo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Autoryzer {

    public static boolean Autoryzuj(Karty k, String CVV, double kwota, String dataWygasniecia) {
        String typKarty = k.getNumerKarty().substring(15);
        int typK = Integer.parseInt(typKarty);
        double saldoPoTransakcji;
        String data1 = k.getDataWygasniecia();
        LocalDate obecnaData = LocalDate.now();
        DateTimeFormatter formatowanaData = DateTimeFormatter.ofPattern("MM/yy");
        String data2 = obecnaData.format(formatowanaData);
        int zestawienieDat = data1.compareTo(data2);
        if (k.getCVV().equals(CVV) && zestawienieDat > 0) {
            switch (typK) {
                // Karta debetowa
                case 0:
                    saldoPoTransakcji = k.getSaldo() - kwota;
                    k.setSaldoKarty(saldoPoTransakcji);
                    // Zapis do bazy danych salda ppo operacji
                    return true;
                case 1:
                    // Karta bankowa
                    if (k.getSaldo() >= kwota) {
                        saldoPoTransakcji = k.getSaldo() - kwota;
                        k.setSaldoKarty(saldoPoTransakcji);
                        // Zapis do bazy danych salda ppo operacji
                        return true;
                    } else {
                        System.out.println("Nie masz wystarczajacej ilosci srodkow na koncie");
                    }
                    return false;
                case 2:
                    // Karta kredytowa
                    if (k.getSaldo()-kwota >= -4000){
                    saldoPoTransakcji = k.getSaldo() - kwota;
                    k.setSaldoKarty(saldoPoTransakcji);
                    return true;
                    }else{
                        System.out.println("Nie masz wystarczajacej ilosci srodkow na koncie");
                    }
                   
                    // Zapis do bazy danych salda ppo operacji
                    

            }
        }
        return false;
    }

}
