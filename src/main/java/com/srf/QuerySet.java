package com.srf;

import java.util.ArrayList;

public class QuerySet {

    /* This is the new data structure for query results
     * all query results will be added here
     */


    ArrayList<ArrayList<Object>> master_set = new ArrayList<>();


    public QuerySet(){

    }
    public QuerySet(ArrayList<ArrayList<Object>> set){
        master_set = set;
    }
    
    public void add(ArrayList<Object> c_line){
        master_set.add(c_line);
    }

    public void print(){
        for(ArrayList<Object> e : master_set){
            System.out.println(e.toString());
        }
    }
}
