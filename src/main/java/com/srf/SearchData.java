package com.srf;

public class SearchData {
    private Client client;
    public SearchData(Client c){
        client = c;
    }

    /*describe file_index;
        +----------------+---------------+------+-----+---------+----------------+
        | Field          | Type          | Null | Key | Default | Extra          |
        +----------------+---------------+------+-----+---------+----------------+
        | file_id        | int           | NO   | PRI | NULL    | auto_increment |
        | file_size      | bigint        | YES  |     | NULL    |                |
        | file_name      | varchar(255)  | YES  |     | NULL    |                |
        | file_extension | varchar(50)   | YES  |     | NULL    |                |
        | file_path      | varchar(1024) | YES  |     | NULL    |                |
        | file_keywords  | varchar(5000) | YES  |     | NULL    |                |
        +----------------+---------------+------+-----+---------+----------------+
        6 rows in set (0.00 sec)
    */

    public void search(String prompt){
        // Extract keywords from the prompt
        TextTools t_tools = new TextTools();
        String[] prompt_keywords = t_tools.pullKeywords(prompt); // Maybe this function could  be static

        System.out.print("Pulled keywords: ");
        for(String str : prompt_keywords){
            System.out.print(str+" ");
        }
        System.out.print("\n");

        /* Rank via keyword hit rate */

        // file_id non applicable

        // file_size implement later

    }



}
