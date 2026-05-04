package firma;

import java.io.*;
import java.util.*;

public class FileEmployeeRepository implements EmployeeRepository {

	@Override
	public void saveToFile(List<Employee> employees, String path) {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {

	        for (Employee employee : employees) {

	            String line = employee.getId() + ";" +
	                          employee.getName() + ";" +
	                          employee.getSurname() + ";" +
	                          employee.getBirthyear() + ";" +
	                          employee.getClass().getSimpleName() + ";";

	            // coworkers
	            for (Map.Entry<Integer, CoopQuality> entry : employee.getCoworkers().entrySet()) {
	                line += entry.getKey() + ":" + entry.getValue() + ",";
	            }

	            writer.write(line);
	            writer.newLine();
	        }

	    } catch (IOException employee) {
	        employee.printStackTrace();
	    }
	}
	
	
	
	@Override
	public List<Employee> loadFromFile(String path) {
		Map<Integer, Employee> map = new HashMap<>();
	    List<String> lines = new ArrayList<>();
		
	    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
	        String line;

	        while ((line = reader.readLine()) != null) {
	            lines.add(line);

	            String[] parts = line.split(";");

	            int id = Integer.parseInt(parts[0]);
	            String name = parts[1];
	            String surname = parts[2];
	            int birthYear = Integer.parseInt(parts[3]);
	            String group = parts[4];

	            Employee e;

	            if (group.equals("DataAnalytic")) {
	                e = new DataAnalytic(id, name, surname, birthYear);
	            } else {
	                e = new SecuritySpecialist(id, name, surname, birthYear);
	            }

	            map.put(id, e);
		        }
	        
	        for (String line2 : lines) {
	        	String[] parts = line2.split(";");
	        	
	        	int id = Integer.parseInt(parts[0]);
	        	Employee emp = map.get(id);
	        	
	        	if (parts.length > 5 && !parts[5].isEmpty()) {
	        		String[] coworkers = parts[5].split(",");
	        		
	        		for (String c : coworkers) {
	        			if (c.isEmpty()) continue;

	        		    String[] pair = c.split(":");
	        		    if (pair.length != 2) continue;
	        			
	        			int coworkerID = Integer.parseInt(pair[0]);
	        			CoopQuality q = CoopQuality.valueOf(pair[1]);
	        			
	        			emp.getCoworkers().put(coworkerID, q);
	        		}
	        	}
	        	
	        }

		    } catch (IOException e) {
		        e.printStackTrace();
		    }

	    return new ArrayList<>(map.values());
		}

	
	
}