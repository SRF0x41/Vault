package com.srf;

import java.io.File;

public class FSearch{
    public FSearch(){

    }

    public void traverseDirectory(String dir){
        File baseDir = new File(dir);
        traverseDir(baseDir);
    }

    private void traverseAll(){
        File rootDir = new File("/");
        traverseDir(rootDir);
    }

    private static void traverseDir(File dir) {
        // Check if the directory exists and is actually a directory
        if (dir != null && dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();

            if (files != null) {
                for (File file : files) {
                    // Print file/directory path
                    System.out.println(file.getAbsolutePath());

                    // If it's a directory, recurse into it
                    if (file.isDirectory()) {
                        traverseDir(file);
                    }
                }
            }
        }
    }
}