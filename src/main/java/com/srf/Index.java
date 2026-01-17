package com.srf;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Index {

    private Client db_client;
    private FileAnalyzer file_analyser = new FileAnalyzer();
    public Index(Client db_client) {
        this.db_client = db_client;
    }

    public void indexFiles(String root_path) {
        File base_directory = new File(root_path);
        List<File> extracted_files = new ArrayList<>();
        traverse_directory(base_directory, extracted_files);
    }

    private void traverse_directory(File dir, List<File> extracted_files) {
        if (dir != null && dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();

            List<File> asList = Arrays.asList(files);
            extracted_files.addAll(asList);

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        
                        System.out.println(file.getAbsolutePath());
                    }
                    if (file.isDirectory()) {
                        traverse_directory(file, extracted_files);
                    }
                }
            }
        }
    }

}
