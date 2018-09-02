package com.example.xiaoh.doubanmovie;

import java.util.List;

public class PSData {//PopularShowData ,用于json解析（即将上映中的json解析）
    private int code;
    private int start;
    private int total;
    private List<ChlMovie> subjects;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ChlMovie> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<ChlMovie> subjects) {
        this.subjects = subjects;
    }
}
