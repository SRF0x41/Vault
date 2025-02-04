package com.srf;

import java.io.File;

public class FileAnalyzer{

    /*
     * CREATE TABLE file_Index(
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
   
    public FileAnalyzer(){

    }


    public String fileAtributes_toSQLQuery(File line){
        String file_size = convertBytes(line.length()); // this is bytes
        String file_path = line.getAbsolutePath();
        String file_name = line.getName();
        String file_extension = pullExtension(file_name);
        String file_keywords = "";

        String query = "INSERT INTO your_table (file_size, file_name, file_extension, file_path, file_keyword)";
        String values = "VALUES ("+file_size+","+file_path+","+file_extension+","+file_path+","+file_keywords+")";
        return query +" "+values;
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

    
    


}
