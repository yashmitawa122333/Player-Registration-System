package javagui;

import java.sql.*;
import javax.swing.*;

public class Operations {
    Operations(String jdbcUrl, String username, String password){
        // Establish a connection to the Oracle database
    	try {
			Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
				if (connection != null) {
	                System.out.println("Connected to Oracle database!");
	                //Create the tables 
	                Statement statement = connection.createStatement();
	                String query = "CREATE TABLE IF NOT EXISTS Game " +
	                        "(game_id INT PRIMARY KEY AUTO_INCREMENT, " +
	                        " game_title VARCHAR(50) NOT NULL)";

			        statement.executeUpdate(query);
		
			        // Creating Player table 
			        query = "CREATE TABLE IF NOT EXISTS Player (" +
			                "player_id INT PRIMARY KEY AUTO_INCREMENT, " +
			                "first_name VARCHAR(50) NOT NULL, " +
			                "last_name VARCHAR(50) NOT NULL, " +  
			                "address VARCHAR(100), " +
			                "province VARCHAR(100), " + 
			                "postal_code VARCHAR(10), " +
			                "phone_number VARCHAR(20))";
		
			        statement.executeUpdate(query);
		
			        // Creating PlayerAndGame
			        query = "CREATE TABLE IF NOT EXISTS PlayerAndGame " +
			                "(player_game_id INT PRIMARY KEY AUTO_INCREMENT, " +
			                "game_id INT, " +
			                "player_id INT, " +
			                "playing_date DATE, " + 
			                "score INT, " +
			                "FOREIGN KEY (game_id) REFERENCES Game(game_id), " +
			                "FOREIGN KEY (player_id) REFERENCES Player(player_id))";
		
			        statement.executeUpdate(query);
		
			        // Remember to close the connection when done
			        connection.close();

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
}
