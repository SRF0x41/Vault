package com.srf;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FSearch {
    private File base_dir;

    public FSearch(String file_path) {
        base_dir = new File(file_path);
    }

    public List<File> traverseDirectory(String dir) {
        File baseDir = new File(dir);
        List<File> allFiles = new ArrayList<>();
        traverseDir(baseDir, allFiles);
        return allFiles;
    }

    private void traverseAll() {
        /* This traverses all the files in the system that are visible */
        File rootDir = new File("/");
        List<File> allFiles = new ArrayList<>();
        traverseDir(rootDir, allFiles);
    }

    public void searchDir_toSQL(Client client, FileAnalyzer fa) {
        traverseDir_toSQL(base_dir, client, fa);
    }

    private static void traverseDir_toSQL(File dir, Client client, FileAnalyzer fa) {
        /*
         * Recursivly search through all the files in a given root dir. Will populate
         * the sql database
         */

        // Check if the directory exists and is actually a directory
        if (dir != null && dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    // Check file or path
                    if (file.isFile()) {
                        String query = fa.fileAtributes_toSQLQuery(file);
                        client.sendQuery_Update(query);
                        System.out.println(file.getAbsolutePath());
                    }
                    // If it's a directory, recurse into it
                    if (file.isDirectory()) {
                        traverseDir_toSQL(file, client, fa);
                    }
                }
            }
        }
    }

    private static void traverseDir(File dir, List<File> all_files) {
        // Check if the directory exists and is actually a directory
        if (dir != null && dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            List<File> asList = Arrays.asList(files);
            all_files.addAll(asList);

            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        traverseDir(file, all_files);
                    }
                }
            }
        }
    }
}