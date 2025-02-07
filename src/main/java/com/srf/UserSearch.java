package com.srf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserSearch {

    private final Client client;
    public QuerySet q_set;
    

    public UserSearch(Client c) {
        client = c;
    }

    public QuerySet userPrompt_fuzzySearch(String user_prompt) {
        TextTools tt = new TextTools();
        String[] keywords = tt.pullKeywords(user_prompt);
        q_set = client.fuzzySearch(user_prompt);
        return q_set;
    }

    public TreeMap<Integer,ArrayList<Object>> hfq_Search(String raw_user_prompt) {

        // This aproach might be bad because the regex for searhcing the sql return
        // short circuits at the first sight of a keyword, therefore i can really check
        // the returned keyword string for number of mathces

        raw_user_prompt = raw_user_prompt.toLowerCase();

        // Pull Keywords
        TextTools tt = new TextTools();
        String[] keywords = tt.pullKeywords(raw_user_prompt);

        StringBuilder[] file_queries_pulltext = {
                new StringBuilder("SELECT * FROM file_Index WHERE file_name REGEXP "),
                new StringBuilder("SELECT * FROM file_Index WHERE file_extension REGEXP "),
                new StringBuilder("SELECT * FROM file_Index WHERE file_path REGEXP "),
                new StringBuilder("SELECT * FROM file_Index WHERE file_keyword REGEXP ")
        };

        StringBuilder regex_line = new StringBuilder();
        for (String str : keywords) {
            regex_line.append(str).append("|");
        }

        // regex line should be keyword1|keyword2|keyword3
        regex_line.deleteCharAt(regex_line.length() - 1);

        TreeMap<Integer, ArrayList<Object>> tree_qset = new TreeMap<>(Collections.reverseOrder());

        String sql_regex_line = "\'" + regex_line + "\'";
        // Check every varchar column for matching keywords

        for (StringBuilder str : file_queries_pulltext) {
            ArrayList<ArrayList<Object>> q_return = new ArrayList<>();
            q_return = client.sendQuery_Query(sql_regex_line);

            for (ArrayList<Object> sql_row : q_return) {
                // Count frequency, add it to the master_hash_qset with the frequency being the
                // key
                int frequency = sqlreturn_keywordFrequency(sql_row, keywords);
                tree_qset.put(frequency, sql_row);
            }
        }
        return tree_qset;
    }

    public int sqlreturn_keywordFrequency(ArrayList<Object> line, String[] keywords) {
        /*
         * Checks one row of sql to to see how many times the string fields match the
         * keywords
         */
        int frequency = 0;
        Pattern patern = Pattern.compile("\\b" + Arrays.toString(keywords) + "\\", Pattern.CASE_INSENSITIVE);
        for (Object str : line) {
            if (str instanceof String) {
                Matcher matcher = patern.matcher(str.toString());
                while (matcher.find()) {
                    frequency++;
                }
            }
        }
        return frequency;
    }

    public String hfqString(TreeMap<Integer, ArrayList<Object>> tree_qset){
        StringBuilder out = new StringBuilder();
        for(Integer i : tree_qset.keySet()){
            String file_name = tree_qset.get(i).get(2).toString();
            String file_extension = tree_qset.get(i).get(3).toString();
            String file_path = tree_qset.get(i).get(4).toString();

            out.append(file_name).append("\n").append(file_path).append("\n");
        }
        return out.toString();
    }


    /* Methods for writing to JTextArea */
    public String longLineText() {
        StringBuilder out = new StringBuilder();
        ArrayList<ArrayList<Object>> array_q_set = q_set.getMasterSet();
        for (ArrayList<Object> line : array_q_set) {
            String comp = line.get(2) + "\n" + line.get(4);
            out.append(comp + " \n\n");
        }
        return out.toString();
    }

    public String longLineText_HTML() {
        StringBuilder out = new StringBuilder();

        ArrayList<ArrayList<Object>> array_q_set = q_set.getMasterSet();
        for (ArrayList<Object> line : array_q_set) {
            String comp = line.get(2) + "\n" + line.get(4);
            out.append(comp + " \n\n");
        }
        return out.toString();
    }

    /*
     * CREATE TABLE file_Index(
     * -- This is the non normalized table that serves as the main file index
     * -- possible data, numerical id, file name, size of file, keywords,
     * file_id INT PRIMARY KEY AUTO_INCREMENT,
     * file_size BIGINT,
     * file_name VARCHAR(255),
     * file_extension VARCHAR(50),
     * file_path VARCHAR(1024),
     * 
     * 
     * -- File data atributes
     * 
     * -- average word is 5 character, 100 key words with 100 delimeters
     * file_keyword VARCHAR(5000)
     * );
     */

}
