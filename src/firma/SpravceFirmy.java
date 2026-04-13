package firma;

import java.util.HashMap;
import java.util.Map;

public class SpravceFirmy {
    
    // Hlavní databáze všech zaměstnanců uložená v paměti
    private Map<Integer, Zamestnanec> vsichniZamestnanci;
    
    // Čítač pro automatické přidělování ID 
    private int dalsiVolneId;

    public SpravceFirmy() {
        this.vsichniZamestnanci = new HashMap<>();
        this.dalsiVolneId = 1; // Začínáme od ID 1
    }
    
    

    // Metoda pro přidání zaměstnance 
    public void pridejZamestnance(Zamestnanec novyZamestnanec) {
        // Automaticky přiřadíme volné ID a zvýšíme čítač
        novyZamestnanec.setId(dalsiVolneId);
        vsichniZamestnanci.put(dalsiVolneId, novyZamestnanec);
        System.out.println("Byl přidán zaměstnanec: " + novyZamestnanec.getJmeno() + " " + novyZamestnanec.getPrijmeni() + " s ID: " + dalsiVolneId);
        dalsiVolneId++;
    }

    // Metoda pro přidání spolupráce mezi dvěma lidmi 
    public void pridejSpolupraci(int idZamestnance, int idKolegy, UrovenSpoluprace uroven) {
        Zamestnanec z = vsichniZamestnanci.get(idZamestnance);
        Zamestnanec k = vsichniZamestnanci.get(idKolegy);

        if (z != null && k != null) {
            z.getSpolupracovnici().put(idKolegy, uroven);
            System.out.println("Spolupráce byla úspěšně přidána.");
        } else {
            System.out.println("Chyba: Jedno nebo obě ID neexistují.");
        }
    }
    
 // Metoda pro bezpečné odebrání zaměstnance včetně všech jeho vazeb 
    public void odeberZamestnance(int idKOdstraneni) {
        // Nejprve ověříme, zda zaměstnanec vůbec existuje
        if (vsichniZamestnanci.containsKey(idKOdstraneni)) {
            
            // 1. Smažeme ho z hlavní celofiremní evidence
            vsichniZamestnanci.remove(idKOdstraneni);
            
            // 2. Projdeme ÚPLNĚ VŠECHNY zbývající zaměstnance ve firmě
            for (Zamestnanec z : vsichniZamestnanci.values()) {
                // Pokusíme se smazat toto ID z jejich osobních seznamů.
                // Metoda .remove() u Mapy neudělá nic, pokud tam to ID náhodou není, 
                // takže to nespadne a je to bezpečné.
                z.getSpolupracovnici().remove(idKOdstraneni);
            }
            
            System.out.println("Zaměstnanec s ID " + idKOdstraneni + " byl úspěšně smazán včetně všech vazeb.");
        } else {
            System.out.println("Chyba: Zaměstnanec s ID " + idKOdstraneni + " nebyl nalezen.");
        }
    }
    
 // d) Vyhledání zaměstnance dle ID
    public void vyhledejZamestnance(int id) {
        Zamestnanec z = vsichniZamestnanci.get(id);
        if (z != null) {
            System.out.println("Nalezen zaměstnanec: " + z.getJmeno() + " " + z.getPrijmeni() + " (ID: " + z.getId() + ")");
            System.out.println("Rok narození: " + z.getRokNarozeni());
            System.out.println("Počet spolupracovníků: " + z.getSpolupracovnici().size());
        } else {
            System.out.println("Zaměstnanec s tímto ID neexistuje.");
        }
    }

    // Výpis všech zaměstnanců (bude se hodit pro kontrolu)
    public void vypisVsechnyZamestnance() {
        if (vsichniZamestnanci.isEmpty()) {
            System.out.println("Firma aktuálně nemá žádné zaměstnance.");
            return;
        }
        System.out.println("--- Seznam zaměstnanců ---");
        for (Zamestnanec z : vsichniZamestnanci.values()) {
            System.out.println("ID: " + z.getId() + " | " + z.getJmeno() + " " + z.getPrijmeni());
        }
        System.out.println("--------------------------");
    }  
    
 // f) Abecední výpis zaměstnanců podle příjmení ve skupinách
    public void vypisAbecedneVeSkupinach() {
        java.util.List<Zamestnanec> analytici = new java.util.ArrayList<>();
        java.util.List<Zamestnanec> specialistove = new java.util.ArrayList<>();

        // Rozdělení do skupin
        for (Zamestnanec z : vsichniZamestnanci.values()) {
            if (z instanceof DatovyAnalytik) analytici.add(z);
            else if (z instanceof BezpecnostniSpecialista) specialistove.add(z);
        }

        // Seřazení podle příjmení
        analytici.sort(java.util.Comparator.comparing(Zamestnanec::getPrijmeni));
        specialistove.sort(java.util.Comparator.comparing(Zamestnanec::getPrijmeni));

        System.out.println("\n--- Datoví analytici ---");
        for (Zamestnanec a : analytici) System.out.println(a.getPrijmeni() + " " + a.getJmeno() + " (ID: " + a.getId() + ")");

        System.out.println("\n--- Bezpečnostní specialisté ---");
        for (Zamestnanec s : specialistove) System.out.println(s.getPrijmeni() + " " + s.getJmeno() + " (ID: " + s.getId() + ")");
    }

    // g) Statistiky - převažující kvalita a nejvíce vazeb
    public void vypisStatistiky() {
        if (vsichniZamestnanci.isEmpty()) {
            System.out.println("Firma nemá žádné zaměstnance pro statistiku.");
            return;
        }

        int nejviceVazeb = -1;
        Zamestnanec nejoblibenejsi = null;
        int pocetSpatna = 0, pocetPrumerna = 0, pocetDobra = 0;

        for (Zamestnanec z : vsichniZamestnanci.values()) {
            // Hledání nejvíce vazeb
            if (z.getSpolupracovnici().size() > nejviceVazeb) {
                nejviceVazeb = z.getSpolupracovnici().size();
                nejoblibenejsi = z;
            }

            // Sčítání kvalit spolupráce u tohoto zaměstnance
            for (UrovenSpoluprace uroven : z.getSpolupracovnici().values()) {
                if (uroven == UrovenSpoluprace.SPATNA) pocetSpatna++;
                else if (uroven == UrovenSpoluprace.PRUMERNA) pocetPrumerna++;
                else if (uroven == UrovenSpoluprace.DOBRA) pocetDobra++;
            }
        }

        System.out.println("\n--- STATISTIKY FIRMY ---");
        if (nejoblibenejsi != null && nejviceVazeb > 0) {
            System.out.println("Nejvíce vazeb: " + nejoblibenejsi.getJmeno() + " " + nejoblibenejsi.getPrijmeni() + " (" + nejviceVazeb + " vazeb)");
        } else {
            System.out.println("Zatím neexistují žádné vazby mezi zaměstnanci.");
        }

        // Zjištění převažující kvality
        int maxHodnoceni = Math.max(pocetSpatna, Math.max(pocetPrumerna, pocetDobra));
        if (maxHodnoceni == 0) {
            System.out.println("Zatím nebyla hodnocena žádná spolupráce.");
        } else if (maxHodnoceni == pocetDobra) System.out.println("Převažující kvalita spolupráce: DOBRÁ (" + pocetDobra + "x)");
        else if (maxHodnoceni == pocetPrumerna) System.out.println("Převažující kvalita spolupráce: PRŮMĚRNÁ (" + pocetPrumerna + "x)");
        else System.out.println("Převažující kvalita spolupráce: ŠPATNÁ (" + pocetSpatna + "x)");
    }

    // h) Výpis počtu zaměstnanců ve skupinách
    public void vypisPoctyVeSkupinach() {
        int pocetAnalytiku = 0, pocetSpecialistu = 0;
        for (Zamestnanec z : vsichniZamestnanci.values()) {
            if (z instanceof DatovyAnalytik) pocetAnalytiku++;
            else if (z instanceof BezpecnostniSpecialista) pocetSpecialistu++;
        }
        System.out.println("\n--- POČTY ZAMĚSTNANCŮ ---");
        System.out.println("Datoví analytici: " + pocetAnalytiku);
        System.out.println("Bezpečnostní specialisté: " + pocetSpecialistu);
    }
    
    // Získá celou databázi (budeme potřebovat pro Analytika a databázi)
    public Map<Integer, Zamestnanec> getVsichniZamestnanci() {
        return vsichniZamestnanci;
    }
}