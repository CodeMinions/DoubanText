package com.example.xiaoh.doubanmovie;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;

public class HotCities implements Serializable {//存储热门城市
    private ArrayList<City> hotcities = new ArrayList<>() ;

    public ArrayList<City> getHotcities() {
        return hotcities;
    }

    public void setHotcities(ArrayList<City> hotcities) {
        Collections.sort(hotcities);
        this.hotcities = hotcities;
    }
}
