package firma;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class CompanyManager {
    
    // Hlavní databáze všech zaměstnanců uložená v paměti
    private Map<Integer, Employee> allEmployee;
    
    // Čítač pro automatické přidělování ID 
    private int freeID;

    public CompanyManager() {
        this.allEmployee = new HashMap<>();
        this.freeID = 1; // Začínáme od ID 1
    }
    
    

    // Metoda pro přidání zaměstnance 
    public void addEmployee(Employee newEmployee) {
        // Automaticky přiřadíme volné ID a zvýšíme čítač
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

        // nastav freeID správne
        freeID = allEmployee.keySet().stream().max(Integer::compare).orElse(0) + 1;
    }

    // Metoda pro přidání spolupráce mezi dvěma lidmi 
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
    
 // Metoda pro bezpečné odebrání zaměstnance včetně všech jeho vazeb 
    public void removeEmployee(int idToRemove) {
        // Nejprve ověříme, zda zaměstnanec vůbec existuje
        if (allEmployee.containsKey(idToRemove)) {
            
            // 1. Smažeme ho z hlavní celofiremní evidence
            allEmployee.remove(idToRemove);
            
            // 2. Projdeme ÚPLNĚ VŠECHNY zbývající zaměstnance ve firmě
            for (Employee z : allEmployee.values()) {
                // Pokusíme se smazat toto ID z jejich osobních seznamů.
                // Metoda .remove() u Mapy neudělá nic, pokud tam to ID náhodou není, 
                // takže to nespadne a je to bezpečné.
                z.getCoworkers().remove(idToRemove);
            }
            
            System.out.println("Zaměstnanec s ID " + idToRemove + " byl úspěšně smazán včetně všech vazeb.");
        } else {
            System.out.println("Chyba: Zaměstnanec s ID " + idToRemove + " nebyl nalezen.");
        }
    }
    
 // d) Vyhledání zaměstnance dle ID
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

    // Výpis všech zaměstnanců (bude se hodit pro kontrolu)
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
    
 // f) Abecední výpis zaměstnanců podle příjmení ve skupinách
    public void showABCGroup() {
        java.util.List<Employee> analysts = new java.util.ArrayList<>();
        java.util.List<Employee> specialists = new java.util.ArrayList<>();

        // Rozdělení do skupin
        for (Employee z : allEmployee.values()) {
            if (z instanceof DataAnalytic) analysts.add(z);
            else if (z instanceof SecuritySpecialist) specialists.add(z);
        }

        // Seřazení podle příjmení
        analysts.sort(java.util.Comparator.comparing(Employee::getSurname));
        specialists.sort(java.util.Comparator.comparing(Employee::getSurname));

        System.out.println("\n--- Datoví analytici ---");
        for (Employee a : analysts) System.out.println(a.getSurname() + " " + a.getName() + " (ID: " + a.getId() + ")");

        System.out.println("\n--- Bezpečnostní specialisté ---");
        for (Employee s : specialists) System.out.println(s.getSurname() + " " + s.getName() + " (ID: " + s.getId() + ")");
    }

    // g) Statistiky - převažující kvalita a nejvíce vazeb
    public void showStats() {
        if (allEmployee.isEmpty()) {
            System.out.println("Firma nemá žádné zaměstnance pro statistiku.");
            return;
        }

        int mostBonds = -1;
        Employee favoriteEmployee = null;
        int countSpatna = 0, countPrumerna = 0, countDobra = 0;

        for (Employee z : allEmployee.values()) {
            // Hledání nejvíce vazeb
            if (z.getCoworkers().size() > mostBonds) {
                mostBonds = z.getCoworkers().size();
                favoriteEmployee = z;
            }

            // Sčítání kvalit spolupráce u tohoto zaměstnance
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

        // Zjištění převažující kvality
        int maxAppreciation = Math.max(countSpatna, Math.max(countPrumerna, countDobra));
        if (maxAppreciation == 0) {
            System.out.println("Zatím nebyla hodnocena žádná spolupráce.");
        } else if (maxAppreciation == countDobra) System.out.println("Převažující kvalita spolupráce: DOBRÁ (" + countDobra + "x)");
        else if (maxAppreciation == countPrumerna) System.out.println("Převažující kvalita spolupráce: PRŮMĚRNÁ (" + countPrumerna + "x)");
        else System.out.println("Převažující kvalita spolupráce: ŠPATNÁ (" + countSpatna + "x)");
    }

    // h) Výpis počtu zaměstnanců ve skupinách
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
    public Map<Integer, Employee> getAllEmployees() {
        return allEmployee;
    }
}