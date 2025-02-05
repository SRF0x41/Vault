package com.srf;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FSearch{
    private File base_dir;
    public FSearch(String file_path){
        base_dir = new File(file_path);
    }


    public List<File> traverseDirectory(String dir){
        File baseDir = new File(dir);
        List<File> allFiles = new ArrayList<>();
        traverseDir(baseDir, allFiles);
        return allFiles;
    }

    private void traverseAll(){
        File rootDir = new File("/");
        List<File> allFiles = new ArrayList<>();
        traverseDir(rootDir, allFiles);
    }

    public void searchDir_toSQL(Client client, FileAnalyzer fa){
        traverseDir_toSQL(base_dir, client, fa);
    }
    private static void traverseDir_toSQL(File dir, Client client, FileAnalyzer fa) {

        // Check if the directory exists and is actually a directory
        if (dir != null && dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    // Print file/directory path
                    if(file.isFile()){
                        String query = fa.fileAtributes_toSQLQuery(file);
                        client.sendQuery_Update(query);
                        System.out.println(file.getAbsolutePath());
                    }
                    // If it's a directory, recurse into it
                    if (file.isDirectory()) {
