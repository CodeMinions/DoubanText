package com.example.xiaoh.doubanmovie;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.xiaoh.doubanmovie.SearchActivity.historySave;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private ArrayList<Movie> movie_List;
    private ArrayList<String> history_List;
    private List<String> id_list;

    public SQLiteDatabase db;

    public static enum ITEM_TYPE {
        ITEM_TYPE_History,
        ITEM_TYPE_Search,
        ITEM_TYPE_List
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView movieNum;
        TextView movieName;

        View movieView;
        public ViewHolder(View view){
            super(view);
            movieNum = view.findViewById(R.id.num);
            movieName = view.findViewById(R.id.name);
            movieView = view;
        }
    }

    public MovieAdapter(ArrayList<Movie> movie_List, List<String> list){

        this.movie_List = movie_List;
        this.id_list = list;
        db = historySave.getWritableDatabase();
    }

    /**
     * 查询获取数据库数据数
     * 并获取数据
     * @return
     */
    public int allCaseNum( ){

        String sql = "select count(*) from Book";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);

        if(count != 0){
            cursor = db.query("Book", null, null, null, null, null, null);
            history_List = new ArrayList<>();
            if(cursor.moveToFirst()){
                do{
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    history_List.add(name);
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        return count;
    }


    public int getItemViewType(int position){
        int viewType;
        if(allCaseNum() == 0){
            if (position == 0) {
                viewType = ITEM_TYPE.ITEM_TYPE_Search.ordinal();
            } else {
                viewType = ITEM_TYPE.ITEM_TYPE_List.ordinal();
            }
        }
        else {
            if (position == 0) {
                viewType = ITEM_TYPE.ITEM_TYPE_History.ordinal();
            } else if (position == (allCaseNum()+1)) {
                viewType = ITEM_TYPE.ITEM_TYPE_Search.ordinal();
            } else {
                viewType = ITEM_TYPE.ITEM_TYPE_List.ordinal();
            }
        }
        return viewType;
    }


    /**
     * 各自加载子项布局
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View view;
        if(viewType == ITEM_TYPE.ITEM_TYPE_History.ordinal()){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list, parent, false);

            /**
             * FIXME: 2018/8/4 数据库清除，清除数据，但id仍在叠加，需要注意一下
             * 删除数据库
             */
            Button delect = (Button) view.findViewById(R.id.delectHistory);
            delect.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                    db.delete("Book", null, null);
                    notifyDataSetChanged();
                    Toast.makeText(parent.getContext(), "清除历史", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(viewType == ITEM_TYPE.ITEM_TYPE_Search.ordinal()){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list, parent, false);
        }
        else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movielist_item, parent, false);
        }

        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    // FIXME: 2018/9/4 可能是计算问题，新加的历史纪录未设置点击事件时点击会进入热搜列表的电影
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){
        if(position != 0){
            if(allCaseNum() == 0) {                                         //不能和上句用&&合并，注意数据库为0但定位为0情况
                final Movie movie = movie_List.get(position - 1);
                holder.movieNum.setText(Integer.toString(position));
                holder.movieName.setText(movie.getTitle());

                holder.movieView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        String id = id_list.get(position - 1);
                        Toast.makeText(v.getContext(), id, Toast.LENGTH_SHORT).show();
                        Toast.makeText(v.getContext(), "wulishi", Toast.LENGTH_SHORT).show();
                        MovieDetailActivity.actionStart(v.getContext(), id);
                    }
                });

            }
            else{
                if(position <= allCaseNum()) {
                    String history_Movie = history_List.get(position-1);
                    holder.movieNum.setText("");
                    holder.movieName.setText(history_Movie);


                    // FIXME: 2018/9/5 未实现：点击将历史item名发至输入框
                    holder.movieView.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View v){
//                            View view = LayoutInflater.from(App.getContext()).inflate(R.layout.activity_search, null ,false);
//                            EditText e = (EditText) view.findViewById(R.id.search_edit);

//                            e.setText(history_List.get(position-1));

                            Toast.makeText(v.getContext(), "点击历史", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else if(position != (allCaseNum()+1)) {
                    final Movie movie = movie_List.get(position-allCaseNum()-2);
                    holder.movieNum.setText(Integer.toString(position - allCaseNum() - 1));
                    holder.movieName.setText(movie.getTitle());
                    if(position >= (allCaseNum() + 2)){
                        holder.movieView.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v){
                                String id = id_list.get(position - allCaseNum() - 2);
                                Toast.makeText(v.getContext(), id, Toast.LENGTH_SHORT).show();
                                Toast.makeText(v.getContext(), "youlishi", Toast.LENGTH_SHORT).show();
                                MovieDetailActivity.actionStart(v.getContext(), id);
                            }
                        });
                    }
                }
            }
        }

    }

    @Override
    public int getItemCount(){
        if(allCaseNum() == 0)
            return movie_List.size() + 1;
        else{
            return movie_List.size() + allCaseNum() + 2;
        }
    }

}
