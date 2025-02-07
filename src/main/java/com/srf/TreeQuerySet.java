package com.srf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

public class TreeQuerySet {
    TreeMap<Integer, ArrayList<Object>> tree_qset = new TreeMap<>(Collections.reverseOrder());
    public TreeQuerySet(){

    }
    
    public void add(int freq, ArrayList<Object> q_set){
        tree_qset.put(freq, q_set);
    }

    

}
