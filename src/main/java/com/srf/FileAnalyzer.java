package com.srf;

import java.io.File;

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
   
    public FileAnalyzer(){

    }


    public String fileAtributes_toSQLQuery(File line){
        long file_size = line.length(); //convertBytes(line.length()); // this is bytes
        String file