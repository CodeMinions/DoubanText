package com.example.xiaoh.doubanmovie.bean;

import java.util.List;

/**
 * 电影实体类
 */

public class MovieBean {
    public Rating rating;
    public String title;
    public int collect_count;
    public String original_title;
    public String subtype;
    public String year;
    public Avatars images;
    public String alt;
    public String id;
    public String mainland_pubdate;
    public boolean has_video;
    public List<String> pubdates;
    public List<String> durations;
    public List<String> genres;
    public List<Casts> casts;
    public List<Directors> directors;
}
