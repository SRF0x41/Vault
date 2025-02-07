package com.srf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserSearch {

    private final Client client;
    public QuerySet q_set;
    

    public UserSearch(Client c) {
        client = c;
    }

    public QuerySet userPrompt_fuzzySearch(String user_prompt) {
        TextTools tt = new TextTools();
        String[] keywords = tt.pullKeywords(user_prompt);
        q_set = client.fuzzySearch(user_prompt);
        return q_set;
    }

    public TreeMap<Integer,ArrayList<Object>> hfq_