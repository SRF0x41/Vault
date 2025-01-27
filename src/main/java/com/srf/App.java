package com.srf;

import java.util.Scanner;


public class App 
{
    public static void main( String[] args )
    {
        Scanner password_input = new Scanner(System.in);
        System.out.println("Enter database password");
        String password = password_input.nextLine();


        Client client = new Client();
        client.setPassword(password);
        client.close();
    }
}
