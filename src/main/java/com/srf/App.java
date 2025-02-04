package com.srf;

import java.io.File;

public class App 
{
    public static void main( String[] args )
    {
        Client client = new Client();
        try {
            FSearch searchAll = new FSearch();
            File dirSergio = new File("/Users/sergiorodriguez/Desktop");
            FileAnalyzer fa = new FileAnalyzer();
            searchAll.searchDir_toSQ