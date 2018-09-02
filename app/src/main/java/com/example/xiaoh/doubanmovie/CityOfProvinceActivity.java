package com.example.xiaoh.doubanmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

public class CityOfProvinceActivity extends AppCompatActivity {//城市选择器中点击省份后的省级城市activity
    private EveryCityOfProvince everyCityOfProvince;//存储各省城市的数据
    private ArrayList<City> data;//用于最终适配的数据
    private XRecyclerView xRecyclerView;
    private CityOfprovinceAdapter adapter; //适配器


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_of_province);
        everyCityOfProvince = CityDatasovle.readEveryCityOfProvince(getResources().openRawResource(R.raw.city2),getResources().openRawResource(R.raw.city3));
        xRecyclerView = (XRecyclerView) findViewById(R.id.cityofprovince_XrecycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        initdata();//初始化数据
        additemdecoration();//加入粘性头
        xRecyclerView.setLayoutManager(layoutManager);
        xRecyclerView.setAdapter(adapter);
    }

    private void initdata() {//初始化数据
        Intent intent = getIntent();
        String name = intent.getStringExtra("nameofprovince");
        data = everyCityOfProvince.getCities(name);
        adapter = new CityOfprovinceAdapter(data,this);
    }

    private void additemdecoration(){//加入粘性头
        ISticky iSticky = new ISticky() {
            @Override
            public boolean isFirstPostion(int pos) {
                if (pos ==data.size()){
                    pos = data.size()-1;
                }//一个奇怪的bug补救
                String head = data.get(pos).getFirstletter();
                for (int i = 0 ; i<pos;i++){
                    if (data.get(i).getFirstletter().equals(head)){
                        return false;
                    }
                }
                return true;
            }
            @Override
            public String getGroupTitle(int pos) {
                if (pos ==data.size()){
                    pos = data.size()-1;
                }//一个奇怪的bug补救
                return data.get(pos).getFirstletter();
            }
        };
        StickyItemDecoration itemDecoration = new StickyItemDecoration(this,iSticky);
        xRecyclerView.addItemDecoration(itemDecoration);
    }
}
