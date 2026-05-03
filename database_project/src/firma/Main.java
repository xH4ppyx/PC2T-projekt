package firma;

import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Inicializace nástrojů
        Scanner scanner = new Scanner(System.in);
        CompanyManager manager = new CompanyManager();
        EmployeeRepository repo = new FileEmployeeRepository();
        
        List<Employee> loaded = repo.loadFromFile("src/firma/employees.txt");
        manager.setEmployees(loaded);
        
        System.out.println("Vítejte v databázovém systému zaměstnanců!");

        System.out.println("Načítaných zaměstnanců: " + loaded.size());
        
        boolean runs = true;


        // Nekonečný cyklus pro zobrazení menu
        while (runs) {
        	System.out.println("\n=== HLAVNÍ MENU ===");
            System.out.println("1. Přidat datového analytika");
            System.out.println("2. Přidat bezpečnostního specialistu");
            System.out.println("3. Vypsat všechny zaměstnance");
            System.out.println("4. Odebrat zaměstnance");
            System.out.println("5. Vypsat abecedně ve skupinách");
            System.out.println("6. Zobrazit statistiky firmy");
            System.out.println("7. Vypsat počty lidí ve skupinách");
            System.out.println("8. Přidat spolupráci");
            System.out.println("0. Ukončit program");
            System.out.print("Zadejte volbu: ");

            // Ošetření vstupu (aby to nespadlo, když uživatel zadá písmeno místo čísla, budeme řešit později)
            int choice = scanner.nextInt();
            scanner.nextLine(); // Tohle je důležité: "sežere" to prázdný znak Enteru po zadání čísla

            switch (choice) {
                case 1:
                    System.out.print("Jméno: ");
                    String name = scanner.nextLine();
                    System.out.print("Příjmení: ");
                    String surname = scanner.nextLine();
                    System.out.print("Rok narození: ");
                    int year = scanner.nextInt();
                    // ID dáváme 0, protože Správce si ho stejně automaticky přepíše na správné
                    manager.addEmployee(new DataAnalytic(0, name, surname, year));
                    break;
                case 2:
                    System.out.print("Jméno: ");
                    String nameSpec = scanner.nextLine();
                    System.out.print("Příjmení: ");
                    String surnameSpec = scanner.nextLine();
                    System.out.print("Rok narození: ");
                    int yearSpec = scanner.nextInt();
                    manager.addEmployee(new SecuritySpecialist(0, nameSpec, surnameSpec, yearSpec));
                    break;
                case 3:
                    manager.showAllEmployee();
                    break;
                case 4:
                    System.out.print("Zadejte ID zaměstnance k odstranění: ");
                    int idSmazat = scanner.nextInt();
                    manager.removeEmployee(idSmazat);
                    break;                  
                case 5:
                    manager.showABCGroup();
                    break;
                case 6:
                    manager.showStats();
                    break;
                case 7:
                    manager.showNumberOfPeople();
                    break;
                case 8:
                    System.out.print("ID zaměstnance: ");
                    int id1 = scanner.nextInt();

                    System.out.print("ID kolegy: ");
                    int id2 = scanner.nextInt();

                    System.out.println("Kvalita (1 = SPATNA, 2 = PRUMERNA, 3 = DOBRA): ");
                    int q = scanner.nextInt();

                    CoopQuality quality;

                    if (q == 1) quality = CoopQuality.SPATNA;
                    else if (q == 2) quality = CoopQuality.PRUMERNA;
                    else quality = CoopQuality.DOBRA;

                    manager.addCoop(id1, id2, quality);
                    break;
                case 0:
                	repo.saveToFile(new ArrayList<>(manager.getAllEmployees().values()), "src/firma/employees.txt");
                	runs = false;
                	System.out.println("Data uložená. Ukončuji program...");
                    break;
                default:
                    System.out.println("Neplatná volba, zkuste to znovu.");
                    
                    
            } 
       
        }
        scanner.close();
    }
}