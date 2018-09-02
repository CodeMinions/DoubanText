package com.example.xiaoh.doubanmovie;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ViewHolder> {

    private enum ITEM_TYPE{
        Normal_Type,
        More_Type
    }

    private List<Movie.Actor> list;
    private List<Movie.Actor> director_list;
    private int lenght;

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView actor_name;
        TextView actor_job;
        ImageView actor_pic;

        public ViewHolder(View view) {
            super(view);
            actor_name = view.findViewById(R.id.actor_name);
            actor_job = view.findViewById(R.id.job);
            actor_pic = view.findViewById(R.id.actor_pic);
        }
    }

    public ActorAdapter(List<Movie.Actor> d_list, List<Movie.Actor> list){
        this.list = list;
        this.director_list = d_list;
        lenght = list.size() + director_list.size();
    }

    /**
     * 获取布局信息
     */
    @Override
    public int getItemViewType(int position) {
        if(position == lenght){
            return ITEM_TYPE.More_Type.ordinal();
        }
        else{
            return ITEM_TYPE.Normal_Type.ordinal();
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view;
        if(viewType == ITEM_TYPE.More_Type.ordinal()){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.actor_more, parent, false);
        }
        else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.actor_item, parent, false);
        }
        return new ViewHolder(view);
    }

    /**
     * 需要一个对象来携带数据
     */
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
    Handler handler = new Handler() {
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

    public void onBindViewHolder(ViewHolder holder, int position){
        if(position != lenght){
            if(position < director_list.size()){
                holder.actor_name.setText(director_list.get(position).getName());
                holder.actor_job.setText("导演");
                setImage(director_list.get(position).getAvatars().getMedium() ,holder.actor_pic);
            }
            else{
                holder.actor_name.setText(list.get(position - director_list.size()).getName());
                setImage(list.get(position - director_list.size()).getAvatars().getMedium() ,holder.actor_pic);
            }
        }

    }

    public int getItemCount(){
        return lenght + 1;
    }
}
