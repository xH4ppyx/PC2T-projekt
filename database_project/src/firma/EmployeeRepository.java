package firma;

import java.util.List;

public interface EmployeeRepository {
	
	void saveToFile(List<Employee> employees, String path);

    List<Employee> loadFromFile(String path);
	
}	
