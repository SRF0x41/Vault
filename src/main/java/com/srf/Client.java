package com.srf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;



public class Client {
    private String jdbcURL = "jdbc:mysql://localhost:3306/fileIndex"; // Replace with your DB URL
    private String username = "root"; // Replace with your username
    private String password = "kekito26$$";

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    
    public Client(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            System.out.println(password);
            connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connected to the database!");

            // Create a statement
            //statement = connection.createStatement();

            // Execute a query
            // String sql = "SELECT id, name, email FROM users";
            // resultSet = statement.executeQuery(sql);

            // Process the results
            /*while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");

                System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Notes
     * sql structure
     * USE fileIndex;
        CREATE TABLE file_index(
        -- This is the non normalized table that serves as the main file index
        -- possible data, numerical id, file name, size of file, keywords, full text data (i feel like i should ommit this)
        file_id INT PRIMARY KEY AUTO_INCREMENT,
        file_size INT,
        file_extension VARCHAR(50)

); 
     */

    public void sendQuery(String q){
        // in the future dont let anyone directly input data to the server
        try{
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(q);
            while (result.next()){
                System.err.println("f");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    

    public void close(){
        try {
            // Close resources
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}