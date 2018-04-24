package com.controller;


import com.dto.AnalyzedFile;
import com.dto.TISSEmployee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

        @RequestMapping(value="searchEmployee", method = RequestMethod.POST)
        public @ResponseBody List<TISSEmployee> searchEmployee(@RequestParam("name") String name){

            List<TISSEmployee> searchedEmployes = null;

            TISSClient = new TISSClient();
            try {
                searchedEmployes = TISSClient.sendGet(name);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return searchedEmployes;
        }

        @RequestMapping(value = "doUpload", method = RequestMethod.POST)
        public String doUpload(@RequestParam("files") MultipartFile[] files) {

            for (MultipartFile file : files) {
                if (!file.getOriginalFilename().isEmpty()) {

                    try {
                        System.out.println("uploading...");
                        BufferedOutputStream outputStream = new BufferedOutputStream(
                                new FileOutputStream(
                                        new File(RESOURCESPATH, file.getOriginalFilename())));

                        outputStream.write(file.getBytes());
                        outputStream.flush();
                        outputStream.close();
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("empty file");
                }
            }



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
            List<String> mimeTypes = new ArrayList<>();

            for (AnalyzedFile analyzedFile : analyzedFiles) {
                System.out.println(analyzedFile.toString());
                mimeTypes.add(analyzedFile.getType());
            }


            TISSClient = new TISSClient();
            try {
                    List<TISSEmployee> searchedEmployes = TISSClient.sendGet("Lukas");
            } catch (Exception e) {
                    e.printStackTrace();
            }

            RepositoryFinder repositoryFinder = new RepositoryFinder();
            try {
                repositoryFinder.sendGet(mimeTypes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
