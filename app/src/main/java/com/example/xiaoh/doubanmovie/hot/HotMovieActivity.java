package com.example.xiaoh.doubanmovie.hot;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xiaoh.doubanmovie.BaseActivity;
import com.example.xiaoh.doubanmovie.Constants;
import com.example.xiaoh.doubanmovie.R;
import com.example.xiaoh.doubanmovie.bean.MovieBean;
import com.example.xiaoh.doubanmovie.bean.Subject;
import com.example.xiaoh.doubanmovie.utils.HttpUtils;
import com.example.xiaoh.doubanmovie.utils.HttpUtils.OnHttpResult;
import com.example.xiaoh.doubanmovie.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 热门电影页面
 */

public class HotMovieActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_share)
    ImageView mIvShare;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rl_progress)
    RelativeLayout mRlProgress;
    private Context mContext;
    private Adapter mAdapter;
    protected List<MovieBean> mList = new ArrayList();
    private int count=20;//每次加载多少条
    /**
     * activity创建
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_hot_movie);
        ButterKnife.bind(this);
        mTvTitle.setText(getTitleName());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = getAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initData();

            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                loadMore();
            }
        });
        mRlProgress.setVisibility(View.VISIBLE);
        mRefreshLayout.setVisibility(View.GONE);
        initData();
    }

    /**
     * 获取adapter
     * @return
     */
    public Adapter getAdapter() {
       return new HotMovieAdapter(mContext, mList);
    }

    public String getTitleName()
    {
        return "豆瓣热门";
    }

    /**
     * 获取数据
     */
    private void initData() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("apikey",Constants.API_KEY);
        map.put("start","0");
        map.put("count",count+"");
        HttpUtils.getData(getAPi(), map, Subject.class, new OnHttpResult<Subject>() {
            @Override
            public void onFail(int code) {
                ToastUtils.show("数据加载失败!");
                mRefreshLayout.finishRefresh(false);
            }

            @Override
            public void onSuccess(Subject subject) {
                mList.clear();
                if (subject == null || subject.subjects == null)
                    return;
                mList.addAll(subject.subjects);
                mAdapter.notifyDataSetChanged();
                mRefreshLayout.finishRefresh(/*,false*/);//传入false表示刷新失败
                mRlProgress.setVisibility(View.GONE);
                mRefreshLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 上拉加载更多数据
     */
    private void loadMore() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("apikey",Constants.API_KEY);
        map.put("start",mList.size()+"");
        map.put("count",count+"");
        HttpUtils.getData(getAPi(), map, Subject.class, new OnHttpResult<Subject>() {
            @Override
            public void onFail(int code) {
                ToastUtils.show("数据加载失败!");
                mRefreshLayout.finishLoadMore(false);
            }

            @Override
            public void onSuccess(Subject subject) {
                if (subject == null )
                    return;
                if( subject.subjects == null||subject.subjects.size()==0)
                {
                    ToastUtils.show("没有更多数据了");
                    mRefreshLayout.finishLoadMore();
                }
                mList.addAll(subject.subjects);
                mAdapter.notifyDataSetChanged();
                mRefreshLayout.finishLoadMore(/*,false*/);//传入false表示刷新失败
            }
        });
    }

    public String getAPi(){
        return Constants.HOT_MOVIE_API;
    }

    /**
     * 绑定点击事件
     */
    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

}
