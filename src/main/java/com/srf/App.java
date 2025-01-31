package com.srf;


public class App 
{
    public static void main( String[] args )
    {
        Client client = new Client();
        client.close();

        FSearch searchAll = new FSearch();
        searchAll.traverseAll();
    }
}
