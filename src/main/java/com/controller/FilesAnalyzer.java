package com.controller;
import org.apache.tika.Tika;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FilesAnalyzer {


    public List<AnalyzedFile> analyze(List<File> filesList){
        List<AnalyzedFile> analyzedFiles = new ArrayList<>();

        Iterator iterator = filesList.iterator();
        while(iterator.hasNext()){
            File file = (File) iterator.next();
            try {
                String type = new Tika().detect(file);
                AnalyzedFile uploadFile = new AnalyzedFile(file.getName(), file.length(), type);
                analyzedFiles.add(uploadFile);
            } catch(IOException e){
                e.printStackTrace();
            }
        }

        return analyzedFiles;
    }
}
