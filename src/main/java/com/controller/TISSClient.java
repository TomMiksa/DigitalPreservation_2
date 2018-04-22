package com.controller;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TISSClient {

    private final String USER_AGENT = "Mozilla/5.0";

    // HTTP GET request
    public List<TISSEmployee> sendGet(String name) throws Exception {
        List<TISSEmployee> searchedEmployees = new ArrayList<>();

        //search by name
        String url = "https://tiss.tuwien.ac.at/api/person/v21/psuche?q=";
        url +=name.replace(" ","%20");
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
        JSONObject json = new JSONObject(stringBuilder.toString());
        JSONArray jsonArray = json.getJSONArray("results");
        for (int i=0; i < jsonArray.length(); i++) {
            String id = String.valueOf(jsonArray.getJSONObject(i).getInt("id"));
            //search again by id
            String url2 = "https://tiss.tuwien.ac.at/api/person/v21/id/";
            url2+=id;
            request = new HttpGet(url2);
            response = client.execute(request);

            // Get the response
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            stringBuilder = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                stringBuilder.append(line);
            }
            json = XML.toJSONObject(String.valueOf(stringBuilder));
            TISSEmployee employee = new TISSEmployee();
            employee.setTissId(id);
            JSONObject information = json.getJSONObject("tuvienna").getJSONObject("person");
            if(information.has("firstname") && !information.isNull("firstname")
                    && !information.getString("firstname").equals("")){
                employee.setFirstName(information.getString("firstname"));
            }
            if(information.has("lastname") && !information.isNull("lastname")
                    && !information.getString("lastname").equals("")){
                employee.setLastName(information.getString("lastname"));
            }
            if(information.has("gender") && !information.isNull("gender")
                    && !information.getString("gender").equals("")){
                employee.setGender(information.getString("gender"));
            }
            if(information.has("preceding_titles") && !information.isNull("preceding_titles")
                    && !information.getString("preceding_titles").equals("")){
                employee.setPrecedingTitle(information.getString("preceding_titles"));
            }
            if(information.has("postpositioned_titles") && !information.isNull("postpositioned_titles")
                    && !information.getString("postpositioned_titles").equals("")){
                employee.setPostpositionedTitle(information.getString("postpositioned_titles"));
            }
            if(information.has("picture_uri") && !information.isNull("picture_uri")
                    && !information.getString("picture_uri").equals("")){
                employee.setPictureURI(information.getString("picture_uri"));
            }
            if(information.has("main_phone_number") && !information.isNull("main_phone_number")
                    && !information.getString("main_phone_number").equals("")){
                employee.setPhoneNumber(information.getString("main_phone_number"));
            }
            if(information.has("employee") && !information.isNull("employee")) {
                try {
                    //Only one employment
                    JSONObject employeeInformation = information.getJSONObject("employee").getJSONObject("employment");
                    TISSEmployment employment = new TISSEmployment();
                    if (employeeInformation.has("function") && !employeeInformation.isNull("function")) {
                        employment.setFunction(employeeInformation.getString("function"));
                    }
                    if (employeeInformation.has("emails") && !employeeInformation.isNull("emails")) {
                        JSONArray emailsJSONArray = employeeInformation.getJSONObject("emails").getJSONArray("tiss:email");
                        for (int j = 0; j < emailsJSONArray.length(); j++) {
                            String content = emailsJSONArray.getJSONObject(j).getString("content");
                            if (!employment.getEmailAddresses().contains(content)) {
                                employment.addEmailAddress(content);
                            }
                        }
                    }
                    if (employeeInformation.has("organisational_unit") && !employeeInformation.isNull("organisational_unit")) {
                        employment.setOrganizationalUnitId(String.valueOf(employeeInformation.getJSONObject("organisational_unit").getInt("tiss_id")));
                        employment.setOrganizationalUnitCode(employeeInformation.getJSONObject("organisational_unit").getString("code"));
                        employment.setOrganizationalUnitName(employeeInformation.getJSONObject("organisational_unit").getString("content"));
                    }
                    employee.addEmployment(employment);
                } catch (JSONException e) {
                    //More employments
                    JSONArray employeeInformations = information.getJSONObject("employee").getJSONArray("employment");
                    for (int j = 0; j < employeeInformations.length(); j++) {
                        JSONObject employeeInformation = (JSONObject) employeeInformations.get(j);
                        TISSEmployment employment = new TISSEmployment();
                        if (employeeInformation.has("function") && !employeeInformation.isNull("function")) {
                            employment.setFunction(employeeInformation.getString("function"));
                        }
                        if (employeeInformation.has("emails") && !employeeInformation.isNull("emails")) {
                            JSONArray emailsJSONArray = employeeInformation.getJSONObject("emails").getJSONArray("tiss:email");
                            for (int k = 0; k < emailsJSONArray.length(); k++) {
                                String content = emailsJSONArray.getJSONObject(k).getString("content");
                                if (!employment.getEmailAddresses().contains(content)) {
                                    employment.addEmailAddress(content);
                                }
                            }
                        }
                        if (employeeInformation.has("organisational_unit") && !employeeInformation.isNull("organisational_unit")) {
                            employment.setOrganizationalUnitId(String.valueOf(employeeInformation.getJSONObject("organisational_unit").getInt("tiss_id")));
                            employment.setOrganizationalUnitCode(employeeInformation.getJSONObject("organisational_unit").getString("code"));
                            employment.setOrganizationalUnitName(employeeInformation.getJSONObject("organisational_unit").getString("content"));
                        }
                        employee.addEmployment(employment);
                    }
                }
            }
            searchedEmployees.add(employee);
        }
        return searchedEmployees;
    }
}
