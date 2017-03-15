package com.example.materialtest.tool;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.materialtest.MyDatabaseHelper;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 龙标F on 2017/2/23 0023.
 */

public class AddTool {
    public static void addDate(String mainText,int category,int timeType,long longTime,
                               int notification,int priority, int isRepeat, int repeatNum,int operationType,
                               String operationData, int extraDataType, String extraData,String remarks,
                               MyDatabaseHelper dbHelper, Context mContext){///13+2
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            ContentValues values=new ContentValues();
            Calendar calendar=Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            values.put("mainText",mainText);
            values.put("isDone",0);
            values.put("longTime",(double)longTime);
            values.put("timeType",timeType);
            values.put("updateTime",(double)calendar.getTimeInMillis());
            values.put("isRepeat",isRepeat);
            values.put("repeatNum",repeatNum);
            values.put("category",category);
            values.put("notification",notification);
            values.put("priority",priority);
            values.put("operationType",operationType);
            values.put("operationData",operationData);
            values.put("extraDataType",extraDataType);
            values.put("extraData",extraData);
            values.put("remarks",remarks);//15
            db.insert("MainList",null,values);
            sendDateChangeBroadcast(mContext);
    }
    public static void addDateRepeat(String mainText,int category,int timeType,String todoTime,String repeatData,
                                     int notification,int priority,int operationType,
                                     String operationData, int extraDataType, String extraData,String remarks,//12
                                     MyDatabaseHelper dbHelper, Context mContext){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        //values.put("repeatNum",)
        values.put("mainText",mainText);
        values.put("isDone",0);
        values.put("todoTime",todoTime);
        values.put("timeType",timeType);
        values.put("repeatData",repeatData);
        values.put("updateTime",(double)calendar.getTimeInMillis());
        values.put("category",category);
        values.put("notification",notification);
        values.put("priority",priority);
        values.put("operationType",operationType);
        values.put("operationData",operationData);
        values.put("extraDataType",extraDataType);
        values.put("extraData",extraData);
        values.put("remarks",remarks);//14=12+2(isDone,updataTime)
        db.insert("RepeatList",null,values);

    }
    private static  void repeatListAddToMainlist(){
        
    }
    private static void sendDateChangeBroadcast(Context mContext){
        Intent intent=new Intent("com.example.broadcast.DATE_CHANGE");
        mContext.sendBroadcast(intent);
    }
}
