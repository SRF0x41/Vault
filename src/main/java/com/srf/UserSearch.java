package com.srf;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class UserSearch {

    private final Client client;
    public QuerySet q_set;
    public UserSearch(Client c){
        client = c;
    }

    public QuerySet userPrompt_fuzzySearch(String user_prompt){
        TextTools tt = new TextTools();
        LinkedHashMap<String,Integer> keywords = tt.pullKeywords(user_prompt);
        q_set = client.fuzzySearch(user_prompt);
        return q_set;
    }

    public QuerySet userPrompt_hfq_Search(String user_prompt){
        TextTools tt = new TextTools();

        // Buncha nonsen