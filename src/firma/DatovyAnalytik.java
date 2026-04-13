package firma;

import java.util.Map;
import java.util.Set;

public class DatovyAnalytik extends Zamestnanec {

    public DatovyAnalytik(int id, String jmeno, String prijmeni, int rokNarozeni) {
        super(id, jmeno, prijmeni, rokNarozeni);
    }

    @Override
    public void spustDovednost() {
        System.out.println("Zatím nemám žádné kolegy pro analýzu.");
    }
}