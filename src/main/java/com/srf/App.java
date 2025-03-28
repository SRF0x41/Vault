package com.srf;

import java.awt.Dimension;
import java.awt.FlowLayout;

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
        Client test_client = new Client();
        

    }


    // Simple function that populates the sql databse
    public void populateSQL(Client client) {

        try {
            String dirSergio = "/Users/sergiorodriguez/Desktop";
            FSearch searchAll = new FSearch(dirSergio);
            FileAnalyzer fa = new FileAnalyzer();
            searchAll.searchDir_toSQL(client, fa);

        } catch (Exception e) {
            System.err.println(e);
        } finally {
            // Return gracefully
            client.close();
        }
    }
}
