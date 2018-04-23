package com.dto;

import java.util.ArrayList;
import java.util.List;

public class TISSEmployee {
    private String tissId;
    private String firstName;
    private String lastName;
    private String gender;
    private String precedingTitle;
    private String postpositionedTitle;
    private String pictureURI;
    private String phoneNumber;
    private List employments;


    public TISSEmployee() {
        employments = new ArrayList<>();
    }

    public TISSEmployee(String tissId, String firstName, String lastName, String gender, String precedingTitle,
                        String postpositionedTitle, String pictureURI, String phoneNumber, List employments) {
        this.tissId=tissId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.precedingTitle = precedingTitle;
        this.postpositionedTitle = postpositionedTitle;
        this.pictureURI = pictureURI;
        this.phoneNumber = phoneNumber;
        this.employments = employments;
    }

    public String getTissId() {
        return tissId;
    }

    public void setTissId(String tissId) {
        this.tissId = tissId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPrecedingTitle() {
        return precedingTitle;
    }

    public void setPrecedingTitle(String precedingTitle) {
        this.precedingTitle = precedingTitle;
    }

    public String getPostpositionedTitle() {
        return postpositionedTitle;
    }

    public void setPostpositionedTitle(String postpositionedTitle) {
        this.postpositionedTitle = postpositionedTitle;
    }

    public String getPictureURI() {
        return pictureURI;
    }

    public void setPictureURI(String pictureURI) {
        this.pictureURI = pictureURI;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List getEmployments() {
        return employments;
    }

    public void setEmployments(List<TISSEmployment> employments) {
        this.employments = employments;
    }

    public void addEmployment(TISSEmployment employment){
        employments.add(employment);
    }
}
