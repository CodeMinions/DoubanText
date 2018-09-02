package com.example.xiaoh.doubanmovie.bean;

import java.util.List;

/**
 */

public class WeeklySubject  {
    public List<MySubject> subjects;
    public String title;

    public class MySubject{
        public String rank;
        public String delta;
        public MovieBean subject;
    }
}
