package com.example.xiaoh.doubanmovie;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CityActivity extends AppCompatActivity {
    //城市解析器的第一层activity（用于展示当前城市，热门城市，省级城市）
    private HotCities hotCities;//存储热门城市的类
    private Provinces provinces;//存储全部省的类
    private XRecyclerView xRecyclerView;
    private ArrayList<Province> data;//最终用于适配器的数据
    private Cityadapter cityadapter;
    public static int RESULT_OK2 = 10086;// 作为setResult的返回值
    //数据
    //当前城市联网获取
    //热门城市处理成一个特殊的省 省的字符串然后在onbind中分割赋值
    //粘性头 改动的地方接口保存组得到信息
    //通知修改

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        hotCities = CityDatasovle.readHotcity(getResources().openRawResource(R.raw.hotcity));//借助CityDatasolve 类实现从raw资源文件中读取预先写入的含有城市信息的文本
        provinces = CityDatasovle.readProvices(getResources().openRawResource(R.raw.shenji));//借助CityDatasolve 类实现从raw资源文件中读取预先写入的含有城市信息的文本
        xRecyclerView = (XRecyclerView) findViewById(R.id.city_Xrecyclevew);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        StringBuilder speicalProvince = new StringBuilder();
        for (int i = 0; i < hotCities.getHotcities().size(); i++) {
            speicalProvince.append(hotCities.getHotcities().get(i).getName() + ",");
        }
        speicalProvince.deleteCharAt(speicalProvince.length() - 1);
        Province speical = new Province(speicalProvince.toString(), true, "热门城市");
        Province nowCity = new Province("天津", true, "当前城市");//先默认当前城市是天津
        getCityName();//测试cityname
        data = new ArrayList<Province>();
        data.add(nowCity);
        data.add(speical);
        for (int i = 0; i < provinces.getProvinces().size(); i++) {
            data.add(provinces.getProvinces().get(i));
        }
        cityadapter = new Cityadapter(data, this);
        xRecyclerView.setLayoutManager(layoutManager);
        additemdecoration();
        xRecyclerView.setAdapter(cityadapter);
    }

    private void additemdecoration() {//通过此方法加入粘性头
        ISticky iSticky = new ISticky() {
            @Override
            public boolean isFirstPostion(int pos) {
                if (pos == data.size()) {
                    pos = data.size() - 1;
                }//一个奇怪的bug补救
                String head = data.get(pos).getFirstName();
                for (int i = 0; i < pos; i++) {
                    if (data.get(i).getFirstName().equals(head)) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public String getGroupTitle(int pos) {
                if (pos == data.size()) {
                    pos = data.size() - 1;
                }//一个奇怪的bug补救
                return data.get(pos).getFirstName();
            }
        };
        StickyItemDecoration decoration = new StickyItemDecoration(this, iSticky);
        xRecyclerView.addItemDecoration(decoration);
    }

    public void startsearchactivity(View view) {//打开搜索城市的方法
        Intent intent = new Intent(this,SearchCityActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK){
                    setResult(RESULT_OK2, data);
                    finish();
                }
                break;
            case 2:
                if (resultCode == Activity.RESULT_OK) {
                    setResult(RESULT_OK2, data);
                    finish();
                }
        }
    }


    public void getCityName(){//此方法用于用户权限的申明
        if (ContextCompat.checkSelfPermission(CityActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(CityActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else {
           getlocataion();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getlocataion();
                }else {
                    Toast.makeText(this,"you denied the permisson",Toast.LENGTH_SHORT).show();
                }
        }
    }

    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    String name = amapLocation.getCity();
                    if (name!= null){
                        if (name.equals("")){
                            data.get(0).setName("天津");
                        }else{
                            data.get(0).setName(name);
                        }
                        cityadapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(CityActivity.this, "服务异常", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("gd", "cityname = "+name);
                }else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    } ;

    private void getlocataion(){//通过高德的api获取地址
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        option.setNeedAddress(true);
        option.setInterval(2000);
        mLocationClient.setLocationOption(option);
        mLocationClient.startLocation();
    }

    public void back(View view) {//用于返回
        onBackPressed();
    }


}
