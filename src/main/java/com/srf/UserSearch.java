package com.srf;

import java.util.HashSet;
import java.util.LinkedHashMap;

public class UserSearch {

    private static final HashSet<String> stopWords = new HashSet<>();

    private final Client client;
    public UserSearch(Client c){
        client = c;
    }

    public void userPrompt_fuzzySearch(String user_prompt){
        TextTools tt = new TextTools();
        LinkedHashMap<String,Integer> keywords = tt.pullKeywords(user_prompt);

        for (String key : keywords.keySet()) {
            //System.out.println(key);
            client.fuzzySearch(key);
        }
    

    }

    


}
