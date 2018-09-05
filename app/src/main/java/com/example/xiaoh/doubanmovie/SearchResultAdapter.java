package com.example.xiaoh.doubanmovie;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    int title = 0;
    int item = 1;

    List<Movie> list;

    class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        ImageView imageView;
        TextView titleView;
        TextView text_name;
        TextView text_info;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            text_name = itemView.findViewById(R.id.result_name);
            imageView = itemView.findViewById(R.id.result_poster);
            text_info = itemView.findViewById(R.id.result_info);
            titleView = itemView.findViewById(R.id.search_title);
        }
    }

    public int getItemViewType(int position){
        if(position == 0){
            return title;
        }
        else{
            return item;
        }
    }

    public SearchResultAdapter(List<Movie> list){
         this.list = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view;
        if(viewType == 0){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list, parent, false);
        }
        else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_item, parent, false);
        }

        return new ViewHolder(view);
    }


    class A{
        Bitmap bmp;
        ImageView image;
    }
    private void setImage(final String url, final ImageView view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bmp = HttpGetImages.getURLImage(url);

                Message msg = new Message();
                A a = new A();
                a.bmp = bmp;
                a.image = view;
                msg.obj = a;
                handler.sendMessage(msg);
            }
        }).start();
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg){
            A a = (A) msg.obj;
            ImageView imv = a.image;
            Bitmap bmp = a.bmp;
            imv.setImageBitmap(bmp);
        }
    };


    public void onBindViewHolder(ViewHolder holder, final int position){
        if(position == 0){
            holder.titleView.setText("影视");
        }
        else{
            holder.text_name.setText(list.get(position-1).getTitle());
            holder.text_info.setText(list.get(position-1).getYear());

            setImage(list.get(position-1).getImages().getSmall() ,holder.imageView);

            holder.view.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    String id = list.get(position-1).getId();
                    MovieDetailActivity.actionStart(v.getContext(), id);
                }
            });
        }
    }

    public int getItemCount(){
        return list.size() + 1;
    }

}
