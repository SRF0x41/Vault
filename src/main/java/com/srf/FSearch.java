package com.srf;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FSearch{
    

    public FSearch(){

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

    private static void traverseDir(File dir, List<File> all_files) {
        // Check if the directory exists and is actually a directory
        if (dir != null && dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            List<File> asList = Arrays.asList(files);
            all_files.addAll(asList);
            

            if (files != null) {
                for (File file : files) {
                    // Print file/directory path
                    // System.out.println(file.getAbsolutePath());

                    // If it's a directory, recurse into it
                    if (file.isDirectory()) {
                        traverseDir(file, all_files);
                    }
                }
            }
        }
    }
}