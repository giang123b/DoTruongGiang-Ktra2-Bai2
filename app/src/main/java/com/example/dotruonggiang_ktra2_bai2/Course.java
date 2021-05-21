package com.example.dotruonggiang_ktra2_bai2;

public class Course {

    private String id;
    private String name;
    private String date;
    private String major;
    private String active;

    public Course(String id, String name, String date, String major, String active) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.major = major;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
