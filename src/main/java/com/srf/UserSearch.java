package com.srf;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class UserSearch {

    private final Client client;
    public QuerySet q_set;
    public UserSearch(Client c){
        client = c;
    }

    public QuerySet userPrompt_fuzzySearch(String user_prompt){
        TextTools tt = new TextTools();
        LinkedHashMap<String,Integer> keywords = tt.pullKeywords(user_prompt);
        q_set = client.fuzzySearch(user_prompt);
        return q_set;
    }

    public String longLineText(){
        StringBuilder out = new StringBuilder();
        ArrayList<ArrayList<Object>> array_q_set = q_set.getMasterSet();
        for(ArrayList<Object> line : array_q_set){
            String comp = line.get(2) + "\n" + line.get(4);
            out.append(comp + " \n\n");
        }
        return out.toString();
    }

    public String longLineText_HTML(){
        StringBuilder out = new StringBuilder();
    
        ArrayList<ArrayList<Object>> array_q_set = q_set.getMasterSet();
        for(ArrayList<Object> line : array_q_set){
            String comp = line.get(2) + "\n" + line.get(4);
            out.append(comp + " \n\n");
        }
        return out.toString();
    }

    /*CREATE TABLE file_Index(
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
    ); */


}
