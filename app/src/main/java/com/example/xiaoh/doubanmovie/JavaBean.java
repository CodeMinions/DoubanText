package com.example.xiaoh.doubanmovie;

import java.util.List;

public class JavaBean {
    public static class Status {
        private int count;
        private int  start;
        private int total;

        private List<Movie> subjects;

        public List<Movie> getSubjects() {
            return subjects;
        }
    }
}
