package com.example.xiaoh.doubanmovie;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SoonShowFragment extends Fragment implements View.OnClickListener {//即将上映的fragment

    private boolean isSee = true;//判断当前fragment是否可见
    private ArrayList<ChlssMovie> movies = new ArrayList<>();//存储数据
    private ArrayList<ChlssMovie> moviesbylike = new ArrayList<>();
    private ArrayList<GroupInfo> groups =new ArrayList<>();
//    private String url = "http://10.0.2.2:3000/movie/coming_soon";
    private String url = "http://192.168.115.70:3000/movie/coming_soon";
    private Ssadapter adapter;
    private XRecyclerView recyclerView;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
 //           additemdirect();
            addItemDecoration();
            sortbylike();
            adapter.notifyDataSetChanged();
            recyclerView.refreshComplete();

            progress.setVisibility(View.GONE);
        }
    };

    private Button time_button,like_button;
    private StickyItemDecoration decoration;

    private RelativeLayout progress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_soon_show,container,false);
        adapter = new Ssadapter(movies,getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView = (XRecyclerView) view.findViewById(R.id.soonfragment_container);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        time_button = (Button) view.findViewById(R.id.time_button);
        like_button = (Button) view.findViewById(R.id.like_button);

        progress = (RelativeLayout) view.findViewById(R.id.progress_3);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        time_button.setOnClickListener(this);
        like_button.setOnClickListener(this);
        initData();
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initData();
            }

            @Override
            public void onLoadMore() {
                recyclerView.refreshComplete();
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden){
            isSee = false;
        }else {
            isSee = true;
        }
    }
    public boolean isSee() {
        return isSee;
    }

    private void initData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    client.newBuilder().
                    connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS);
                    Request request = new Request.Builder()
                            .url(url)
                            .build();

                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Gson gson = new Gson();
                    ArrayList<ChlssMovie> temp;
                    temp = gson.fromJson(responseData, new TypeToken<ArrayList<ChlssMovie>>(){}.getType());
                    for (int i = 0 ; i < temp.size();i++){
                        movies.add(temp.get(i));
                    }
                    handler.sendEmptyMessage(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

//    private void additemdirect(){
//        SectionTimeDecoration.GroupInfoCallback callback = new SectionTimeDecoration.GroupInfoCallback() {
//                @Override
//                public GroupInfo getGroupInfo(int position) {
//                    int groupId = position / 5;
//                    int index = position % 5;
//                    GroupInfo groupInfo = new GroupInfo(groupId,groupId+"");
//                    groupInfo.setPosition(index);
//                    return groupInfo;
//                }
//        };
//        SectionTimeDecoration decoration = new SectionTimeDecoration(callback);
//        recyclerView.addItemDecoration(decoration);
//    }

    private void addItemDecoration(){
        ISticky iSticky = new ISticky() {
            @Override
            public boolean isFirstPostion(int pos) {
                if (pos ==movies.size()){
                    pos = movies.size()-1;
                }//一个奇怪的bug补救
                String date = movies.get(pos).getDate();
                for (int i = 0 ;i <pos ;i ++){
                    if (date.equals(movies.get(i).getDate())){
                        return false;
                    }
                }
                return true;
            }
            @Override
            public String getGroupTitle(int pos) {
                if (pos ==movies.size()){
                    pos = movies.size()-1;
                }//一个奇怪的bug补救
               return   movies.get(pos).getDate();
            }
        };
        decoration =new StickyItemDecoration(getContext(),iSticky);
        recyclerView.addItemDecoration(decoration);
    }


    private boolean isExist(int position){//用于在粘性头判断是否为同组第一个
        String date = movies.get(position).getDate();
        for (int i = 0 ;i <position ;i ++){
            if (date.equals(movies.get(i).getDate())){
                return true;
            }
        }
        return false;
    }

    private void sortbylike(){//按热度排序并去掉粘性头
        for (ChlssMovie movie : movies){
            moviesbylike.add(movie);
        }
        Collections.sort(moviesbylike);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.time_button://按时间排序
                recyclerView.addItemDecoration(decoration);
                Ssadapter ssadapter = new Ssadapter(movies,getActivity());
                recyclerView.setAdapter(ssadapter);
                ssadapter.notifyDataSetChanged();
                break;

            case R.id.like_button://按热度排序
                Ssadapter adapter = new Ssadapter(moviesbylike,getActivity());
                recyclerView.removeItemDecoration(decoration);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
