package com.example.xiaoh.doubanmovie;

import java.io.Serializable;

public class City implements Serializable,Comparable {//用于城市解析器的存储城市相关数据的类
    private String name;//城市的名字
    private String firstletter;//城市的首字母

    public City(String name, String firstletter) {
        this.name = name;
        this.firstletter = firstletter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstletter() {
        return firstletter;
    }

    public void setFirstletter(String firstletter) {
       this.firstletter = firstletter;
    }


    @Override
    public int compareTo(Object o) {//用于城市的排序，便于粘性头的展示
        char c1, c2;
        c1 = firstletter.charAt(0);
        c2 = ((City) o).getFirstletter().charAt(0);
        if (c1<c2){
            return -1;
        }
        return 1;
    }
}
