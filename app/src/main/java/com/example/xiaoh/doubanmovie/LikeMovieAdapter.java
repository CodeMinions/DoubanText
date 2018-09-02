package com.example.xiaoh.doubanmovie;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

/**
 * 相似电影列表的适配器
 */
public class LikeMovieAdapter extends RecyclerView.Adapter<LikeMovieAdapter.ViewHolder> {

    List<Movie> list;

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgView;
        TextView nameView;
        View item;
//        TextView gradeView;

        public ViewHolder(View view){
            super(view);
            nameView = view.findViewById(R.id.likemovie_name);
//            gradeView = view.findViewById(R.id.grade);
            imgView = view.findViewById(R.id.likemovie_pic);
            item = view;
        }
    }
    public LikeMovieAdapter(List<Movie> l){
        list = l;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.like_item, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, final int position){
        holder.nameView.setText(list.get(position).getName());
        setImage(list.get(position).getImg(), holder.imgView);

        holder.item.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int id = list.get(position).getDoubanId();
                Toast.makeText(v.getContext(), Integer.toString(id), Toast.LENGTH_SHORT).show();
                MovieDetailActivity.actionStart(v.getContext(), Integer.toString(id));
            }
        });
    }
    class A{
        Bitmap bmp;
        ImageView image;
    }
    private void setImage(final String url, final ImageView i){

        new Thread(new Runnable(){
            @Override
            public void run(){
                Bitmap bmp = HttpGetImages.getURLImage(url);
                Message msg = new Message();

                A a = new A();
                a.bmp = bmp;
                a.image = i;

                msg.what = 1;
                msg.obj = a;
                handler.sendMessage(msg);
            }
        }).start();
    }
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg){
            switch (msg.what){
                case 1:
                    A a = (A) msg.obj;
                    ImageView imv = a.image;
                    Bitmap bmp = a.bmp;
                    imv.setImageBitmap(bmp);

                    break;
                default:
                    break;
            }
        }
    };


    public int getItemCount(){
        return list.size();
    }
}
