package com.example.xiaoh.doubanmovie.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaoh.doubanmovie.MovieDetailActivity;
import com.example.xiaoh.doubanmovie.R;
import com.example.xiaoh.doubanmovie.SearchActivity;
import com.example.xiaoh.doubanmovie.bean.MovieBean;
import com.example.xiaoh.doubanmovie.main.MainHotMovieAdapter.ViewHolder;
import com.example.xiaoh.doubanmovie.utils.ImageLoadUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 *首页hot的adapter
 */

public class MainHotMovieAdapter extends Adapter<ViewHolder> {
    private Context mContext;
    private List<MovieBean> mList;

    public MainHotMovieAdapter(Context context, List<MovieBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_main_hot_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MovieBean bean = mList.get(position);
        ImageLoadUtils.display(holder.mIvAvatar,bean.images.medium);
        holder.mTvName.setText(bean.title);
        holder.mTvStar.setText(bean.rating.average+"");
        holder.mRbStar.setRating((float) (bean.rating.average/2.0));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieDetailActivity.actionStart(view.getContext(), bean.id);
                Toast.makeText(view.getContext(), "HotMovie", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_avatar)
        ImageView mIvAvatar;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.rb_star)
        MaterialRatingBar mRbStar;
        @BindView(R.id.tv_star)
        TextView mTvStar;

        View view;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            view = itemView;
        }
    }
}
