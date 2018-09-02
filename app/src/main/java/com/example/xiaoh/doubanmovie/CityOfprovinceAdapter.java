package com.example.xiaoh.doubanmovie;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CityOfprovinceAdapter extends RecyclerView.Adapter<CityOfprovinceAdapter.COPviewHolder> {//省级城市activity（CityOfProvinceActivity）中的适配器
    private ArrayList<City> data;
    private AppCompatActivity appCompatActivity;

    public CityOfprovinceAdapter(ArrayList<City> arrayList ,AppCompatActivity appCompatActivity){
        data = arrayList;
        this.appCompatActivity = appCompatActivity;
    }


    @Override
    public COPviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {//
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cityitemview,parent,false);
        return new COPviewHolder(view);
    }

    @Override
    public void onBindViewHolder(final COPviewHolder holder, final int position) {
        holder.textView.setText(data.get(position).getName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int postion = holder.getAdapterPosition();
                String name = data.get(position).getName();
                Intent intent = new Intent();
                intent.putExtra("cityOfProvince",name);
                appCompatActivity.setResult(Activity.RESULT_OK,intent);
                appCompatActivity.finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class COPviewHolder extends RecyclerView.ViewHolder{
        private TextView textView;

        public COPviewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.cityname_textview);
        }
    }

}
