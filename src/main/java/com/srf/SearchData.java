package com.srf;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                // new StringBuilder("SELECT * FROM file_index WHERE file_keywords REGEXP ")
        };

        for (StringBuilder query : file_queries_pulltext) {
            String full_query = query.append(quotes_escaped_regex).toString();
            ArrayList<ArrayList<Object>> q_return = client.sendQuery_Query(full_query);
            printTable(q_return, prompt_keywords);
        }

        // file_id non applicable

        // file_size implement later

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

    public void printTable(ArrayList<ArrayList<Object>> q_return, String[] keywords) {
        for (ArrayList<Object> row : q_return) {
            System.out.println("Total hits: "+countHitRate(row, keywords));
            String file_name = row.get(2).toString();
            String file_path = row.get(4).toString();
            // System.out.println(file_name+" "+file_path);
            String file_ext = row.get(3).toString();
            // System.out.println(file_name+" "+file_ext);
            String file_keywords = row.get(5).toString();
            System.out.println("\nFile Name: " + file_name + " File Extension: " + file_ext);
            System.out.println("File Path: " + file_path);
            // System.out.println("File Keywords: " + file_keywords);
        }
    }

}
