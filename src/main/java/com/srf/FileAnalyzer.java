package com.srf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class FileAnalyzer{

    /*
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
    file_keyword VARCHAR(600)
    );
     */

    private static final HashSet<String> stopWords = new HashSet<>();
   
    public FileAnalyzer(){
        
    }


    public String fileAtributes_toSQLQuery(File line){
        long file_size = line.length(); //convertBytes(line.length()); // this is bytes
        String file_path = line.getAbsolutePath().replace("'", "''");
        String file_name = line.getName().replace("'", "''");
        String file_extension = pullExtension(file_name);
        String file_keywords = "";

        String query = "INSERT INTO file_Index (file_size, file_name, file_extension, file_path, file_keyword)";
        String values = "VALUES ("+file_size+",'"+file_name+"','"+file_extension+"','"+file_path+"','"+file_keywords+"')";
        return query +" "+values;
    }

    private String pullKeywords(File file_path){
        if(!file_path.canRead()){
            return "";
        }
        /* Algo options
         * First clean file of commonwords
         * remove special characters
         * all lowercase
         * 
         * 1. Frequency, higher frequewncy probaly the keywords
         *  use hashmaps
         */

        HashMap<String,Integer> word_frequency = new HashMap<>();

        String filePath = "stop_words.txt"; 
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath)); // Open the file for reading
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(" ");
                for(String w : words){
                    if(!stopWords.contains(w)){
                        if(word_frequency.containsKey(w)){
                            word_frequency.put(w,word_frequency.get(w) + 1);
                        } else {
                            word_frequency.put(w,1);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close(); 
                }
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        }

        
        return "";

    }
   

    private String pullExtension(String name){
        int dotIndex = name.lastIndexOf('.');
        if (dotIndex > 0) {
            return name.substring(dotIndex + 1);
        }
        return "";
    }

    private String convertBytes(long bytes){
        double d_bytes = (double) bytes;
        String[] unitAbv = {"B","KB","MB","GB","TB","PB"};
        int size = 0;
        while(d_bytes > 1000){
            d_bytes /= 1024;
            size += 1;
        }
        if(d_bytes %1 == 0){
            return (int)d_bytes +" "+unitAbv[size];
        }
        return String.format("%.2f", d_bytes) +" "+ unitAbv[size];
    }




    static {
        // Stop words hashset is used by all instances

        String filePath = "stop_words.txt"; 
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath)); // Open the file for reading
            String line;
            while ((line = reader.readLine()) != null) {
                stopWords.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close(); 
                }
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        }
    }



    
    


}
