package com.example.xiaoh.doubanmovie;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * 历史搜索信息的数据库定义
 */
public class HistorySaveHelper extends SQLiteOpenHelper {

    public static final String CREATE_BOOK = "create table Book (\n" +
            "        id integer primary key autoincrement,\n" +
            "        name text\n" +
            "    )";
    private Context context;

    public HistorySaveHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        this.context = context;
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_BOOK);
        Toast.makeText(context, "Create Successful", Toast.LENGTH_SHORT).show();
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}

}
