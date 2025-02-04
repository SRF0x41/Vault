package com.srf;

public class App 
{
    public static void main( String[] args )
    {
        Client client = new Client();
        try {
            String dirSergio = "/Users/sergiorodriguez/Desktop";
            FSearch searchAll = new FSearch(dirSergio);
            FileAnalyzer fa = new FileAnalyzer();
            searchAll.searchDir_toSQL(client, fa);
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            // Return gracefully
            client.close();
        }
    }
}
