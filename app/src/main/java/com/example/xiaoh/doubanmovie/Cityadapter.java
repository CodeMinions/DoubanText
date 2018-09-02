package com.example.xiaoh.doubanmovie;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Cityadapter extends RecyclerView.Adapter  {//用于CityActiviy中的适配器
    private static int nowcity = 0;
    private static int hotcity = 1;
    private static int province = 2;
    private ArrayList<Province> data ;
    private AppCompatActivity cityActivity;

    public Cityadapter (ArrayList<Province> outdata,AppCompatActivity cityActivity){
        data = outdata;
        this.cityActivity = cityActivity;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return nowcity;
        }else if (position == 1){
            return hotcity;
        }
        return province;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == nowcity){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cityitemview,parent,false);
            return new MyNowCityViewHolder(view);
        }else if (viewType ==hotcity){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotcityitem,parent,false);
            return new MyHotCityViewHolder(view);
        }else if (viewType == province){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cityitemview,parent,false);
            return new MyProvinceViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (position == 0){//bug的一个侧面反映
            MyNowCityViewHolder myNowCityViewHolder = (MyNowCityViewHolder)holder;
            myNowCityViewHolder.textView.setText(data.get(position).getName());
            myNowCityViewHolder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();
                    Province province = data.get(0);
                    backOrstart(true,province.getName());
                }
            });

        }else if (position == 1){
            final MyHotCityViewHolder myHotCityViewHolder = (MyHotCityViewHolder) holder;
            String names = data.get(position).getName();
            final String [] citynames = names.split(",");
            for (int i = 0 ; i<citynames.length;i++){
                myHotCityViewHolder.buttons[i].setText(citynames[i]);
                final int finalI = i;
                myHotCityViewHolder.buttons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = citynames[finalI];
                        backOrstart(true,name);
                    }
                });
            }
        }else{
            MyProvinceViewHolder myProvinceViewHolder = (MyProvinceViewHolder) holder;
            myProvinceViewHolder.textView.setText(data.get(position).getName());
            myProvinceViewHolder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int postion = holder.getAdapterPosition();
                    Province province = data.get(position);
                    backOrstart(province.isOnlyCity(),province.getName());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    private class MyNowCityViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public MyNowCityViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.cityname_textview);
        }
    }

    private class MyProvinceViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public MyProvinceViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.cityname_textview);
        }
    }

    private class MyHotCityViewHolder extends RecyclerView.ViewHolder{
        private Button [] buttons= new Button[15];

        public MyHotCityViewHolder(View itemView) {
            super(itemView);
            buttons[0] =(Button)itemView.findViewById(R.id.button_1);
            buttons[1] =(Button)itemView.findViewById(R.id.button_2);
            buttons[2] =(Button)itemView.findViewById(R.id.button_3);
            buttons[3] =(Button)itemView.findViewById(R.id.button_4);
            buttons[4] =(Button)itemView.findViewById(R.id.button_5);
            buttons[5] =(Button)itemView.findViewById(R.id.button_6);
            buttons[6] =(Button)itemView.findViewById(R.id.button_7);
            buttons[7] =(Button)itemView.findViewById(R.id.button_8);
            buttons[8] =(Button)itemView.findViewById(R.id.button_9);
            buttons[9] =(Button)itemView.findViewById(R.id.button_10);
            buttons[10] =(Button)itemView.findViewById(R.id.button_11);
            buttons[11] =(Button)itemView.findViewById(R.id.button_12);
            buttons[12] =(Button)itemView.findViewById(R.id.button_13);
            buttons[13] =(Button)itemView.findViewById(R.id.button_14);
            buttons[14] =(Button)itemView.findViewById(R.id.button_15);
        }
    }


    public void backOrstart(boolean isonclycity,String name){//是不是唯一城市，城市或者省份名字
        if (isonclycity){
            //返回到Mainactivity
            Intent intent = new Intent();
            intent.putExtra("nameofcity",name);
            cityActivity.setResult(Activity.RESULT_OK,intent);
            cityActivity.finish();

        }else {
//            打开省份选择城市的activity
            Intent intent = new Intent(cityActivity,CityOfProvinceActivity.class);
            intent.putExtra("nameofprovince",name);
            cityActivity.startActivityForResult(intent,2);
        }
    }

}
