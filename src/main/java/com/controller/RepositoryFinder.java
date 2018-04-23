package com.controller;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class RepositoryFinder {

    // HTTP GET request
    public Map<String,String> sendGet(Map<String,Integer> mimeTypes) throws Exception {
        Map<String, String> repositoriesUrls = new HashMap<>();
        String continent = "Europe";
        String country = "xx";

        //search (still TODO: add continent and mimeTypes to the query)
        String url = "http://opendoar.org/api13.php?";
        url += "co=" + country /*+ "," + continent*/;
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);

        // Get the response
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            stringBuilder.append(line);
        }
        JSONObject json = XML.toJSONObject(String.valueOf(stringBuilder));
        if(json.getJSONObject("OpenDOAR").has("repositories") &&
                !json.getJSONObject("OpenDOAR").isNull("repositories") && json.getJSONObject("OpenDOAR").get("repositories")!=""){
            JSONArray jsonArray = json.getJSONObject("OpenDOAR").getJSONObject("repositories").getJSONArray("repository");
            int count = 3;
            if(jsonArray.length()<count){
                count= jsonArray.length();
            }
            for(int i=0; i<count; i++){
                JSONObject repo = (JSONObject) jsonArray.get(i);
                repositoriesUrls.put(repo.getString("rName"), repo.getString("oUrl"));
            }
        }

        return repositoriesUrls;
    }
}
