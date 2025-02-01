package com.srf;


public class App 
{
    public static void main( String[] args )
    {
        Client client = new Client();
        client.close();

        FSearch searchAll = new FSearch();
        //searchAll.traverseDirectory(args[0]);
        System.err.println(searchAll.traverseDirectory("/Users/sergiorodriguez/Desktop"));
    }
}
