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

public class TextTools {
    private static final HashSet<String> stopWords = new HashSet<>();

    final int MINIMUM_KEYWORD_LENGTH = 3;
    final int MAXIMUM_KEYWORD_LENGTH = 20;

    static {
        // Stop words hashset is used by all instances

        String filePath = "/Users/sergiorodriguez/Desktop/dev/git_repos/Vault/vault/src/main/java/com/srf/stop_words.txt";
        File file = new File(filePath);

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath)); // Open the file for reading
            String line;
            while ((line = reader.readLine()) != null) {
                stopWords.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // if (reader != null) {
                reader.close();
                // }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public TextTools() {

    }

    public String[] pullKeywords(String line) {
        HashMap<String, Integer> word_frequency = new HashMap<>();
        line = line.toLowerCase();
        String[] words = line.split(" ");
        for (String w : words) {
            if (word_frequency.containsKey(w) && validateKeyword(w)) {
                word_frequency.put(w, word_frequency.get(w) + 1);
            } else {
                word_frequency.put(w, 1);
            }
        }

        // Keep highest frequency words at the beggining
        List<Map.Entry<String, Integer>> list = new ArrayList<>(word_frequency.entrySet());

        // Sort the list in descending order based on values
        list.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        // Store the sorted entries in a LinkedHashMap to maintain order
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        String[] key_words = new String[sortedMap.size()];
        int count = 0;
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
            key_words[count]= entry.getKey();
        }

        return key_words;
    }

    // Validate keyword
    private Boolean validateKeyword(String word) {
        Boolean[] checks = {
                !stopWords.contains(word),
                word.length() >= MINIMUM_KEYWORD_LENGTH,
                word.length() <= MAXIMUM_KEYWORD_LENGTH,
                word.matches("^[\\x20-\\x7E]*$")
        };
        for (Boolean b : checks) {
            if (!b) {
                return false;
            }
        }
        return true;
    }

    // sort keywords
}
