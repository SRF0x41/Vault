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

public class UserSearch {

    private static final HashSet<String> stopWords = new HashSet<>();

    public UserSearch(){}

    public void userPrompt(String user_prompt){

    }

    public String[] pullKeywords(String prompt){
        HashMap<String,Integer> word_frequency = new HashMap<>();
        String[] words = prompt.split(" ");
        for(String w : words){
            w = w.toLowerCase();
            if(!stopWords.contains(w)){
                if(word_frequency.containsKey(w)){
                    word_frequency.put(w, word_frequency.get(w) + 1);
                }else{
                    word_frequency.put(w, 1);
                }
            }
        }

        return null;
    }

    private String sortHashMap_Frequency(HashMap<String,Integer> word_frequency){

        // Keep highest frequency words at the beggining
       List<Map.Entry<String, Integer>> list = new ArrayList<>(word_frequency.entrySet());

        // Sort the list in descending order based on values
        list.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        // Store the sorted entries in a LinkedHashMap to maintain order
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        StringBuilder keyword_string = new StringBuilder();
        int count = 0;
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
            keyword_string.append(entry.getKey()+" ");
            if(count == 100){
                break;
            }

        }
        
        return keyword_string.toString();
    }


    static {
        // Stop words hashset is used by all instances

        String filePath = "/Users/sergiorodriguez/Desktop/dev/git_repos/Vault/vault/src/main/java/com/srf/stop_words.txt"; 
        File file = new File(filePath);
        System.out.println("Looking for file at: " + file.getAbsolutePath());
        System.out.println("File exists: " + file.exists());

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
                //if (reader != null) {
                reader.close(); 
                //}
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        }
    }


}
