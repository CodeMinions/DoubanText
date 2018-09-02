package com.example.xiaoh.doubanmovie;

import java.io.Serializable;

public class Province implements Serializable , Comparable{//城市选择器中用于存储省数据的类
    private String name;
    private boolean isOnlyCity;
    private String firstName;

    public Province(String name, boolean isOnlyCity,String firstName) {
        this.name = name;
        this.isOnlyCity = isOnlyCity;
        this.firstName = firstName;
    }
    public Province(String name,String firstName){
        this.name = name;
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOnlyCity() {
        return isOnlyCity;
    }

    public void setOnlyCity(boolean onlyCity) {
        isOnlyCity = onlyCity;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public int compareTo(Object o) {
        char c1, c2;
        c1 = this.firstName.charAt(0);
        c2 = ((Province)o).getFirstName().charAt(0);
        if (c1<c2){
            return -1;
        }
        return 1;
    }



}
