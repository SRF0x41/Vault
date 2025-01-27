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
            statement = connection.createStatement();

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

    public void setPassword(String pas){
        password = pas;

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