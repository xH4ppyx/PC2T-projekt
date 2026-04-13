package firma;

import java.util.HashMap;
import java.util.Map;

public abstract class Zamestnanec {
    private int id;
    private String jmeno;
    private String prijmeni;
    private int rokNarozeni;

    // Dynamická datová struktura pro evidenci spolupráce (ID kolegy -> úroveň spolupráce) 
    private Map<Integer, UrovenSpoluprace> spolupracovnici;

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJmeno() {
		return jmeno;
	}

	public void setJmeno(String jmeno) {
		this.jmeno = jmeno;
	}

	public String getPrijmeni() {
		return prijmeni;
	}

	public void setPrijmeni(String prijmeni) {
		this.prijmeni = prijmeni;
	}

	public int getRokNarozeni() {
		return rokNarozeni;
	}

	public void setRokNarozeni(int rokNarozeni) {
		this.rokNarozeni = rokNarozeni;
	}

	public Map<Integer, UrovenSpoluprace> getSpolupracovnici() {
		return spolupracovnici;
	}

	public void setSpolupracovnici(Map<Integer, UrovenSpoluprace> spolupracovnici) {
		this.spolupracovnici = spolupracovnici;
	}

	public Zamestnanec(int id, String jmeno, String prijmeni, int rokNarozeni) {
        this.id = id;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.rokNarozeni = rokNarozeni;
        // Inicializace mapy (zde budeme ukládat např. klíč 5 = "dobrá")
        this.spolupracovnici = new HashMap<>();
    }

    // Abstraktní metoda pro specifické dovednosti skupin [cite: 14, 25]
    public abstract void spustDovednost();
    
    // Zbytek (gettery, settery) přidáme později...
}