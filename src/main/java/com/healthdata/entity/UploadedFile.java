package com.healthdata.entity;

public class UploadedFile {
    private Long id;
    private String name;
    private String cat;
    private String tag;
    private String year;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCat() { return cat; }
    public void setCat(String cat) { this.cat = cat; }
    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }
    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }
}

