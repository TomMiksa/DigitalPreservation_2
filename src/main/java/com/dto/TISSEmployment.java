package com.dto;

import java.util.ArrayList;
import java.util.List;

public class TISSEmployment {
    private List<String> emailAddresses;
    private String organizationalUnitId;
    private String organizationalUnitCode;
    private String organizationalUnitName;
    private String function;

    public TISSEmployment() {
        emailAddresses = new ArrayList<>();
    }

    public TISSEmployment(List<String> emailAddresses, String organizationalUnitId, String organizationalUnitCode,
                          String organizationalUnitName, String function) {

        this.emailAddresses = emailAddresses;
        this.organizationalUnitId = organizationalUnitId;
        this.organizationalUnitCode = organizationalUnitCode;
        this.organizationalUnitName = organizationalUnitName;
        this.function = function;
    }

    public List<String> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(List<String> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    public String getOrganizationalUnitId() {
        return organizationalUnitId;
    }

    public void setOrganizationalUnitId(String organizationalUnitId) {
        this.organizationalUnitId = organizationalUnitId;
    }

    public String getOrganizationalUnitCode() {
        return organizationalUnitCode;
    }

    public void setOrganizationalUnitCode(String organizationalUnitCode) {
        this.organizationalUnitCode = organizationalUnitCode;
    }

    public String getOrganizationalUnitName() {
        return organizationalUnitName;
    }

    public void setOrganizationalUnitName(String organizationalUnitName) {
        this.organizationalUnitName = organizationalUnitName;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public void addEmailAddress(String emailAddress){
        emailAddresses.add(emailAddress);
    }
}
