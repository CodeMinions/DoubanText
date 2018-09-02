package com.example.xiaoh.doubanmovie;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.xiaoh.doubanmovie.main.SearchMovieActivity;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.popularShow_button)
    Button button_1;

    Button button_2;
//    @BindView(R.id.my_button)
//    Button button_3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        button_2 = (Button) findViewById(R.id.search_movie_fbutton);
        button_2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, SearchMovieActivity.class);
                startActivity(intent);
            }
        });
//        button_3.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                replaceFragment(new DiscussFragment());
//            }
//        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        FragmentManager manager = getSupportFragmentManager();
        MainPopularShowFragment fragment =(MainPopularShowFragment) manager.findFragmentById(R.id.Main_popular_fragment);//id为我的热映fragment的id
        fragment.onActivityResult(requestCode,resultCode,data);
        //因为fragment没有onActiviyResult 说以需要借助Mainacyivity的方法

    }

}
