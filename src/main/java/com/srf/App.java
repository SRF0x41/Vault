package com.srf;


public class App 
{
    public static void main( String[] args )
    {
        Client client = new Client();
        client.close();

        FSearch searchAll = new FSearch();
        //searchAll.traverseDirectory(args[0]);
        searchAll.traverseDirectory("/Users/sergiorodriguez/Desktop");


        //client.sendQuery("SELECT * FROM fileIndex");

        FileAnalyzer fa = new FileAnalyzer();
        for(int i = 0; i < 10; i++){
            long min = 0;
            long max = (long) Math.pow(10, 15);  // 10^15
    
            // Generate random long between 0 and 10^15
            long randomValue = (long) (Math.random() * (max - min)) + min;
    
            // Print the result
            System.out.println("Random long between 0 and 10^15: " + randomValue);
            System.out.println(fa.convertBytes(randomValue));
        }



    }
}
