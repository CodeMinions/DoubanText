package com.example.xiaoh.doubanmovie.top;

import android.content.Context;
import android.support.v7.widget.CardView;
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
import com.example.xiaoh.doubanmovie.bean.MovieBean;
import com.example.xiaoh.doubanmovie.top.TopMovieAdapter.ViewHolder;
import com.example.xiaoh.doubanmovie.utils.ImageLoadUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * top250的adapter
 */

public class TopMovieAdapter extends Adapter<ViewHolder> {
    private Context mContext;
    private List<MovieBean> mList;

    public TopMovieAdapter(Context context, List<MovieBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_top_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MovieBean bean = mList.get(position);
        ImageLoadUtils.display(holder.mIvAvatar,bean.images.medium);
        holder.mTvName.setText(bean.title);
        holder.mTvYear.setText("("+bean.year+")");
        holder.mTvStar.setText(bean.rating.average+"");
        holder.mRbStar.setRating((float) (bean.rating.average/2.0));
        String content = bean.year + "/";
        for (String string : bean.genres) {
            content = new StringBuffer(content + string + " ").toString();
        }
        for (int i = 0; i < bean.directors.size(); i++) {
            content+="/"+bean.directors.get(i).name+"/";
        }
        for (int i = 0; i < bean.casts.size(); i++) {
            if (i != bean.casts.size() - 1) {
                content = new StringBuffer(content + bean.casts.get(i).name + " ").toString();
            } else {
                content = new StringBuffer(content + bean.casts.get(i).name).toString();
            }

        }
        holder.mTvInfo.setText(content);
        holder.mTvNumber.setText("NO."+(position+1));
        holder.mTvDetail.setText(bean.original_title);

        holder.view.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                MovieDetailActivity.actionStart(v.getContext(), bean.id);
                Toast.makeText(v.getContext(), "NumMovie", Toast.LENGTH_SHORT).show();
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
        @BindView(R.id.cv_content)
        CardView mCvContent;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.rb_star)
        MaterialRatingBar mRbStar;
        @BindView(R.id.tv_star)
        TextView mTvStar;
        @BindView(R.id.tv_info)
        TextView mTvInfo;
        @BindView(R.id.tv_number)
        TextView mTvNumber;
        @BindView(R.id.tv_year)
        TextView mTvYear;
        @BindView(R.id.tv_detail)
        TextView mTvDetail;

        View view;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            view = itemView;
        }
    }
}
