package com.srf;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // Reliable database and file searching
        // indexFiles("/home/acerlaptop1/Desktop/MacBackup3_25_2025");
        String root_path = "/home/acerlaptop1/Desktop/MacBackup3_25_2025";

        Client client = new Client();
        Scanner scanner = new Scanner(System.in);
        String cliInput;

        System.out.println("Type 'exit' to quit.");

        while (!(cliInput = scanner.nextLine()).equals("exit")) {
            System.out.print("Vault> ");

            if (cliInput.equals("reindex")) { // Corrected string comparison
                System.out.println("Reindexing...");

                // Current number of rows 18235 rows
                indexFiles(root_path, client);
                continue; // Skips the rest of the loop iteration
            }

            if (cliInput.equals("reindex-hardreset")) {
                client.DELETE_TABLE_DATA();
                indexFiles(root_path, client);
                continue;
            }

            SearchData searchData = new SearchData(client);
            searchData.search(cliInput);
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
