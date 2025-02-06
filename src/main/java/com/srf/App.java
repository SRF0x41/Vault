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
        // search tooling
        Client client = new Client();
        UserSearch user_search = new UserSearch(client);

        float phi = (float) 1.618;
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Vault 1.0.0"); // UI created on the main thread
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1024, 632);
            frame.add(new JLabel("Vault search"));
            frame.setVisible(true);
            frame.setLayout(new FlowLayout());

            // Create a text field with a width of 20 columns
            JTextField textField = new JTextField(20);

            // Create a button to retrieve the text
            JButton button = new JButton("Search");
            int height = 40;
            button.setPreferredSize(new Dimension((int) (height * phi), height));

            JLabel label = new JLabel("Type something: ");

            // Text area
            JTextArea search_results = new JTextArea("This is some printed text.\nHere is another line.");
            search_results.setEditable(false); // Make it non-editable
            int text_results_width = 800;
            int text_results_height = 500;
            search_results.setPreferredSize(new Dimension(text_results_width, text_results_height));

            button.addActionListener(e -> {
                String text = textField.getText(); // Get text from the field
                // user_search.userPrompt_fuzzySearch(text);
                user_search.userPrompt_hfq_Search(text);
                SwingUtilities.invokeLater(() -> {
                    // search_results.setText(user_search.longLineText());
                });
            });
            frame.add(label);
            frame.add(textField);
            frame.add(button);

            frame.add(new JScrollPane(search_results));

        });

    }

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
