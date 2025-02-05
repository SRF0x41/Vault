package com.srf;

import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {
        Client client = new Client();
        try {
            //String dirSergio = "/Users/sergiorodriguez/Desktop";
            //FSearch searchAll = new FSearch(dirSergio);
            //FileAnalyzer fa = new FileAnalyzer();
            //searchAll.searchDir_toSQL(client, fa);
            Scanner scanner = new Scanner(System.in);
            UserSearch user = new UserSearch(client);
            String text_prompt = "";
            while (!text_prompt.equals("exit")) { 
                    text_prompt = scanner.nextLine();
                    user.userPrompt_fuzzySearch(text_prompt);
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            // Return gracefully
            client.close();
        }

               
               
        
    }
}
