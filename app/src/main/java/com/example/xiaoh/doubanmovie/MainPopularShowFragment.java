package com.example.xiaoh.doubanmovie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.example.xiaoh.doubanmovie.utils.DrawableCenterLeftEditView;

public class MainPopularShowFragment extends Fragment{//主要的fragment，功能：容纳正在热映，和即将上映两个fragment 以及跳转到搜索电影，选择城市的activity
    private Button choice_city_button;//城市选择
    private Button search_movie;//搜索电影
    private Button nowpopularshow;//正在热映
    private Button soonshow;//即将上映
    private Now_popular_fragemnt now_popular_fragemnt;//正在热映的fragment
    private SoonShowFragment soon_Show_fragment;//即将上映的fragment
    private FragmentManager manager;//管理子fragment（即将上映，正在热映的fragment）的管理者
    private FragmentTransaction transaction;//操作fragment
    private String cityname;//用于显示当前城市的属性

    private DrawableCenterLeftEditView editView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_popularshow,container,false);
        choice_city_button = (Button) view.findViewById(R.id.choice_city_button);

        nowpopularshow=(Button) view.findViewById(R.id.now_popular_show_button);
        soonshow = (Button) view.findViewById(R.id.soon_show_button);

//        search_movie =(Button) view.findViewById(R.id.search_movie_button);
        editView = (DrawableCenterLeftEditView) view.findViewById(R.id.search_movie_button);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences preferences =  getActivity().getSharedPreferences("precityname", Context.MODE_PRIVATE);
        cityname = preferences.getString("cityname","北京");
        choice_city_button.setText(cityname);
        manager = getChildFragmentManager();
        now_popular_fragemnt = (Now_popular_fragemnt) manager.findFragmentById(R.id.now_popular_show_fragment);
        soon_Show_fragment = (SoonShowFragment) manager.findFragmentById(R.id.soon_show_fragment);
        transaction = manager.beginTransaction();
        transaction.hide(soon_Show_fragment).commit();
        choice_city_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),CityActivity.class);
                getActivity().startActivityForResult(intent,1);
            }
        });


        /**
         * 点击进入搜索界面
         */
        editView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(getActivity(), SearchActivity.class);//写上电影详情页
                 getActivity().startActivity(intent);

            }
        });

        nowpopularshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!now_popular_fragemnt.isSee()) {//判断这个fragment可见
                    transaction = manager.beginTransaction();
                    transaction.show(now_popular_fragemnt)
                            .hide(soon_Show_fragment)
                            .commit();//显示now_popular_fragemnt ，隐藏soon_Show_fragment
                }
            }
        });

        soonshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!soon_Show_fragment.isSee()){
                    transaction = manager.beginTransaction();
                    transaction.show(soon_Show_fragment)
                            .hide(now_popular_fragemnt)
                            .commit();//显示soon_Show_fragment，隐藏now_popular_fragemnt
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){//处理城市选择器返回的城市
        switch (requestCode){
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    String cityname = data.getStringExtra("nameofcity");
                    SharedPreferences preferences =  getActivity().getSharedPreferences("precityname", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("cityname",cityname);
                    editor.commit();
                    this.cityname = cityname;
                    choice_city_button.setText(cityname);
                    now_popular_fragemnt.notifyDataChaged(cityname);
                }
                if (resultCode == CityActivity.RESULT_OK2){
                    String cityname = data.getStringExtra("cityOfProvince");
                    SharedPreferences preferences =  getActivity().getSharedPreferences("precityname", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("cityname",cityname);
                    editor.commit();
                    this.cityname = cityname;
                    choice_city_button.setText(cityname);
                    now_popular_fragemnt.notifyDataChaged(cityname);
                }
                break;
        }
    }
}
