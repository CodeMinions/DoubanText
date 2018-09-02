package com.example.xiaoh.doubanmovie;

import android.support.annotation.NonNull;

import java.util.List;

public class Movie {
    private String title;
    private String id;
    private Images images;
    private String year;
    private List<String> genres;
    private List<String> countries;
    private Rating rating;
    private int ratings_count;
    private String summary;
    private List<Actor> casts;
    private List<Actor> directors;

    /**
     * likeMovie键名
     */
    private String name;
    private String img;
    private int doubanId;


    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public int getDoubanId() {
        return doubanId;
    }

    public List<Actor> getDirectors() {
        return directors;
    }

    public List<Actor> getCasts() {
        return casts;
    }

    public String getSummary() {
        return summary;
    }

    public int getRatings_count() {
        return ratings_count;
    }

    public Rating getRating() {
        return rating;
    }

    public List<String> getCountries() {

        return countries;
    }

    public List<String> getGenres() {

        return genres;
    }

    public Movie(String s){
        title = s;
    }

    public Images getImages() {
        return images;
    }

    public String getId() {

        return id;
    }

    public String getYear() {

        return year;
    }

    public String getTitle() {

        return title;
    }

    public class Images {
        private String small;
        private String large;
        private String medium;

        public String getMedium() {
            return medium;
        }

        public String getLarge() {

            return large;
        }

        public String getSmall() {

            return small;
        }
    }

    public class Rating{
        Float average;

        public Float getAverage() {

            return average;
        }

        public void setAverage(Float average) {
            this.average = average;
        }
    }

    public class Actor{
        private String name;
        private Pic avatars;

        class Pic{
            String medium;
            public String getMedium() {
                return medium;
            }
        }
        public Pic getAvatars() {
            return avatars;
        }

        public String getName() {
            return name;
        }
    }

    public class Comment{

        String name;
        String text;
        String time;
        String votes;
        String avatar;

        public String getAvatar() {
            return avatar;
        }

        public String getVotes() {
            return votes;
        }

        public String getTime() {
            return time;
        }

        public String getText() {
            return text;
        }
        public String getName() {
            return name;
        }
    }
}
