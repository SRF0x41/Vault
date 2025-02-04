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
    private ResultSet result = null;
    
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
     CREATE TABLE file_index(
        -- This is the non normalized table that serves as the main file index
        -- possible data, numerical id, file name, size of file, keywords, 
        file_id INT PRIMARY KEY AUTO_INCREMENT,
        file_size INT,
        file_name VARCHAR(255),
        file_extension VARCHAR(50),
        file_path VARCHAR(1024),


        -- File data atributes

        -- average word is 5 character, 100 key words with 100 delimeters
        file_keyword VARCHAR(600)
        );
     */

    public void sendQuery_Query(String q){
        // in the future dont let anyone directly input data to the server
        try{
            statement = connection.createStatement();
            result = statement.executeQuery(q);
            while (result.next()){
                int file_id = result.getInt("file_id");
                int file_size = result.getInt("file_size");
                String file_name = result.getString("file_name");
                String file_extension = result.getString("file_extension");
                String file_path 