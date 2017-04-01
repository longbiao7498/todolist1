package com.example.materialtest.tool;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.materialtest.MyDatabaseHelper;
import com.example.materialtest.ToDoItemComplex;

/**
 * Created by 龙标F on 2017/3/17 0017.
 */

public class QueryTool {
    public static ToDoItemComplex querayItemData(int id, MyDatabaseHelper dbHelper){
        //if the data be changed by cloud  operation,and the mainView didn't change right now ,so ,
        // when i query data by id,is nothing ,what can i do for it
        int repeatNum;
        int isDone;
        int  isRepeat;
        String mainText;
        long longTime;
        int timeType;
        String repeatData=null;
        int notification;
        int priority;
        int extraDataType;
        String extraData=null;//
        int operationType;
        String operationData=null;//这里的operationData并不是真的，可能是对
        String remarks;
        int category;


        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM MainList WHERE id=?" +
                "ORDER BY longTime",new String[]{""+id});
        if(cursor!=null&&cursor.moveToFirst()){
            isDone=cursor.getInt(cursor.getColumnIndex("isDone"));
            isRepeat=cursor.getInt(cursor.getColumnIndex("isRepeat"));
            repeatNum=cursor.getInt(cursor.getColumnIndex("repeatNum"));//
            longTime=(long)cursor.getDouble(cursor.getColumnIndex("longTime"));
            timeType=cursor.getInt(cursor.getColumnIndex("timeType"));
            mainText=cursor.getString(cursor.getColumnIndex("mainText"));
            priority=cursor.getInt(cursor.getColumnIndex("priority"));
            notification=cursor.getInt(cursor.getColumnIndex("notification"));
            operationType=cursor.getInt(cursor.getColumnIndex("operationType"));
            operationData=cursor.getString(cursor.getColumnIndex("operationData"));//
            extraDataType=cursor.getInt(cursor.getColumnIndex("extraDataType"));
            extraData=cursor.getString(cursor.getColumnIndex("extraData"));//
            remarks=cursor.getString(cursor.getColumnIndex("remarks"));//
            category=cursor.getInt(cursor.getColumnIndex("category"));//13

            if(isRepeat==1){
                Cursor cursor1=db.rawQuery("SELECT * FROM RepeatList WHERE repeatNum=?",new String[]{""+repeatNum});
                if(cursor1!=null&&cursor1.moveToFirst()){
                    repeatData=cursor1.getString(cursor1.getColumnIndex("repeatData"));
                }else {
                    Log.d("QueryTool","can't query repeatData from RepeatList");
                }
                cursor1.close();
            }else{
                repeatData=null;
            }
            cursor.close();
            return new ToDoItemComplex(id,repeatNum,isDone,isRepeat,mainText,longTime,timeType,repeatData,notification,priority
                    ,extraDataType,extraData,operationType,operationData,remarks,category);
        }else{
            Log.d("QueryTool","can't query Data from MainList");
            cursor.close();
            return null;
        }



    }
}
