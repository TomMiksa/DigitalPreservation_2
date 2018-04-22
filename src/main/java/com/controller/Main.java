package com.controller;


import org.apache.tika.Tika;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class Main {

        private static final String RESOURCESPATH = "src/main/res/";
        private static FilesAnalyzer filesAnalyzer;
        private static TISSClient TISSClient;

        @RequestMapping("/")
        public String homepage() {

                return "homepage";

        }

        /*
        @RequestMapping(value = "getStationsVienna")
        @ResponseBody
        public List<Station> getStationVienna() {

            return dataProcessor.getStationsVienna();
        }

        @RequestMapping(value = "getStationsBudapest")
        @ResponseBody
        public List<Station> getStationBudapest() {

            return dataProcessor.getStationsBudapest();
        }*/

        public Main(){

        }

        public static void main(String[] args) {
            ArrayList<File> files = new ArrayList<>();

            File file1 = new File(RESOURCESPATH+"FILE1");
            File file2 = new File(RESOURCESPATH+"FILE2");
            File file3 = new File(RESOURCESPATH+"FILE3");

            files.add(file1);
            files.add(file2);
            files.add(file3);

            filesAnalyzer = new FilesAnalyzer();
            List<AnalyzedFile> analyzedFiles = filesAnalyzer.analyze(files);

            for (Object analyzedFile : analyzedFiles) {
                System.out.println(analyzedFile.toString());
            }


            TISSClient = new TISSClient();
            try {
                    List<TISSEmployee> searchedEmployes = TISSClient.sendGet("Lukas");
            } catch (Exception e) {
                    e.printStackTrace();
            }
        }
}
