package com.controller;


import com.dto.AnalyzedFile;
import com.dto.Report;
import com.dto.Repository;
import com.dto.TISSEmployee;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class Main {

        private static final String RESOURCESPATH = "src/main/res/";
        private static FilesAnalyzer filesAnalyzer;
        private static TISSClient TISSClient;
        private List<AnalyzedFile> analyzedFiles;
        private List<TISSEmployee> searchedEmployes = new ArrayList<>();
        private List<Repository> repositories = new ArrayList<>();
        private Report report = null;

        @RequestMapping("/")
        public String homepage() {

                return "homepage";
        }

        @RequestMapping(value="searchEmployee", method = RequestMethod.POST)
        public @ResponseBody List<TISSEmployee> searchEmployee(@RequestParam("name") String name){

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
                repositories = repositoryFinder.sendGet(mimeTypes);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return repositories;
        }



    @RequestMapping(value="setReport", method = RequestMethod.POST, consumes = {"application/json;charset=UTF-8"}, produces={"application/json;charset=UTF-8"})
    public @ResponseBody Report setReport(@RequestBody Report report){
            JSONObject finalJson = new JSONObject();

            String tissID = report.getName().replaceAll("\\D+","");
            TISSEmployee creator = new TISSEmployee();
            Repository repository = new Repository();
            for (TISSEmployee employee:searchedEmployes) {
                if(employee.getTissId().equals(tissID)){
                    creator.setFirstName(employee.getFirstName());
                    creator.setLastName(employee.getLastName());
                    creator.setGender(employee.getGender());
                    creator.setTissId(employee.getTissId());
                    creator.setPictureURI(employee.getPictureURI());
                    creator.setPrecedingTitle(employee.getPrecedingTitle());
                    creator.setPostpositionedTitle(employee.getPrecedingTitle());
                    creator.setPhoneNumber(employee.getPhoneNumber());
                    creator.setEmployments(employee.getEmployments());
                    break;
                }
            }

            for(Repository repo: repositories){
                if(repo.getName().equals(report.getRepository().split(": ")[0])){
                    repository.setName(repo.getName());
                    repository.setUrl(repo.getUrl());
                    break;
                }
            }

            JSONObject creatorJsonObject = new JSONObject(creator);
            JSONObject repositoryJsonObject = new JSONObject(repository);
            finalJson.put("projectTitle", report.getProjectTitle());
            finalJson.put("creator",creatorJsonObject);
            finalJson.put("date", LocalDateTime.now());
            finalJson.put("license", report.getLicense());
            finalJson.put("repository", repositoryJsonObject);

            JSONArray array = new JSONArray();
            for (AnalyzedFile analyzedFile: report.getFiles()) {
                array.put(new JSONObject(analyzedFile));
            }

            finalJson.put("sampleFiles", array);


            File dir = new File("src/main/output");
            dir.mkdir();

            try{
                // Create a new FileWriter object
                FileWriter fileWriter = new FileWriter("src/main/output/output.json");

                // Writting the jsonObject into sample.json
                fileWriter.write(finalJson.toString());
                fileWriter.close();
            }catch (Exception e){
                System.err.println("Error: " + e);
            }

            this.report = report;
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
