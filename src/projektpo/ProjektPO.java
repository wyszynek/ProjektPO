package projektpo;

import java.io.Serializable;


public class ProjektPO implements Serializable {

    public static void main(String[] args) throws Exception {
        CentrumObslugi centrum = new CentrumObslugi();
        centrum.odczyt();
        new GUI(centrum).setVisible(true);
        // interfejs
    }
}
