package com.srf;

import java.io.File;

public class FileAnalyzer{

    /*
     * CREATE TABLE file_index(
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
    private String file_size;
    private String file_path;
    private String file_name;
    private String file_extension;
    private String file_keywords;

    public FileAnalyzer(){

    }

    public void collectAtributes(File line){
        file_path = line.getAbsolutePath();
        file_size = convertBytes(line.length()); // this is bytes

    }

    public String convertBytes(long bytes){
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
        String formatted = String.format("%.2f", d_bytes);
        return String.format("%.2f", d_bytes) +" "+ unitAbv[size];
    }

    

    


}
