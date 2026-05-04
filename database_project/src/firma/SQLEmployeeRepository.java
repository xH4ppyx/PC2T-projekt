package firma;

import java.sql.*;
import java.util.List;

public class SQLEmployeeRepository implements EmployeeRepository {
	
	private Connection connect(String path) throws SQLException{
		return DriverManager.getConnection("jdbc:sqlite" + path);
	}
	
	
	private void createTable(Connection connect) throws SQLException{
		String employeeTable = """
            CREATE TABLE IF NOT EXISTS employees (
                id INTEGER PRIMARY KEY,
                name TEXT,
                surname TEXT,
                birthyear INTEGER,
                role TEXT
            );
        """;
		
		String coworkerTable = """
				CREATE TABLE IF NOT EXISTS coworkers (
					employee_id INTEGER,
					coworker_id INTEGER,
					quality TEXT
					);
				""";
		
		try (Statement stmt = connect.createStatement()){
			stmt.execute(employeeTable);
			stmt.execute(coworkerTable);
		}
	}
	@Override
	public void saveToFile(List<Employee> employees, String path) {
		
		
	}

	@Override
	public List<Employee> loadFromFile(String path) {
		
		return null;
	}
	
}
