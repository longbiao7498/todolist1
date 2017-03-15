package com.example.materialtest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by 龙标F on 2017/2/18 0018.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final  String CREATE_LIST="CREATE TABLE MainList (" +
            "id integer  primary key autoincrement," +
            "timeType integer NOT NULL DEFAULT 0,"+///////////////
            "longTime real NOT NULL DEFAULT 0,"+
            "updateTime real NOT NULL,"+//////////////////////////
            "isDone integer NOT NULL DEFAULT 0," +///0是没有完成，1才是完成
            "isRepeat integer NOT NULL DEFAULT 0,"+
            "repeatNum integer NOT NULL DEFAULT 0 ,"+
            "mainText text,"+
            "notification integer NOT NULL DEFAULT 1,"+
            "category integer NOT NULL DEFAULT 0,"+//categoryyyyyy
            "priority integer NOT NULL DEFAULT 1,"+////优先级：1,2,3,4,5
            "operationType integer NOT NULL DEFAULT 0,"+
            "operationData text,"+
            "remarks text,"+
            "extraDataType integer NOT NULL DEFAULT 0,"+
            "extraData text)";//16
    private static final String CREATE_LIST_REPEAT="CREATE TABLE  RepeatList(" +
            "repeatNum integer  primary key autoincrement,"+
            "isDone integer NOT NULL DEFAULT 0," +
            "repeatData text,"+
            "mainText text,"+
            "notification integer NOT NULL DEFAULT 1,"+
            "category integer NOT NULL DEFAULT 0,"+//categoryyyyyy
            "priority integer NOT NULL DEFAULT 1,"+
            "todoTime text ,"+
            "timeType integer NOT NULL DEFAULT 1,"+
            "updateTime real NOT NULL,"+
            "operationType integer NOT NULL DEFAULT 0,"+
            "operationData text,"+
            "remarks text,"+
            "extraDataType NOT NULL DEFAULT 0,"+
            "extraData text)";//15
    private Context context;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LIST);
        db.execSQL(CREATE_LIST_REPEAT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
