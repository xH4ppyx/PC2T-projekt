package firma;

import java.util.Map;
import java.util.Set;

public class DataAnalytic extends Employee {

    public DataAnalytic(int id, String name, String surname, int birthyear) {
        super(id, name, surname, birthyear);
    }

    @Override
    public void startSkill() {
        System.out.println("Zatím nemám žádné kolegy pro analýzu.");
    }
}