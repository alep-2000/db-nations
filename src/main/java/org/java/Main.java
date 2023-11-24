package org.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
//		base();
		Milestone3();
	}
	
	private static void Milestone3() {
		final String url = "jdbc:mysql://localhost:3306/db-nations";
		final String user = "root";
		final String pws = "";
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Inserisci la stringa di ricerca: ");
		String strRicerca = sc.nextLine();
		
		
		try (Connection con = DriverManager.getConnection(url, user, pws)) {
			
			final String SQL = "SELECT countries.name AS 'nazione', countries.country_id, regions.name AS 'regione', continents.name AS 'continente'" +
								" FROM countries" +
								" JOIN regions" + 
									" ON countries.region_id = regions.region_id" +
								" JOIN continents" + 
									" ON regions.continent_id = continents.continent_id" +
							" WHERE countries.name LIKE ? " +
							" ORDER BY countries.name" + 
							";";
					
			try (PreparedStatement ps = con.prepareStatement(SQL)) {
				
				ps.setString(1, "%" + strRicerca + "%");
				
				try (ResultSet rs = ps.executeQuery()) {
					
					
					while(rs.next()) {
						final String COUNTRY_NAME = rs.getString(1);
						final int COUNTRY_ID = rs.getInt(2);
						final String REGION_NAME = rs.getString(3);
						final String CONTINENT_NAME = rs.getString(4);
						
						System.out.println("[" + COUNTRY_ID + "] " +
											"Nome Nazione: " + COUNTRY_NAME + "\n" +
											"Nome Regione: " + REGION_NAME + "\n" +
											"Nome Continente: " + CONTINENT_NAME
											);
					}
				}
			}
		} catch (Exception e) {
			
			System.out.println("Error in db: " + e.getMessage());
		}
		
		sc.close();
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
