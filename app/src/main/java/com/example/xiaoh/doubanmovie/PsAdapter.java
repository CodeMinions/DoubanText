package com.example.xiaoh.doubanmovie;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class PsAdapter extends RecyclerView.Adapter<PsAdapter.MyHolder>{//正在热映中fragment中的适配器

    private List<ChlMovie> list;
    private Activity activity;


    public PsAdapter(ArrayList<ChlMovie> list,Activity activity){
        this.list = list;
        this.activity = activity;
    }


    @Override
    public PsAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.psitemview,parent,false);
        PsAdapter.MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        if (list.size() ==0 ){
            holder.titleView.setText("正在刷新");
        }else {
            holder.titleView.setText(list.get(position).getTitle());
            double average =list.get(position).getRating().getAverage();
            String str="暂无评分";
            if (average > 0){
                str = "评分:"+average;
            }
            holder.score.setText(str);

            holder.ratingBar.setRating((float) (average/2.0));

            int number = list.get(position).getCollect_count();
            String numberOfPeopleSee;
            if (number<=10000){
                numberOfPeopleSee= number+"人看过";
            }else {
                double n = (double)number/10000;
                numberOfPeopleSee = String.format("%.1f 万人看过",n);
            }
            holder.manysee.setText(numberOfPeopleSee);
            StringBuilder builder = new StringBuilder();
            builder.append("导演:");
            builder.append(list.get(position).getDirectors().get(0).getName()+"/");
            String result =builder.deleteCharAt(builder.length()-1).toString();
            holder.director.setText(result);
            builder=null;
            StringBuilder builder1 = new StringBuilder();
            builder1.append("主演:");
            for (int z = 0 ; z < list.get(position).getCasts().size();z++){
                builder1.append(list.get(position).getCasts().get(z).getName()+"/");
            }
            if (builder1.length()>=13){
                int end = builder1.length();
                builder1.delete(13,end);
                builder1.append("....");
            }
            String result1 = builder1.deleteCharAt(builder1.length()-1).toString();
            holder.staring.setText(result1);
            holder.imageView.setImageURL(list.get(position).getImages().getSmall());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(activity,); //需要填上你写的电影详情页的activiclass
                    String id = list.get(position).getId();
//                    intent.putExtra("id",id);
//                    activity.startActivity(intent);
                    MovieDetailActivity.actionStart(view.getContext(), id);
                }
            });

        }
    }


    @Override
    public int getItemCount() {
        if (list.size()== 0){
            return 1;
        }else {
            return list.size();
        }
    }

    class MyHolder extends RecyclerView.ViewHolder{
        MyImageView imageView;
        TextView titleView,director,score,staring,manysee;
        Button byticket;

        MaterialRatingBar ratingBar;

        public MyHolder(View itemView) {
            super(itemView);

            ratingBar = (MaterialRatingBar) itemView.findViewById(R.id.rating_star);
            imageView = (MyImageView) itemView.findViewById(R.id.picture_image);
            titleView = (TextView) itemView.findViewById(R.id.movie_title_Text);
            director = (TextView) itemView.findViewById(R.id.director);
            score = (TextView) itemView.findViewById(R.id.score);
            staring= (TextView) itemView.findViewById(R.id.staring);
            manysee = (TextView) itemView.findViewById(R.id.how_many_people_see);
        }
    }
}
