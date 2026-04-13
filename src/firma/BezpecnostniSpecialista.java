package firma;

import java.util.Map;

public class BezpecnostniSpecialista extends Zamestnanec {

    public BezpecnostniSpecialista(int id, String jmeno, String prijmeni, int rokNarozeni) {
        super(id, jmeno, prijmeni, rokNarozeni);
    }

    
    public void spustDovednost() {
        Map<Integer, UrovenSpoluprace> mojiSpolupracovnici = this.getSpolupracovnici();
        
        if (mojiSpolupracovnici.isEmpty()) {
            System.out.println("Nemám žádné spolupracovníky, rizikové skóre je 0.");
            return;
        }

        int celkoveSkore = 0;

        // Špatná = +10 rizikových bodů, Průměrná = +5 bodů, Dobrá = -2 body (snižuje riziko)
        for (UrovenSpoluprace uroven : mojiSpolupracovnici.values()) {
            switch (uroven) {
                case SPATNA:
                    celkoveSkore += 10;
                    break;
                case PRUMERNA:
                    celkoveSkore += 5;
                    break;
                case DOBRA:
                    celkoveSkore -= 2;
                    break;
            }
        }

        // Výpočet průměru a vynásobení počtem lidí (čím více lidí, tím větší šance úniku dat)
        double prumerneRiziko = (double) celkoveSkore / mojiSpolupracovnici.size();
        double vysledneRiziko = prumerneRiziko * mojiSpolupracovnici.size();

        System.out.println("Bezpečnostní prověrka dokončena.");
        System.out.println("Počet sledovaných vazeb: " + mojiSpolupracovnici.size());
        System.out.println("Vypočítané rizikové skóre: " + vysledneRiziko);
    }
}