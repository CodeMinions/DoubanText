package com.example.xiaoh.doubanmovie;

import android.location.LocationManager;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class SearchCityActivity extends AppCompatActivity {//搜索城市的activity
    private EditText editText;
    private Button marsureButton;
    private String cityname;//
    private EveryCityOfProvince everyCityOfProvince;//存储所有省份城市，直辖城市
    private ArrayList<City> data = new ArrayList<>();
    private XRecyclerView xRecyclerView;
    private CityOfprovinceAdapter adapter;
    private ISticky iSticky = new ISticky() {//用于实现粘性头
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        editText = (EditText) findViewById(R.id.search_cityName);
        marsureButton = (Button) findViewById(R.id.makesure_button);
        everyCityOfProvince = CityDatasovle.readEveryCityOfProvince(getResources().openRawResource(R.raw.city2),getResources().openRawResource(R.raw.city3));
        xRecyclerView = (XRecyclerView) findViewById(R.id.city_container_XrecycleView);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adapter = new CityOfprovinceAdapter(data,this);

        xRecyclerView.setLayoutManager(manager);
//        final StickyItemDecoration decoration = new StickyItemDecoration(this,iSticky);
        final Now_popular_fragemnt.SimpeleitemDecoration decoration = new Now_popular_fragemnt.SimpeleitemDecoration();
        xRecyclerView.setAdapter(adapter);

        marsureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname = editText.getText().toString();
                initData();
                xRecyclerView.addItemDecoration(decoration);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initData(){//初始化数据
        data.clear();
        for (int i = 0 ; i < everyCityOfProvince.getProvinceSize();i++){
            for (City city : everyCityOfProvince.getCities(i)){
                if (cityname!= null) {
                    if (city.getName().contains(cityname)){
                        data.add(city);
                    }
                }
            }
        }
        Collections.sort(data);
    }


}
