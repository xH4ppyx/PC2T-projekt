package firma;

import java.util.HashMap;
import java.util.Map;
import java.util.List;



public class CompanyManager {
    
    public static Map<Integer, Employee> allEmployee;
    

    private int freeID;

    public CompanyManager() {
        this.allEmployee = new HashMap<>();
        this.freeID = 1;
    }
 
    


    public void addEmployee(Employee newEmployee) {
        newEmployee.setId(freeID);
        allEmployee.put(freeID, newEmployee);
        System.out.println("Byl přidán zaměstnanec: " + newEmployee.getName() + " " + newEmployee.getSurname() + " s ID: " + freeID);
        freeID++;
    }
    
    public void setEmployees(List<Employee> employees) {
        allEmployee.clear();

        for (Employee e : employees) {
            allEmployee.put(e.getId(), e);
        }


        freeID = allEmployee.keySet().stream().max(Integer::compare).orElse(0) + 1;
    }


    public void addCoop(int idEmployee, int idCoop, CoopQuality quality) {
        Employee z = allEmployee.get(idEmployee);
        Employee k = allEmployee.get(idCoop);

        if (z != null && k != null) {
            z.getCoworkers().put(idCoop, quality);
            System.out.println("Spolupráce byla úspěšně přidána.");
        } else {
            System.out.println("Chyba: Jedno nebo obě ID neexistují.");
        }
    }
    

    public void removeEmployee(int idToRemove) {

        if (allEmployee.containsKey(idToRemove)) {

            allEmployee.remove(idToRemove);

            for (Employee z : allEmployee.values()) {

                z.getCoworkers().remove(idToRemove);
            }
            
            System.out.println("Zaměstnanec s ID " + idToRemove + " byl úspěšně smazán včetně všech vazeb.");
        } else {
            System.out.println("Chyba: Zaměstnanec s ID " + idToRemove + " nebyl nalezen.");
        }
    }
    

    public void findEmployee(int id) {
        Employee z = allEmployee.get(id);
        if (z != null) {
            System.out.println("Nalezen zaměstnanec: " + z.getName() + " " + z.getSurname() + " (ID: " + z.getId() + ")");
            System.out.println("Rok narození: " + z.getBirthyear());
            System.out.println("Počet spolupracovníků: " + z.getCoworkers().size());
        } else {
            System.out.println("Zaměstnanec s tímto ID neexistuje.");
        }
    }


    public void showAllEmployee() {
        if (allEmployee.isEmpty()) {
            System.out.println("Firma aktuálně nemá žádné zaměstnance.");
            return;
        }
        System.out.println("--- Seznam zaměstnanců ---");
        for (Employee z : allEmployee.values()) {
            System.out.println("ID: " + z.getId() + " | " + z.getName() + " " + z.getSurname());
        }
        System.out.println("--------------------------");
    }  
    

    public void showABCGroup() {
        java.util.List<Employee> analysts = new java.util.ArrayList<>();
        java.util.List<Employee> specialists = new java.util.ArrayList<>();

        for (Employee z : allEmployee.values()) {
            if (z instanceof DataAnalytic) analysts.add(z);
            else if (z instanceof SecuritySpecialist) specialists.add(z);
        }

        analysts.sort(java.util.Comparator.comparing(Employee::getSurname));
        specialists.sort(java.util.Comparator.comparing(Employee::getSurname));

        System.out.println("\n--- Datoví analytici ---");
        for (Employee a : analysts) System.out.println(a.getSurname() + " " + a.getName() + " (ID: " + a.getId() + ")");

        System.out.println("\n--- Bezpečnostní specialisté ---");
        for (Employee s : specialists) System.out.println(s.getSurname() + " " + s.getName() + " (ID: " + s.getId() + ")");
    }

    public void showStats() {
        if (allEmployee.isEmpty()) {
            System.out.println("Firma nemá žádné zaměstnance pro statistiku.");
            return;
        }

        int mostBonds = -1;
        Employee favoriteEmployee = null;
        int countSpatna = 0, countPrumerna = 0, countDobra = 0;

        for (Employee z : allEmployee.values()) {
            if (z.getCoworkers().size() > mostBonds) {
                mostBonds = z.getCoworkers().size();
                favoriteEmployee = z;
            }

            for (CoopQuality uroven : z.getCoworkers().values()) {
                if (uroven == CoopQuality.SPATNA) countSpatna++;
                else if (uroven == CoopQuality.PRUMERNA) countPrumerna++;
                else if (uroven == CoopQuality.DOBRA) countDobra++;
            }
        }

        System.out.println("\n--- STATISTIKY FIRMY ---");
        if (favoriteEmployee != null && mostBonds > 0) {
            System.out.println("Nejvíce vazeb: " + favoriteEmployee.getName() + " " + favoriteEmployee.getSurname() + " (" + mostBonds + " vazeb)");
        } else {
            System.out.println("Zatím neexistují žádné vazby mezi zaměstnanci.");
        }


        int maxAppreciation = Math.max(countSpatna, Math.max(countPrumerna, countDobra));
        if (maxAppreciation == 0) {
            System.out.println("Zatím nebyla hodnocena žádná spolupráce.");
        } else if (maxAppreciation == countDobra) System.out.println("Převažující kvalita spolupráce: DOBRÁ (" + countDobra + "x)");
        else if (maxAppreciation == countPrumerna) System.out.println("Převažující kvalita spolupráce: PRŮMĚRNÁ (" + countPrumerna + "x)");
        else System.out.println("Převažující kvalita spolupráce: ŠPATNÁ (" + countSpatna + "x)");
    }

    public void showNumberOfPeople() {
        int countAnalysts = 0, countSpecialists = 0;
        for (Employee z : allEmployee.values()) {
            if (z instanceof DataAnalytic) countAnalysts++;
            else if (z instanceof SecuritySpecialist) countSpecialists++;
        }
        System.out.println("\n--- POČTY ZAMĚSTNANCŮ ---");
        System.out.println("Datoví analytici: " + countAnalysts);
        System.out.println("Bezpečnostní specialisté: " + countSpecialists);
    }
    
    // Získá celou databázi (budeme potřebovat pro Analytika a databázi)
    public static Map<Integer, Employee> getAllEmployees() {
        return allEmployee;
    }
}
