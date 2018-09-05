package com.example.xiaoh.doubanmovie;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DialogTitle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhouwei.blurlibrary.EasyBlur;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import okhttp3.Call;
import okhttp3.Response;

public class MovieDetailActivity extends AppCompatActivity {

    final int SetImage = 1;
    final int ResponseSuccessful = 2;
    final int LikeSuccessful = 3;

    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private List<Movie.Actor> actor_list;
    private List<Movie.Actor> director_list;
    private List<Movie> like_list;

    private Button comment_Button;
    private Button discuss_Button;

    private RelativeLayout title_back;
    private LinearLayout poster_back;
    private ImageView poster_Images;
    private String image_url;

    private TextView title_Text;
    private String movie_Title;

    private TextView year_Text;
    private String movie_year;
    private List<String> movie_genres;

    private TextView time_Text;
    private String movie_time;
    private List<String> movie_localtion;

    private TextView lenght_Text;
    private String movie_lenght;

    private TextView rating_Text;
    private MaterialRatingBar ratingBar;
    private Float movie_rating;

    private TextView ratingCount_Text;
    private int movie_ratingCount;

    private TextView summary_Text;
    private String movie_summary;

    private String movie_id;

    private Button back_button;

    public static void actionStart(Context context, String movieId){
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra("id", movieId);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetail);

        movie_id = getIntent().getStringExtra("id");
        String url = "https://api.douban.com/v2/movie/subject/" + movie_id;
        String like_url = "http://192.168.115.70:3000/movie/movie_like/" + movie_id;

        comment_Button = findViewById(R.id.button_comment);
        discuss_Button = findViewById(R.id.button_discuss);

        poster_Images = findViewById(R.id.posters);
        poster_back = findViewById(R.id.posters_back);
        title_back = findViewById(R.id.title_back);

        title_Text = findViewById(R.id.movie_name);

        year_Text = findViewById(R.id.info1);
        time_Text = findViewById(R.id.info2);
        lenght_Text = findViewById(R.id.info3);
        rating_Text = findViewById(R.id.rating);
        ratingCount_Text = findViewById(R.id.rating_count);
        ratingBar = findViewById(R.id.rating_star);
        summary_Text = findViewById(R.id.summary);

        back_button = findViewById(R.id.back_movie);

/**
 * 定义返回按钮
 */
        back_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });



/**
 * 隐藏原生标题栏
 */
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

/**
 * 评论讨论板块点击翻页
 */
        final ColorStateList aColor = getResources().getColorStateList(R.color.choose_color);
        final ColorStateList bColor = getResources().getColorStateList(R.color.notchoose_color);
        comment_Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                replaceFragment(new CommentFragment());
                comment_Button.setTextColor(aColor);
                discuss_Button.setTextColor(bColor);
            }
        });
        discuss_Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                replaceFragment(new DiscussFragment());
                comment_Button.setTextColor(bColor);
                discuss_Button.setTextColor(aColor);
            }
        });

        replaceFragment(new CommentFragment());
        discuss_Button.setTextColor(bColor);

/**
 * 网络请求加载解析数据与图片
 */
//        "https://api.douban.com/v2/movie/subject/26985127"
        HttpUtil.sendHttpRequest(url, new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String getData = response.body().string();
                parseJson(getData);

                if(response.isSuccessful()){
                    Message msg = new Message();
                    msg.what = ResponseSuccessful;
                    handler.sendMessage(msg);
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {

            }
        });


        HttpUtil.sendHttpRequest(like_url, new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String getData = response.body().string();
                parseLikeJson(getData);
                if(response.isSuccessful()){
                    Message msg = new Message();
                    msg.what = LikeSuccessful;
                    handler.sendMessage(msg);
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {

            }
        });

    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg){
            switch (msg.what){
                case SetImage:
                    Bitmap bmp = (Bitmap) msg.obj;
                    poster_Images.setImageBitmap(bmp);

                    break;
                case ResponseSuccessful:
                    initData();
                    initActorList();
                    break;
                case LikeSuccessful:
                    initLikeList();
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 加载影人列表
     */
    public void initActorList(){
        recyclerView = findViewById(R.id.actor);
        recyclerView.setNestedScrollingEnabled(false);
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);

        ActorAdapter adapter = new ActorAdapter(director_list, actor_list);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 加载相似电影列表
     */
    public void initLikeList(){
        recyclerView = findViewById(R.id.likemovie_list);
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);

        LikeMovieAdapter adapter = new LikeMovieAdapter(like_list);
        recyclerView.setAdapter(adapter);
    }



/**
 * 加载大部分数据
 */
    public void initData(){
        setImage(image_url);
        title_Text.setText(movie_Title);

        rating_Text.setText(Float.toString(movie_rating));
        ratingBar.setRating(movie_rating / 2);

        ratingCount_Text.setText(Integer.toString(movie_ratingCount) + "人");
        summary_Text.setText(movie_summary);

        String type = "";
        for(int i = 0; i < movie_genres.size(); i++){
            type += movie_genres.get(i);
            if(i != movie_genres.size()-1)
                type += "  / ";
        }
        year_Text.setText(movie_year + " / " + type);

        String localtion = "(";
        for(int i = 0; i < movie_localtion.size(); i++){
            localtion += movie_localtion.get(i);
            if(i == movie_localtion.size()-1)
                localtion += ")";
            else
                localtion += ",";
        }
        time_Text.setText("上映时间：" + localtion);
    }


/**
 * 载入图片
 * 耗时操作，开启异步线程
 * 请求成功发送消息，成功获取图片
 */
//    https://pic4.zhimg.com/v2-94ff4fdd94d43e04e349f32c9e210a79_b.jpg
/**
 * 设置主色调背景
 */
    private void setImage(final String url){
        new Thread(new Runnable(){
            @Override
            public void run(){
                Bitmap bmp = HttpGetImages.getURLImage(url);
                Palette.Builder builder = Palette.from(bmp);
                builder.generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch vibrant = palette.getDominantSwatch();
                        poster_back.setBackgroundColor(vibrant.getRgb());
                        title_back.setBackgroundColor(vibrant.getRgb());
/**
 * 实现沉浸式状态栏
 */
                        if (android.os.Build.VERSION.SDK_INT >= 21) {
                            Window window = getWindow();
                            window.setStatusBarColor(vibrant.getRgb());
                            window.setNavigationBarColor(vibrant.getRgb());
                        }
                    }
                });
                Message msg = new Message();
                msg.what = SetImage;
                msg.obj = bmp;
                handler.sendMessage(msg);
            }
        }).start();
    }

/**
 * 加载评论区布局
 */
    private void replaceFragment(Fragment fragmenet){
        Bundle b = new Bundle();
        b.putString("id", movie_id);
        fragmenet.setArguments(b);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.comments, fragmenet);
        transaction.commit();
    }

/**
 * 解析json数据
 */
// FIXME: 2018/8/20 上映时间api有误
    public void parseJson(String data){
        Gson gson = new Gson();
        Movie l = gson.fromJson(data, Movie.class);
        image_url = l.getImages().getSmall();
        movie_Title = l.getTitle();
        movie_year = l.getYear();
        movie_rating = l.getRating().getAverage();
        movie_localtion = l.getCountries();
        movie_ratingCount = l.getRatings_count();
        movie_genres = l.getGenres();
        movie_summary = l.getSummary();
        actor_list = l.getCasts();
        director_list = l.getDirectors();

    }

    /**
     * 解析相似电影json
     */
    public void parseLikeJson(String data){
        Gson gson = new Gson();
        like_list = gson.fromJson(data, new TypeToken<List<Movie>>(){}.getType());
    }
}
