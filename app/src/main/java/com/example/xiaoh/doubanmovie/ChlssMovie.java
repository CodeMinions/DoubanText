package com.example.xiaoh.doubanmovie;

import android.support.annotation.NonNull;

public class ChlssMovie implements Comparable {//json数据解析类
    private long id ;
    private int doubanId;
    private String avatar;
    private String name;
    private String date;
    private String label;
    private String area;
    private int like;

    public int getDoubanId() {
        return doubanId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avater) {
        this.avatar = avater;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }


    @Override
    public int compareTo(@NonNull Object o) {
        if (o instanceof  ChlssMovie){
            try {
                throw new Exception("不是同一类");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ChlssMovie another = (ChlssMovie) o;
        if (this.like < another.like){
            return  1;
        }else {
            return -1;
        }
    }
}
