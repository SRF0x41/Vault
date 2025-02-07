package com.srf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class QuerySet {

    /*
     * This is the new data structure for query results
     * all query results will be added here
     */

    ArrayList<ArrayList<Object>> master_set = new ArrayList<>();
    HashMap<Integer, ArrayList<Object>> frequency_master_set = new HashMap<>();

    public QuerySet() {
        /*
         * A query set contains an ArrayList of ArrayLists of objects, the inner
         * arraylist
         * contains all the elements pulled from a query
         */

    