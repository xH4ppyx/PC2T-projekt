package firma;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Inicializace nástrojů
        Scanner scanner = new Scanner(System.in);
        SpravceFirmy spravce = new SpravceFirmy();
        boolean bezi = true;

        System.out.println("Vítejte v databázovém systému zaměstnanců!");

        // Nekonečný cyklus pro zobrazení menu
        while (bezi) {
        	System.out.println("\n=== HLAVNÍ MENU ===");
            System.out.println("1. Přidat datového analytika");
            System.out.println("2. Přidat bezpečnostního specialistu");
            System.out.println("3. Vypsat všechny zaměstnance");
            System.out.println("4. Odebrat zaměstnance");
            System.out.println("5. Vypsat abecedně ve skupinách");
            System.out.println("6. Zobrazit statistiky firmy");
            System.out.println("7. Vypsat počty lidí ve skupinách");
            System.out.println("0. Ukončit program");
            System.out.print("Zadejte volbu: ");

            // Ošetření vstupu (aby to nespadlo, když uživatel zadá písmeno místo čísla, budeme řešit později)
            int volba = scanner.nextInt();
            scanner.nextLine(); // Tohle je důležité: "sežere" to prázdný znak Enteru po zadání čísla

            switch (volba) {
                case 1:
                    System.out.print("Jméno: ");
                    String jmeno = scanner.nextLine();
                    System.out.print("Příjmení: ");
                    String prijmeni = scanner.nextLine();
                    System.out.print("Rok narození: ");
                    int rok = scanner.nextInt();
                    // ID dáváme 0, protože Správce si ho stejně automaticky přepíše na správné
                    spravce.pridejZamestnance(new DatovyAnalytik(0, jmeno, prijmeni, rok));
                    break;
                case 2:
                    System.out.print("Jméno: ");
                    String jmenoSpec = scanner.nextLine();
                    System.out.print("Příjmení: ");
                    String prijmeniSpec = scanner.nextLine();
                    System.out.print("Rok narození: ");
                    int rokSpec = scanner.nextInt();
                    spravce.pridejZamestnance(new BezpecnostniSpecialista(0, jmenoSpec, prijmeniSpec, rokSpec));
                    break;
                case 3:
                    // Zde se volá metoda, kterou sis (snad) přidal do SpravceFirmy v minulém kroku
                    spravce.vypisVsechnyZamestnance();
                    break;
                case 4:
                    System.out.print("Zadejte ID zaměstnance k odstranění: ");
                    int idSmazat = scanner.nextInt();
                    spravce.odeberZamestnance(idSmazat);
                    break;                  
                case 5:
                    spravce.vypisAbecedneVeSkupinach();
                    break;
                case 6:
                    spravce.vypisStatistiky();
                    break;
                case 7:
                    spravce.vypisPoctyVeSkupinach();
                    break;
                case 0:
                    bezi = false;
                    System.out.println("Ukončuji program...");
                    break;
                default:
                    System.out.println("Neplatná volba, zkuste to znovu.");
            }
        }
        scanner.close();
    }
}