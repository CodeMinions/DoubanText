package com.example.xiaoh.doubanmovie;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;

public class EveryCityOfProvince implements Serializable {//城市选择器中的类用于存储每个省的数据
    private static int  numofProvice = 34;

    private ArrayList<City> [] provnices = new ArrayList[numofProvice];
    private String [] nameOfprovince = new String[numofProvice];

    private int getI(String name){
       int result = 0;
       for (int i = 0 ; i < numofProvice ; i++){
           if (nameOfprovince[i].equals(name)){
               result = i;
           }
       }
       return result;
    }

    public ArrayList<City> getCities(String name){//返回对应 I 的城市集合
        int i = getI(name);
        return provnices[i];
    }
    public ArrayList<City> getCities(int i){
        return provnices[i];
    }

    public void setNameOfprovinceAndcities(String name,int i,ArrayList<City> cities){
        nameOfprovince[i] = name;
        Collections.sort(cities);
        provnices[i] = cities;
    }

    public int getProvinceSize(){
        return nameOfprovince.length;
    }



}
