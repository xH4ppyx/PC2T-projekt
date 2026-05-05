package firma;

import java.util.HashMap;
import java.util.Map;

public abstract class Employee {
    protected int id;
    protected String name;
    protected String surname;
    protected int birthyear;


    private Map<Integer, CoopQuality> coworkers;

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String jmeno) {
		this.name = jmeno;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String prijmeni) {
		this.surname = prijmeni;
	}

	public int getBirthyear() {
		return birthyear;
	}

	public void setBirthyear(int Birthyear) {
		this.birthyear = Birthyear;
	}
	public Map<Integer, CoopQuality> getCoworkers() {
		return coworkers;
	}

	public void setCoworkers(Map<Integer, CoopQuality> spolupracovnici) {
		this.coworkers = spolupracovnici;
	}

	public Employee(int id, String name, String surname, int Birthyear) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthyear = Birthyear;

        this.coworkers = new HashMap<>();
    }


    public abstract void startSkill();

}
