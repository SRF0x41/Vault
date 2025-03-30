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
        // indexFiles("/home/acerlaptop1/Desktop/");


        System.out.println("Hello world");

        // Client client = new Client();
        // String cli_input = "";
        // do{
        //     Scanner scanner = new Scanner(System.in);
        //     System.out.println("Search:");
        //     cli_input = scanner.nextLine();

        //     SearchData search_data = new SearchData(client);
        //     search_data.search(cli_input);

            
        // }while(cli_input.equals("exit"));
        

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
