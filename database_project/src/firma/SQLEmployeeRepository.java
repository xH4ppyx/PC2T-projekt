package firma;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class SQLEmployeeRepository implements EmployeeRepository {
	
	private Connection connect(String path) throws SQLException{
		return DriverManager.getConnection("jdbc:sqlite" + path);
	}

	@Override
	public void saveToFile(List<Employee> employees, String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Employee> loadFromFile(String path) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
