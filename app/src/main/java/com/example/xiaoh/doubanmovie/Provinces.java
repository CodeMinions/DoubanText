package com.example.xiaoh.doubanmovie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Provinces implements Serializable {//城市选择器中用于存储所有省数据的类
    private ArrayList<Province> provinces = new ArrayList<>();

    public ArrayList<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(ArrayList<Province> provinces) {
        Collections.sort(provinces);
        this.provinces = provinces;
    }
}
