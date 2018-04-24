package com.dto;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class AnalyzedFile {

    private String name;
    private Long size;
    private String type;
    private Integer quantity;
    private Boolean output;

    public AnalyzedFile() {
    }

    public AnalyzedFile(String name, Long size, String type, Integer quantity, Boolean output) {
        this.name = name;
        this.size = size;
        this.type = type;
        this.quantity = quantity;
        this.output = output;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getOutput() {
        return output;
    }

    public void setOutput(Boolean output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "AnalyzedFile{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", type='" + type + '\'' +
                ", quantity=" + quantity +
                ", output=" + output +
                '}';
    }
}
