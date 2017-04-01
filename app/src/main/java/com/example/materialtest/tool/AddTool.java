package com.example.materialtest.tool;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.materialtest.AddActivity;
import com.example.materialtest.MyDatabaseHelper;
import com.example.materialtest.constants.TodoItem_constants;

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
    public static void addDateRepeat(String mainText,int category,int timeType,long todoTime,String repeatData,
                                     int notification,int priority,int operationType,
                                     String operationData, int extraDataType, String extraData,String remarks,//12
                                     MyDatabaseHelper dbHelper, Context mContext){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        double updateTime=(double)calendar.getTimeInMillis();
        values.put("mainText",mainText);
        values.put("isDone",0);
        values.put("todoTime",""+todoTime);
        values.put("timeType",timeType);
        values.put("repeatData",repeatData);
        values.put("updateTime",updateTime);
        values.put("category",category);
        values.put("notification",notification);
        values.put("priority",priority);
        values.put("operationType",operationType);
        values.put("operationData",operationData);
        values.put("extraDataType",extraDataType);
        values.put("extraData",extraData);
        values.put("remarks",remarks);//14=12+2(isDone,updataTime)
        db.insert("RepeatList",null,values);
        Cursor cursor=db.rawQuery("SELECT * FROM RepeatList WHERE updateTime = ? " ,new String[]{""+updateTime});///两个错误
        ////假如这里断了，无法根据更新时间查到repeatNum怎么办？，在这里线程转移，同时该重复数据被修改
        //long longTime=Long.getLong(todoTime);
        int repeatDateint=Integer.parseInt(repeatData);
        if(cursor!=null&&cursor.moveToFirst()){
            int repeatNum=cursor.getInt(cursor.getColumnIndex("repeatNum"));////时效性
            Calendar calendar1=Calendar.getInstance();
            calendar1.setTimeInMillis(todoTime);
            Calendar calendar2=Calendar.getInstance();
            calendar2.setTimeInMillis(System.currentTimeMillis());
            calendar2.add(Calendar.DAY_OF_MONTH,7);
            calendar2.set(Calendar.HOUR_OF_DAY,0);
            calendar2.set(Calendar.MINUTE,0);
            calendar2.set(Calendar.SECOND,1);
            Long endTime=calendar2.getTimeInMillis();//////////
            for(int i=0;i<8;i++){
                long longTime=calendar1.getTimeInMillis();
                AddTool.addDate(mainText,category,timeType,longTime,notification,priority, TodoItem_constants.ISREPEAT_1,repeatNum,
                            operationType,operationData,extraDataType,extraData,remarks,dbHelper,mContext);
                if(longTime>endTime){
                    break;
                }
                switch(repeatDateint){
                    case TodoItem_constants.EVERYDAY:
                        calendar1.add(Calendar.DAY_OF_MONTH,1);
                        break;
                    case TodoItem_constants.EVERYWEEK:
                        calendar1.add(Calendar.WEEK_OF_MONTH,1);////还有一个weekofyear，是什么
                        break;
                    case TodoItem_constants.EVERYMONTH:
                        calendar1.add(Calendar.MONTH,1);
                        break;
                    case TodoItem_constants.EVERYYEAR:
                        calendar1.add(Calendar.YEAR,1);
                        break;
                }
            }
            sendDateChangeBroadcast(mContext);
        }else {
            Log.d("Addtool","error in repeatListAddToMainlist can't find repeatnum by updateTime ");
        }
        cursor.close();
    }

    private static void sendDateChangeBroadcast(Context mContext){
        Intent intent=new Intent("com.example.broadcast.DATE_CHANGE");
        mContext.sendBroadcast(intent);
    }
}
