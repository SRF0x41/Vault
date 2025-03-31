package com.srf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileAnalyzer {

    /*
     * SQL Table Structure:
     * This table stores file metadata for indexing and searching.
     * file_id: Unique identifier (auto-incremented)
     * file_size: Size of the file in bytes
     * file_name: Name of the file
     * file_extension: File extension (e.g., .txt, .jpg)
     * file_path: Full path of the file
     * file_keyword: Extracted keywords for search optimization
     */

    private static final HashSet<String> stopWords = new HashSet<>(); // Set to store common stop words

    static {
        // Static block to initialize stop words list from a file
        String filePath = "src/main/java/com/srf/stop_words.txt";
        File file = new File(filePath);
        System.out.println("Looking for file at: " + file.getAbsolutePath());
        System.out.println("File exists: " + file.exists());

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath)); // Open the file for reading
            String line;
            while ((line = reader.readLine()) != null) {
                stopWords.add(line); // Add each stop word to the set
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close(); // Close the reader
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public FileAnalyzer() {
        // Constructor (currently empty)
    }

   

    // Generates an SQL INSERT query string to store file attributes
    public String fileAtributes_toSQLQuery(Client client, File line) {
        long file_size = line.length(); // Get file size in bytes
        String file_path = line.getAbsolutePath().replace("'", "''"); // Escape single quotes
        String file_name = line.getName().replace("'", "''"); // Escape single quotes
        String file_extension = pullExtension(file_name); // Extract file extension
        String file_keywords = pullKeywords(line).replace("'", "''"); // Extract keywords

        String is_indexed_query = "SELECT EXISTS(SELECT 1 FROM file_index WHERE "
                + "file_size = " + file_size
                + " AND file_name = '" + file_name + "'"
                + " AND file_extension = '" + file_extension + "'"
                + " AND file_path = '" + file_path + "'"
                + " AND file_keywords = '" + file_keywords + "')";


        if(!client.queryForExistence(is_indexed_query)){
            String query = "INSERT INTO file_index (file_size, file_name, file_extension, file_path, file_keywords)";
            String values = "VALUES (" + file_size + ",'" + file_name + "','" + file_extension + "','" + file_path + "','"
                    + file_keywords + "')";
            return query + " " + values;
        }
        return null;


        // Construct SQL query
       
    }

    // Extracts keywords from a file and returns them as a string
    private String pullKeywords(File file_path) {
        if (!file_path.canRead()) {
            return ""; // Return empty if the file is not readable
        }
        HashMap<String, Integer> word_frequency = new HashMap<>(); // Stores word counts

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file_path));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(" "); // Split line into words
                for (String w : words) {
                    w = w.toLowerCase(); // Convert to lowercase
                    if (validKeyword(w)) { // Check if word is a valid keyword
                        word_frequency.put(w, word_frequency.getOrDefault(w, 0) + 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sortHashMap_Frequency(word_frequency);
    }

    // Determines if a word is a valid keyword for indexing
    private Boolean validKeyword(String word) {
        Boolean[] check_vals = {
                !stopWords.contains(word), // Not a stop word
                word.matches("^[\\x20-\\x7E]*$"), // Contains only printable characters
                word.length() <= 20, // Max length 20 characters
                word.length() >= 3 // Min length 3 characters
        };
        for (boolean b : check_vals) {
            if (!b) {
                return false;
            }
        }
        return true;
    }

    // Sorts words based on frequency and returns the top 100 as a string
    private String sortHashMap_Frequency(HashMap<String, Integer> word_frequency) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(word_frequency.entrySet());
        list.sort((a, b) -> b.getValue().compareTo(a.getValue())); // Sort in descending order

        StringBuilder keyword_string = new StringBuilder();
        int count = 0;
        for (Map.Entry<String, Integer> entry : list) {
            keyword_string.append(entry.getKey()).append(" "); // Append words to the string
            if (++count == 100) {
                break; // Limit to 100 keywords
            }
        }
        return keyword_string.toString();
    }

    // Extracts the file extension from the filename
    private String pullExtension(String name) {
        int dotIndex = name.lastIndexOf('.');
        if (dotIndex > 0) {
            return name.substring(dotIndex + 1); // Extract extension after last dot
        }
        return ""; // No extension found
    }

    // Converts file size from bytes to human-readable format (e.g., KB, MB, GB)
    private String convertBytes(long bytes) {
        double d_bytes = (double) bytes;
        String[] unitAbv = { "B", "KB", "MB", "GB", "TB", "PB" };
        int size = 0;
        while (d_bytes > 1000) {
            d_bytes /= 1024;
            size += 1;
        }
        if (d_bytes % 1 == 0) {
            return (int) d_bytes + " " + unitAbv[size];
        }
        return String.format("%.2f", d_bytes) + " " + unitAbv[size];
    }
}
