package com.srf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


/* UserSearch and client are mixing too much, separate the 
 * Client class to only handle direcnt communication to the 
 * sql server. Leave processing to user search.
 */



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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Notes
     CREATE TABLE file_Index(
        -- This is the non normalized table that serves as the main file index
        -- possible data, numerical id, file name, size of file, keywords, 
        file_id INT PRIMARY KEY AUTO_INCREMENT,
        file_size BIGINT,
        file_name VARCHAR(255),
        file_extension VARCHAR(50),
        file_path VARCHAR(1024),


        -- File data atributes

        -- average word is 5 character, 100 key words with 100 delimeters
        file_keyword VARCHAR(5000)
        );
     */

    public ArrayList<ArrayList<Object>> sendQuery_Query(String q){
        /* OK */
        ArrayList<ArrayList<Object>> query_result = new ArrayList<>();

        try{
            statement = connection.createStatement();
            result = statement.executeQuery(q);
            while (result.next()){
                ArrayList<Object> compiled_line = new ArrayList<>();

                int file_id = result.getInt("file_id");
                long file_size = result.getInt("file_size");
                String file_name = result.getString("file_name");
                String file_extension = result.getString("file_extension");
                String file_path = result.getString("file_path");
                String file_keywords = result.getString("file_keyword");

                compiled_line.add(file_id);
                compiled_line.add(file_size);
                compiled_line.add(file_name);
                compiled_line.add(file_extension);
                compiled_line.add(file_path);
                compiled_line.add(file_keywords);

                query_result.add(compiled_line);
                
                //String comiled_line = ""+file_id+" " +file_size+" "+file_name+" "+file_extension+" "+file_path+" "+file_keywords;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return query_result;
    }

    public QuerySet fuzzySearch(String target_value){
        /* Obselete */
        /* SQL like statement usage 
         *  SELECT * FROM Customers
            WHERE city LIKE '%L%';
         */

        String query = "SELECT * FROM file_Index WHERE file_name LIKE '%"+target_value+"%' ;";
        // Search file name first values like target value
        QuerySet q_result = new QuerySet(sendQuery_Query(query));
        return q_result;
    }

    /*SELECT * FROM table_name 
    WHERE column_name REGEXP 'apple|banana|cherry'; */

    public QuerySet hfq_Search(String[] target_values){
        /*file_name VARCHAR(255),
        file_extension VARCHAR(50),
        file_path VARCHAR(1024), */
        StringBuilder[] file_queries = {
            new StringBuilder("SELECT * FROM file_Index WHERE file_name REGEXP "),
            new StringBuilder("SELECT * FROM file_Index WHERE file_extension REGEXP "),
            new StringBuilder("SELECT * FROM file_Index WHERE file_path REGEXP ")
        };

        StringBuilder regx_line = new StringBuilder("'");
        for(String str : target_values){
            regx_line.append(str).append("|");
        }
        regx_line.setCharAt(regx_line.length()-1, '\'');

        //ArrayList<QuerySet> query_sets = new ArrayList<>();

        int frequency_hits = 0;
        QuerySet hfq_query_set = new QuerySet(); // this will be the hashmaped map

        for(StringBuilder str : file_queries){
            String query = "" + str + regx_line;
            // System.out.println(query);
            // query_sets.add(new QuerySet(sendQuery_Query(query)));


        }
        return null;
    }


    public void sendQuery_Update(String q){
        try{
            statement = connection.createStatement();
            int result = statement.executeUpdate(q);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void DROP_FILE_INDEX(){
        sendQuery_Update("DROP TABLE file_Index");
    }

    public void close(){
        try {
            // Close resources
            if (result != null) result.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
            System.out.println("Closed database connection");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}