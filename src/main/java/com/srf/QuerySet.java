package com.srf;

import java.util.ArrayList;
import java.util.HashMap;

public class QuerySet {

    /*
     * This is the new data structure for query results
     * all query results will be added here
     */

    ArrayList<ArrayList<Object>> master_set = new ArrayList<>();
    HashMap<Integer, QuerySet> frequency_master_set = new HashMap<>();

    public QuerySet() {
        /*
         * A query set contains an ArrayList of ArrayLists of objects, the inner
         * arraylist
         * contains all the elements pulled from a query
         */

        /*
         * For a data structure that puts hights keyword frequency
         * in the front maybe theres no need to sort.
         * maybe a hashmap that contrains query sets that contain the same frequency
         * for example key = 4 will contain all the query sets that
         * had 4 hits.
         * 
         * Then think about sorting the query set with 4 hits.
         */
    }

    public QuerySet(ArrayList<ArrayList<Object>> set) {
        master_set = set;
    }

    public void add(ArrayList<Object> c_line) {
        master_set.add(c_line);
    }

    public void add(int key, QuerySet q_set) {

    }

    public ArrayList<ArrayList<Object>> getMasterSet() {
        return master_set;
    };

    public void print() {
        for (ArrayList<Object> e : master_set) {
            System.out.println(e.toString());
        }
    }
}
