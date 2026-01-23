package com.srf;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        // Reliable database and file searching
        // indexFiles("/home/acerlaptop1/Desktop/MacBackup3_25_2025");
        String root_path = "/home/user1/Desktop/MacBackup3_25_2025";

        Client client = new Client();
        Scanner scanner = new Scanner(System.in);
        String cliInput;

        System.out.println("Type 'exit' to quit.");

        while (!(cliInput = scanner.nextLine()).equals("exit")) {
            cliInput = cliInput.strip();
            String[] search_command = cliInput.split(" ");

            // Inputing a search command
            if (search_command.length > 1) {
                if (search_command[0].equals("search")) {
                    System.out.println("VOID");
                }
            } else if (cliInput.equals("reindex")) { 
                System.out.println("Reindexing...");
                Index indexer = new Index(client);
                indexer.indexFiles(root_path);
            } else if (cliInput.equals("reindex-hardreset")) {
                // Deletes the entire database and reindex
                client.DELETE_TABLE_DATA();
                indexFiles(root_path, client);

            } else if (cliInput.equals("show-root-path")) {
                System.out.println(root_path);
            } else if (cliInput.equals("help")) {
                System.out.println(
                        "Commands:\n"
                        + "  reindex             VOID\n"
                        + "  reindex-hardreset   VOID\n"
                        + "  show-root-path      VOID"
                );
            } else {
                System.out.println("Unidentified command");
            }

            /*
            the actual search commands
            SearchData searchData = new SearchData(client);
                searchData.search(cliInput); */
        }

        client.close();
        scanner.close();
    }

    public static void indexFiles(String root_path, Client client) {
        try {
            FSearch search_all = new FSearch(root_path);
            FileAnalyzer fa = new FileAnalyzer();
            search_all.searchDir_toSQL(client, fa);
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            client.close();
        }
    }
}
