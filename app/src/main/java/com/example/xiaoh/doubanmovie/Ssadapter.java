package com.example.xiaoh.doubanmovie;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Ssadapter extends RecyclerView.Adapter<Ssadapter.MyViewHolder> {//即将上映的fragment(SoonShowFragmnet)中的xrecycleView 的adapter
    private ArrayList<ChlssMovie> movies;
    private Activity activity;


    public Ssadapter(ArrayList<ChlssMovie> movies, Activity activity) {
        this.movies = movies;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ssitemview,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        String res1 = movies.get(position).getName();
        holder.titleview.setText(res1);
        res1 = movies.get(position).getLabel();
        holder.label.setText(res1);
        res1 = movies.get(position).getArea();
        holder.area.setText(res1);
        int number = movies.get(position).getLike();
        String numberOfPeopleSee;
        if (number<=10000){
            numberOfPeopleSee= number+"人想看";
        }else {
            double n = (double)number/10000;
            numberOfPeopleSee = String.format("%.1f 万人想看",n);
        }
        holder.manylike.setText(numberOfPeopleSee);
        res1 = movies.get(position).getAvatar();
        holder.imageView.setImageURL(res1);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = String.valueOf(movies.get(position).getDoubanId());
//                Intent intent = new Intent(activity,);//写上详情页的class
//                intent.putExtra("id",id);
//                activity.startActivity(intent);
                MovieDetailActivity.actionStart(view.getContext(), id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private MyImageView imageView;
        private TextView titleview;
        private TextView label;
        private TextView area;
        private TextView manylike;


        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (MyImageView) itemView.findViewById(R.id.picture_image);
            titleview = (TextView) itemView.findViewById(R.id.movie_title_Text);
            label = (TextView) itemView.findViewById(R.id.label);
            area = (TextView) itemView.findViewById(R.id.area);
            manylike = (TextView) itemView.findViewById(R.id.how_many_people_like);
        }
    }


}
