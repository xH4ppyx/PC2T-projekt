package firma;

import java.util.Map;
import java.util.Set;

public class DataAnalytic extends Employee {

    public DataAnalytic(int id, String name, String surname, int birthyear) {
        super(id, name, surname, birthyear);
    }

    @Override
    public void startSkill() {
    	Map<Integer, CoopQuality> myCoworkers = this.getCoworkers();
        
        if (myCoworkers.isEmpty()) {
            System.out.println("Nemám žádné spolupracovníky, rizikové skóre je 0.");
            return;
        }
    }
}