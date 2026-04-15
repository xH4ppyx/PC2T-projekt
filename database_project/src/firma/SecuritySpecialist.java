package firma;

import java.util.Map;

public class SecuritySpecialist extends Employee {

    public SecuritySpecialist(int id, String name, String surname, int birthyear) {
        super(id, name, surname, birthyear);
    }

    
    public void startSkill() {
        Map<Integer, CoopQuality> myCoworkers = this.getCoworkers();
        
        if (myCoworkers.isEmpty()) {
            System.out.println("Nemám žádné spolupracovníky, rizikové skóre je 0.");
            return;
        }

        int totalScore = 0;

        // Špatná = +10 rizikových bodů, Průměrná = +5 bodů, Dobrá = -2 body (snižuje riziko)
        for (CoopQuality level : myCoworkers.values()) {
            switch (level) {
                case SPATNA:
                    totalScore += 10;
                    break;
                case PRUMERNA:
                    totalScore += 5;
                    break;
                case DOBRA:
                    totalScore -= 2;
                    break;
            }
        }

        // Výpočet průměru a vynásobení počtem lidí (čím více lidí, tím větší šance úniku dat)
        double averageRisk = (double) totalScore / myCoworkers.size();
        double resultRisk = averageRisk * myCoworkers.size();

        System.out.println("Bezpečnostní prověrka dokončena.");
        System.out.println("Počet sledovaných vazeb: " + myCoworkers.size());
        System.out.println("Vypočítané rizikové skóre: " + resultRisk);
    }
}