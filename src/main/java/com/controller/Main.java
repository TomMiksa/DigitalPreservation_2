package com.controller;


import com.dto.AnalyzedFile;
import com.dto.Report;
import com.dto.Repository;
import com.dto.TISSEmployee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class Main {

        private static final String RESOURCESPATH = "src/main/res/";
        private static FilesAnalyzer filesAnalyzer;
        private static TISSClient TISSClient;
        private List<AnalyzedFile> analyzedFiles;
        private Report report = null;

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

        @RequestMapping(value="setFileOptions", method = RequestMethod.POST, consumes = {"application/json;charset=UTF-8"}, produces={"application/json;charset=UTF-8"})
        public @ResponseBody List<Repository> setFileOptions(@RequestBody List<AnalyzedFile> files){
            List<String> mimeTypes = new ArrayList<>();
            for (AnalyzedFile analyzedFile: files) {
                if(analyzedFile.getOutput()) {
                    if (mimeTypes.isEmpty() || !mimeTypes.contains(analyzedFile.getType())) {
                        mimeTypes.add(analyzedFile.getType());
                    }
                }
            }
            RepositoryFinder repositoryFinder = new RepositoryFinder();
            try {
                return repositoryFinder.sendGet(mimeTypes);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }



        @RequestMapping(value="setReport", method = RequestMethod.POST, consumes = {"application/json;charset=UTF-8"}, produces={"application/json;charset=UTF-8"})
        public @ResponseBody Report setReport(@RequestBody Report report){

            this.report = report;
            System.out.println(report.toString());
            return report;
        }


        @RequestMapping(value="getReport", method = RequestMethod.POST, produces={"application/json;charset=UTF-8"})
        public @ResponseBody Report getReport(){

            return report;
        }

        @RequestMapping(value = "doUpload", method = RequestMethod.POST)
        public @ResponseBody List<AnalyzedFile> doUpload(@RequestParam("files") MultipartFile[] files) {

            File dir = new File(RESOURCESPATH);
            dir.mkdir();


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


            ArrayList<File> filesInDirectory = new ArrayList<>(Arrays.asList(new File(RESOURCESPATH).listFiles()));

            filesAnalyzer = new FilesAnalyzer();
            analyzedFiles = filesAnalyzer.analyze(filesInDirectory);

            return analyzedFiles;
        }

        public Main(){

        }

        public static void main(String[] args) {
        }
}
