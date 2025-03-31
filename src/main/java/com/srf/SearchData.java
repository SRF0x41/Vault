package com.srf;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.cj.x.protobuf.MysqlxResultset.Row;

public class SearchData {
    private Client client;

    public SearchData(Client c) {
        client = c;
    }

    /*
     * describe file_index;
     * +----------------+---------------+------+-----+---------+----------------+
     * | Field | Type | Null | Key | Default | Extra |
     * +----------------+---------------+------+-----+---------+----------------+
     * | file_id | int | NO | PRI | NULL | auto_increment |
     * | file_size | bigint | YES | | NULL | |
     * | file_name | varchar(255) | YES | | NULL | |
     * | file_extension | varchar(50) | YES | | NULL | |
     * | file_path | varchar(1024) | YES | | NULL | |
     * | file_keywords | varchar(5000) | YES | | NULL | |
     * +----------------+---------------+------+-----+---------+----------------+
     * 6 rows in set (0.00 sec)
     */

    public void search(String prompt) {
        // Extract keywords from the prompt
        TextTools t_tools = new TextTools();
        prompt = prompt.toLowerCase();
        String[] prompt_keywords = t_tools.pullKeywords(prompt); // Maybe this function could be static

        System.out.print("Pulled keywords: ");
        for (String str : prompt_keywords) {
            System.out.print(str + " ");
        }
        System.out.print("\n");

        StringBuilder regex_line = new StringBuilder();

        for (int i = 0; i < prompt_keywords.length - 1; i++) {
            regex_line.append(prompt_keywords[i]).append("|");
        }
        regex_line.append(prompt_keywords[prompt_keywords.length - 1]);

        String quotes_escaped_regex = "'" + regex_line + "\'";

        System.out.println(quotes_escaped_regex);

        /* Rank via keyword hit rate */

        StringBuilder[] file_queries_pulltext = {
                new StringBuilder("SELECT * FROM file_index WHERE file_name REGEXP "),
                new StringBuilder("SELECT * FROM file_index WHERE file_extension REGEXP "),
                new StringBuilder("SELECT * FROM file_index WHERE file_path REGEXP "),
                new StringBuilder("SELECT * FROM file_index WHERE file_keywords REGEXP ")
        };

        ArrayList<ArrayList<Object>> main_entry_list = new ArrayList<>();
        for (StringBuilder query : file_queries_pulltext) {
            String full_query = query.append(quotes_escaped_regex).toString();
            ArrayList<ArrayList<Object>> q_return = client.sendQuery_Query(full_query);

            // Append a frequency and add to the sorted linked list
            for (ArrayList<Object> row : q_return) {
                int frequency = countHitRate(row, prompt_keywords);
                row.add(frequency);
                concatenateList(main_entry_list,row);
            }
        }

        printMainList(main_entry_list);

        // file_id non applicable

        // file_size implement later

    }

    public void printMainList(ArrayList<ArrayList<Object>> main_entry_list) {
        // Check if the result is not empty
        if (main_entry_list.isEmpty()) {
            System.out.println("No results found.");
            return;
        }

        // Iterate through each row and print every object in the row on separate lines
        for (ArrayList<Object> row : main_entry_list) {
            // Print each element of the row with capitalized labels
            System.out.println("KEYWORD FREQUENCY: " + row.get(6));
            System.out.println("FILE_ID: " + row.get(0)); // file_id
            System.out.println("FILE_SIZE: " + row.get(1)); // file_size
            System.out.println("FILE_NAME: " + row.get(2)); // file_name
            System.out.println("FILE_EXTENSION: " + row.get(3)); // file_extension
            System.out.println("FILE_PATH: " + row.get(4)); // file_path
            System.out.println("FILE_KEYWORDS: " + row.get(5)); // file_keywords
            System.out.println(); // Add a blank line to separate each entry
        }
    }

    public void concatenateList(ArrayList<ArrayList<Object>> main_entry_list, ArrayList<Object> sql_row) {
        if (main_entry_list.isEmpty()) {
            main_entry_list.add(sql_row);
            return;
        }

        // Iterate through the main_entry_list to find the right position for sql_row
        for (int i = 0; i < main_entry_list.size(); i++) {
            ArrayList<Object> current_row = main_entry_list.get(i);
            int stored_row_hits = (int) current_row.get(current_row.size() - 1); // Last element as hits
            int row_hits = (int) sql_row.get(sql_row.size() - 1); // Last element as hits

            // Insert sql_row if its hits are less than the stored hits in current_row
            if (row_hits < stored_row_hits) {
                main_entry_list.add(i, sql_row);
                return; // Stop after inserting the row
            }
        }

        // If no position found, add the row at the end of the list (sorted order)
        main_entry_list.add(sql_row);
    }

    public int countHitRate(ArrayList<Object> q_return, String[] keywords) {

        int total_hits = 0;
        for (Object col : q_return) {
            for (String keyword : keywords) {
                Pattern pattern = Pattern.compile("\\b" + keyword + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(col.toString());
                while (matcher.find()) {
                    total_hits++;
                }
            }
        }

        return total_hits;
    }

}
