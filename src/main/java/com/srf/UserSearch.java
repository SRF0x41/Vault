package com.srf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserSearch {

    private final Client client;
    public ArrayList<ArrayList<Object>> q_set;

    public UserSearch(Client c) {
        client = c;
    }

    public ArrayList<ArrayList<Object>> userPrompt_fuzzySearch(String user_prompt) {
        TextTools tt = new TextTools();
        String[] keywords = tt.pullKeywords(user_prompt);
        q_set = client.fuzzySearch(user_prompt);
        return q_set;
    }

    public TreeMap<Integer, ArrayList<ArrayList<Object>>> hfq_Search(String raw_user_prompt) {

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

        TreeMap<Integer, ArrayList<ArrayList<Object>>> tree_qset = new TreeMap<>(Collections.reverseOrder());

        // Check every varchar column for matching keywords

        for (StringBuilder str : file_queries_pulltext) {
            String quotes_escaped_regex = "'" + regex_line + "\'";
            String sql_regex_line = str.append(quotes_escaped_regex).toString();
            System.out.println(sql_regex_line);
            ArrayList<ArrayList<Object>> q_return = client.sendQuery_Query(sql_regex_line);

            for (ArrayList<Object> sql_row : q_return) {
                // Count frequency, add it to the master_hash_qset with the frequency being the
                // key
                int frequency = sqlreturn_keywordFrequency(sql_row, keywords);
                tree_qset.putIfAbsent(frequency, new ArrayList<>());
                tree_qset.get(frequency).add(sql_row);
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
        String regex = "\\b(" + String.join("|", keywords) + ")\\b";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        for (Object str : line) {
            if (str instanceof String) {
                Matcher matcher = pattern.matcher(str.toString());
                while (matcher.find()) {
                    frequency++;
                }
            }
        }
        return frequency;
    }

    public String hfqString(TreeMap<Integer, ArrayList<ArrayList<Object>>> tree_qset) {
        StringBuilder out = new StringBuilder();
        for (Integer i : tree_qset.keySet()) {
            // get ArrayList<ArrayList<Object>>
            for (ArrayList<Object> sql_row : tree_qset.get(i)) {
                // get each sql row from
                String file_name = sql_row.get(2).toString();
                String file_path = sql_row.get(4).toString();
                String file_keyw = sql_row.get(5).toString();

                // out.append("frq: ").append(i).append(" ");
                out.append(file_name).append("\n");
                out.append(file_path).append("\n");
                out.append(file_keyw).append("\n\n");
            }
        }
        return out.toString();

    }

    /* Methods for writing to JTextArea */

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
