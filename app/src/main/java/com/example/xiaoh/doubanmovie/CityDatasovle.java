package com.example.xiaoh.doubanmovie;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CityDatasovle {//该类用于获取城市集合的工具类,你不需要管这个类
    private static Firstletter firstletter = new Firstletter();


    public static HotCities readHotcity(InputStream inputStream){
        HotCities  hotCities= new HotCities();
        Scanner scanner = new Scanner(inputStream,"gbk");
        String s = new String();
        while (scanner.hasNext()){
            s= scanner.nextLine();
        }
        String parten = "\",\"";
        String [] cities = s.split(parten);
        ArrayList<City> hotCity = new ArrayList<>();
        for (int i = 0 ; i < cities.length;i++){
            String name = cities[i];
            String first = firstletter.getFirstLetter(name);
            City city = new City(name,first);
            hotCity.add(city);
        }
        hotCities.setHotcities(hotCity);
        return hotCities;
    }

    public static Provinces readProvices(InputStream inputStream){
        Scanner scanner = new Scanner(inputStream,"gbk");
        String s = new String();
        while (scanner.hasNext()){
            s= scanner.nextLine();
        }
        String parten = "\",\"";
        String [] cities = s.split(parten);
        ArrayList<Province> provinces = new ArrayList<>();
        for (int i = 0 ; i < cities.length;i++){
            String name = cities[i];
            String first = firstletter.getFirstLetter(name);
            boolean isOnlyCity = false;
            if (name.equals("北京") || name.equals("天津") ||name.equals("上海") || name.equals("重庆") || name.equals("香港") ||name.equals("澳门")){
                isOnlyCity = true;
            }
            Province province = new Province(name,isOnlyCity,first);
            provinces.add(province);
        }
        Provinces provinces1 = new Provinces();
        provinces1.setProvinces(provinces);
        return provinces1;
    }

    public static EveryCityOfProvince readEveryCityOfProvince(InputStream inputStream,InputStream inputStream2){//读取省以及每个省中的城市
        Scanner scanner = new Scanner(inputStream);
        String s = new String();
        EveryCityOfProvince everyCityOfProvince = new EveryCityOfProvince();
        int j = 0;
        while (scanner.hasNext()){
            if (j == 18){
                break;
            }
            s= scanner.nextLine();
            String [] part= s.split(":\\[\"|\"\\]" );
            String name = part[0];
            String parten = "\",\"";
            String [] cities = part[1].split(parten);
            ArrayList<City> citiesofprovince = new ArrayList<>();
            for (int i = 0 ; i <cities.length;i++){
                String nameofcity = cities[i];
                String first = firstletter.getFirstLetter(nameofcity) ;
                City city = new City(nameofcity,first);
                citiesofprovince.add(city);
            }
            everyCityOfProvince.setNameOfprovinceAndcities(name,j,citiesofprovince);
            j++;
        }//一个bug导致后面的代码这个是因为stream无法读取raw文本文件18行内容导致的
        scanner.close();
        Scanner scanner2= new Scanner(inputStream2);
        while (scanner2.hasNext()){
            s= scanner2.nextLine();
            String [] part= s.split(":\\[\"|\"\\]" );
            String name = part[0];
            String parten = "\",\"";
            String [] cities = part[1].split(parten);
            ArrayList<City> citiesofprovince = new ArrayList<>();
            for (int i = 0 ; i <cities.length;i++){
                String nameofcity = cities[i];
                String first = firstletter.getFirstLetter(nameofcity) ;
                City city = new City(nameofcity,first);
                citiesofprovince.add(city);
            }
            everyCityOfProvince.setNameOfprovinceAndcities(name,j,citiesofprovince);
            j++;
        }

        return everyCityOfProvince;
    }

}
