package com.srf;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

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

    public QuerySet userPrompt_hfq_Search(String user_prompt){
        TextTools tt = new TextTools();

        // Buncha nonsense
        LinkedHashMap<String,Integer> KeyWords = tt.pullKeywords(user_prompt);
        int k_length = KeyWords.size();
        String[] key_words = new String[k_length];
        int count = 0;
        for (Map.Entry<String, Integer> entry : KeyWords.entrySet()) {
            key_words[count] = entry.getKey();
            count++;
        }

        client.hfq_Search(key_words);
        return null;
    }

    // This is the main hfq_search method
    public QuerySet hfq_Search(String user_prompt){
        // Pull keywords
        TextTools tt = new TextTools();

        // Buncha nonsense
        LinkedHashMap<String,Integer> KeyWords = tt.pullKeywords(user_prompt);
        int k_length = KeyWords.size();
        String[] target_values = new String[k_length];
        int count = 0;
        for (Map.Entry<String, Integer> entry : KeyWords.entrySet()) {
            target_values[count] = entry.getKey();
            count++;
        }

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

    /* Methods for writing to JTextArea */
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
