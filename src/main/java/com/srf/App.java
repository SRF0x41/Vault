package com.srf;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        // Reliable database and file searching
        // indexFiles("/home/acerlaptop1/Desktop/MacBackup3_25_2025");



        
        Client client = new Client();
        Scanner scanner = new Scanner(System.in);
        String cliInput;

        System.out.println("Type 'exit' to quit.");
        
        while (!(cliInput = scanner.nextLine()).equals("exit")) {
            System.out.println("Search:");
            SearchData searchData = new SearchData(client);
            searchData.search(cliInput);
        }

        client.close();
        scanner.close();
        

    }

    public static void indexFiles(String root_path) {
        Client client = new Client();
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
