package firma;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLEmployeeRepository implements EmployeeRepository {
	
	private Connection connect(String path) throws SQLException{
		return DriverManager.getConnection("jdbc:sqlite:" + path);
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
		try (Connection conn = connect(path)){
			createTable(conn);
			
			Statement clear = conn.createStatement();
			clear.execute("DELETE FROM employees");
			clear.execute("DELETE FROM coworkers");
			
			String insertEmployee = "INSERT INTO employees VALUES (?, ?, ?, ?, ?)";
            String insertCoworker = "INSERT INTO coworkers VALUES (?, ?, ?)";
            
            PreparedStatement empStmt = conn.prepareStatement(insertEmployee);
            PreparedStatement coworkStmt = conn.prepareStatement(insertCoworker);
			
			for (Employee emp : employees) {
				empStmt.setInt(1, emp.getId());
				empStmt.setString(2, emp.getName());
				empStmt.setString(3, emp.getSurname());
				empStmt.setInt(4, emp.getBirthyear());
				empStmt.setString(5, emp.getClass().getSimpleName());
				empStmt.executeUpdate();
				
				for (Map.Entry<Integer, CoopQuality> entry : emp.getCoworkers().entrySet()) {
					coworkStmt.setInt(1, emp.getId());
					coworkStmt.setInt(2, entry.getKey());
					coworkStmt.setString(3, entry.getValue().name());
					coworkStmt.executeUpdate();
					
				}
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Employee> loadFromFile(String path) {
		Map<Integer, Employee> map = new HashMap<>();
		
		try (Connection conn = connect(path)){
			createTable(conn);	
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM employees");
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				int birthyear = rs.getInt("birthyear");
				String role = rs.getString("role");
				
				Employee emp;
				
				if (role.equals("DataAnalytic")) {
					emp = new DataAnalytic(id, name, surname, birthyear);	
				} else {
					emp = new SecuritySpecialist(id, name, surname, birthyear);
				}
				
				map.put(id, emp);
			}
			
			ResultSet coworkers = stmt.executeQuery("SELECT * FROM coworkers");
			
			while (coworkers.next()) {
				int empID = coworkers.getInt("employee_id");
				int coworkerID = coworkers.getInt("coworker_id");
				CoopQuality quality = CoopQuality.valueOf(coworkers.getString("quality"));
				
				Employee emp = map.get(empID);
				
				if (emp != null) {
					emp.getCoworkers().put(coworkerID, quality);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<>(map.values());
	}
	
}
