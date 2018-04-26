package com.dto;

import java.util.List;

public class Report {

    private String projectTitle;
    private String name;
    private String repository;
    private String license;
    private List<AnalyzedFile> files;

    public Report() {
    }

    public Report(String projectTitle, String name, String repository, String license, List<AnalyzedFile> files) {
        this.projectTitle = projectTitle;
        this.name = name;
        this.repository = repository;
        this.license = license;
        this.files = files;
    }

    public List<AnalyzedFile> getFiles() {
        return files;
    }

    public void setFiles(List<AnalyzedFile> files) {
        this.files = files;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    @Override
    public String toString() {
        return "Report{" +
                "projectTitle='" + projectTitle + '\'' +
                ", name='" + name + '\'' +
                ", repository='" + repository + '\'' +
                ", license='" + license + '\'' +
                ", files=" + files +
                '}';
    }
}
