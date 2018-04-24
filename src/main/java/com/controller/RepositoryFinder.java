package com.controller;

import com.dto.Repository;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RepositoryFinder {

    // HTTP GET request
    // repositories from AT are preferred over other european repositories
    public List<Repository> sendGet(List<String> mimeTypes) throws Exception {
        List<Repository> repositories = new ArrayList<>();
        String country = "at";
        String contentTypes = "";
        if(mimeTypes!= null && !mimeTypes.isEmpty()) {
            country+=",al,by,be,ba,bg,hr,cy,cz,dk,ee,fi,fr,de,gr,hu,is,ie,it,lv," +
                    "lt,lu,mk,mt,md,nl,no,pl,pt,ro,ru,rs,si,es,se,ch,ua,gb";
            if (mimeTypes.toString().contains("image") || mimeTypes.toString().contains("audio") ||
                    mimeTypes.toString().contains("video")) {
                contentTypes += "11";
            }
            if (mimeTypes.toString().contains("text") || mimeTypes.toString().contains("application")) {
                if (contentTypes.equals("")) {
                    contentTypes += "1,9,12";
                } else {
                    contentTypes += ",1,9,12";
                }
            }
        }

        String url = "http://opendoar.org/api13.php?";
        url += "co=" + country;
        url += "&ct=" + contentTypes;
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
            //take maximum 3 repositories
            int count = 3;
            if(jsonArray.length()<count){
                count= jsonArray.length();
            }
            //first check if all three can be from AT
            for(int i=0; i<jsonArray.length(); i++){
                if(count>0){
                    JSONObject repo = (JSONObject) jsonArray.get(i);
                    if(repo.getJSONObject("country").getString("cIsoCode").equals("AT")){
                        repositories.add(new Repository(repo.getString("rName"),repo.getString("oUrl")));
                        count--;
                    }
                }
            }
            //if no three repos from AT found, take also from other countries
            if(count>0){
                for(int i=0; i<jsonArray.length(); i++){
                    if(count>0){
                        JSONObject repo = (JSONObject) jsonArray.get(i);
                        if(!repo.getJSONObject("country").getString("clsoCode").equals("AT")){
                            repositories.add(new Repository(repo.getString("rName"),repo.getString("oUrl")));
                            count--;
                        }
                    }
                }
            }
        }

        return repositories;
    }
}
