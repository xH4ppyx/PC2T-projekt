package firma;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class DataAnalytic extends Employee {

    public DataAnalytic(int id, String name, String surname, int birthyear) {
        super(id, name, surname, birthyear);
    }

    @Override
    public void startSkill() {
        Map<Integer, CoopQuality> myCoworkers = this.getCoworkers();

        if (myCoworkers.isEmpty()) {
            System.out.println("Nemám žádné spolupracovníky pro analýzu.");
            return;
        }

        int maxCommon = -1;
        Employee bestMatch = null;
        
        for (Integer coworkerId : myCoworkers.keySet()) {
            Employee coworker = CompanyManager.getAllEmployees().get(coworkerId);
            
            if (coworker == null) {
                continue;
            }
            

            Set<Integer> commonCoworkers = new HashSet<>(myCoworkers.keySet());

            commonCoworkers.retainAll(coworker.getCoworkers().keySet());

            commonCoworkers.remove(this.getId());

            int commonCount = commonCoworkers.size();
            
            if (commonCount > maxCommon) {
                maxCommon = commonCount;
                bestMatch = coworker;
            }
        }
        
        if (bestMatch != null) {
            System.out.println("Spolupracovník s nejvíce společnými vazbami je: " 
                + bestMatch.getName() + " " + bestMatch.getSurname() 
                + " (ID: " + bestMatch.getId() + ") s celkem " + maxCommon + " společnými spolupracovníky.");
        } else {
            System.out.println("Nepodařilo se najít žádného spolupracovníka ke srovnání.");
        }
    }
}
