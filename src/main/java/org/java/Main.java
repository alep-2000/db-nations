package org.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
	public static void main(String[] args) {
		base();
	}
	
	private static void base() {
		final String url = "jdbc:mysql://localhost:3306/db-nations";
		final String user = "root";
		final String pws = "";
		
		try (Connection con 
				= DriverManager.getConnection(url, user, pws)) {  
			
			final String sql = "SELECT countries.name AS 'nazione', countries.country_id, regions.name AS 'regione', continents.name AS 'continente'" +
								" FROM countries" +
									" JOIN regions" + 
										" ON countries.region_id = regions.region_id" +
									" JOIN continents" + 
										" ON regions.continent_id = continents.continent_id" +
								" ORDER BY countries.name" + 
								";";	  
			
			try(PreparedStatement ps = con.prepareStatement(sql)){
				try(ResultSet rs = ps.executeQuery()){
					
					while(rs.next()) {
						
						String countryName = rs.getString(1);
						int countryId = rs.getInt(2);
						String regionName = rs.getString(3);
						String continentsName = rs.getNString(4);
						
						System.out.println("[" + countryId + "] " + "\n" 
											+ "Nome Nazione: " + countryName + "\n" 
											+ "Nome Regione: " + regionName + "\n" 
											+ "Nome Continente: " + continentsName + "\n"
											+ "----------------------------------------");
					}
				}
			}
		} catch (Exception e) {
			
			System.out.println("Error in db: " + e.getMessage());
		}
		
	}
}
