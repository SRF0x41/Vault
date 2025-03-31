package com.srf;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


/* UserSearch and Client are mixing too much, separate the 
 * Client class to only handle direct communication to the 
 * SQL server. Leave processing to UserSearch.
 */

public class Client {
    // Database connection details
    private String jdbcURL = "jdbc:mysql://localhost:3306/FileIndex"; // Replace with your DB URL
    private String username = "root"; // Replace with your username
    private String password = "kekito26"; // Replace with your password

    // SQL connection components
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet result = null;

    public Client() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC driver
            System.out.println(password); // Debugging: Print password (Consider removing for security reasons)
            connection = DriverManager.getConnection(jdbcURL, username, password); // Establish connection
            System.out.println("Connected to the database!");
        } catch (Exception e) {
            e.printStackTrace(); // Print error if connection fails
        }
    }

    /*
     * SQL Table Structure
     * CREATE TABLE file_Index(
     * file_id INT PRIMARY KEY AUTO_INCREMENT, // Unique identifier for files
     * file_size BIGINT, // File size in bytes
     * file_name VARCHAR(255), // File name
     * file_extension VARCHAR(50), // File extension
     * file_path VARCHAR(1024), // Full file path
     * file_keyword VARCHAR(5000) // Keywords for search
     * );
     */

    // Executes a SELECT query and returns results as a nested ArrayList
    public ArrayList<ArrayList<Object>> sendQuery_Query(String q) {
        ArrayList<ArrayList<Object>> query_result = new ArrayList<>();

        try {
            statement = connection.createStatement(); // Create SQL statement
            result = statement.executeQuery(q); // Execute query

            while (result.next()) { // Iterate over results
                ArrayList<Object> compiled_line = new ArrayList<>();
                
                // Retrieve column values
                int file_id = result.getInt("file_id"); // Unique file identifier
                long file_size = result.getInt("file_size"); // File size in bytes
                String file_name = result.getString("file_name"); // File name
                String file_extension = result.getString("file_extension"); // File extension
                String file_path = result.getString("file_path"); // File path
                String file_keywords = result.getString("file_keywords"); // Keywords for search
                
                // Store values in lowercase for uniformity
                compiled_line.add(file_id);
                compiled_line.add(file_size);
                compiled_line.add(file_name.toLowerCase());
                compiled_line.add(file_extension.toLowerCase());
                compiled_line.add(file_path.toLowerCase());
                compiled_line.add(file_keywords.toLowerCase());

                query_result.add(compiled_line); // Add row to results
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print error details
        }
        return query_result;
    }


    // CHECK FOR EXISTENCE HERE

    public boolean queryForExistence(String query){
        try{
            statement = connection.createStatement(); // Create SQL statement
            result = statement.executeQuery(query); // Execute query
            while(result.next()){
                return(result.getInt(1) == 1);
            }
        }catch (Exception e) {
            e.printStackTrace(); // Print error details
        }
        return false;
    }

    // Performs a fuzzy search for file names containing the target value
    public ArrayList<ArrayList<Object>> fuzzySearch(String target_value) {
        // LIKE query to find file names that contain the target value
        String query = "SELECT * FROM file_Index WHERE file_name LIKE '%" + target_value + "%' ;";
        return sendQuery_Query(query);
    }

    /*
     * Performs a keyword-based search using the REGEXP operator.
     * Example:
     * SELECT * FROM file_Index WHERE file_name REGEXP 'apple|banana|cherry';
     */

    // Executes an UPDATE/INSERT/DELETE query
    public void sendQuery_Update(String q) {
        try {
            statement = connection.createStatement(); // Create SQL statement
            int result = statement.executeUpdate(q); // Execute update query
        } catch (Exception e) {
            e.printStackTrace(); // Print error details
        }
    }

    public void DELETE_TABLE_DATA(){
        sendQuery_Query("TRUNCATE TANBLE file_index");
    }
    
    // Closes database connections and resources
    public void close() {
        try {
            if (result != null)
                result.close(); // Close result set
            if (statement != null)
                statement.close(); // Close statement
            if (connection != null)
                connection.close(); // Close connection
            System.out.println("Closed database connection");
        } catch (Exception e) {
            e.printStackTrace(); // Print error details
        }
    }
}
