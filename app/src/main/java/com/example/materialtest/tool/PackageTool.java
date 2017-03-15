package com.example.materialtest.tool;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.materialtest.MyDatabaseHelper;
import com.example.materialtest.R;
import com.example.materialtest.TodoItem;
import com.example.materialtest.constants.MainActivity_view_constants;
import com.example.materialtest.constants.TimeType_constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by 龙标F on 2017/2/21 0021.
 */

public class PackageTool {
    private static long startTimeToday;
    public static void packageAll(ArrayList<TodoItem> todoItemList,MyDatabaseHelper dbHelper,Context context){
        todoItemList.clear();
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        initTime();
        for(int i=1;i<6;i++)
        {
            long startTime=0;
            long endTime=0;
            List containList=new ArrayList();
            switch (i){
                case MainActivity_view_constants.TIME_SECTION_TODAY: //today
                    startTime=startTimeToday;
                    endTime=startTimeToday+24*60*60*1000;
                    break;
                case MainActivity_view_constants.TIME_SECTION_TOMMORW://tomorrow
                    startTime=startTimeToday+24*60*60*1000;
                    endTime=startTimeToday+24*60*60*1000*2;;
                    break;
                case MainActivity_view_constants.TIME_SECTION_INWEEK://in week
                    startTime=startTimeToday+24*60*60*1000*2;
                    endTime=startTimeToday+24*60*60*1000*7;
                    break;
                case MainActivity_view_constants.TIME_SECTION_FURTURE://future
                    startTime=startTimeToday+24*60*60*1000*7;
                    endTime=startTimeToday+24*60*60*1000*365;
                    break;

            }

            Cursor cursor;
            switch (i){
                case MainActivity_view_constants.TIME_SECTION_FURTURE:
                    cursor=db.rawQuery("SELECT * FROM MainList WHERE isDone=0 AND longTime >= ? " +
                            "ORDER BY longTime",new String[]{""+startTime});
                    break;
                case MainActivity_view_constants.TIME_SECTION_NOTIME:
                    cursor=db.rawQuery("SELECT * FROM MainList WHERE isDone=0 AND timeType=0 " +////////
                            "ORDER BY updateTime",null);
                    break;
                default:
                    cursor=db.rawQuery("SELECT * FROM MainList WHERE isDone=0 AND longTime >= ? AND longTime < ? " +
                            "ORDER BY longTime",new String[]{""+startTime,""+endTime});
                    break;
            }
            int k=0;

            if(cursor!=null&&cursor.moveToFirst()){
                do{
                    int id=cursor.getInt(cursor.getColumnIndex("id"));
                    //int isDone=cursor.getInt(cursor.getColumnIndex("isDone"));
                    int repeat=cursor.getInt(cursor.getColumnIndex("isRepeat"));
                    int repeatNum=cursor.getInt(cursor.getColumnIndex("repeatNum"));
                    String mainText=cursor.getString(cursor.getColumnIndex("mainText"));
                    int timeType=cursor.getInt(cursor.getColumnIndex("timeType"));
                    double longTime=cursor.getDouble(cursor.getColumnIndex("longTime"));
                    int notification=cursor.getInt(cursor.getColumnIndex("notification"));
                    int extraDataType=cursor.getInt(cursor.getColumnIndex("extraDataType"));//extraDataType
                    int priority=cursor.getInt(cursor.getColumnIndex("priority"));
                    boolean isRepeat;

                    if(repeat==0){
                        isRepeat=false;
                    }
                    else {
                        isRepeat=true;
                    }
                    Calendar calendar=Calendar.getInstance();
                    calendar.setTimeInMillis((long)longTime);
                    String todoTime;
                    SimpleDateFormat df;
                    switch (timeType){
                        case TimeType_constants.NOTIME:
                            todoTime="";
                            break;
                        case TimeType_constants.EXACTTIME:
                            df = new SimpleDateFormat("yyyy/MM/dd EE HH:mm");
                            todoTime=df.format(calendar.getTime());
                            break;
                        case TimeType_constants.SOMEDAY:
                            df = new SimpleDateFormat("yyyy/MM/dd EE");
                            todoTime=df.format(calendar.getTime());
                            break;
                        case TimeType_constants.SOMEDAY_MORNING:
                            df = new SimpleDateFormat("yyyy/MM/dd EE");
                            todoTime=df.format(calendar.getTime())+context.getString(R.string.time_type_text_morning);
                            break;
                        case TimeType_constants.SOMEDAY_FORENOON:
                            df = new SimpleDateFormat("yyyy/MM/dd EE");
                            todoTime=df.format(calendar.getTime())+context.getString(R.string.time_type_text_forenoon);
                            break;
                        case TimeType_constants.SOMEDAY_NOOON:
                            df = new SimpleDateFormat("yyyy/MM/dd EE");
                            todoTime=df.format(calendar.getTime())+context.getString(R.string.time_type_text_noon);
                            break;
                        case TimeType_constants.SOMEDAY_AFTERNOON:
                            df = new SimpleDateFormat("yyyy/MM/dd EE");
                            todoTime=df.format(calendar.getTime())+context.getString(R.string.time_type_text_afternoon);
                            break;
                        case TimeType_constants.SOMEDAY_EVENFALL:
                            df = new SimpleDateFormat("yyyy/MM/dd EE");
                            todoTime=df.format(calendar.getTime())+context.getString(R.string.time_type_text_evenfall);
                            break;
                        case TimeType_constants.SOMEDAY_EVENING:
                            df = new SimpleDateFormat("yyyy/MM/dd EE");
                            todoTime=df.format(calendar.getTime())+context.getString(R.string.time_type_text_evening);
                            break;
                        default:
                            todoTime="";
                            break;

                    }
                    int j;
                    if(k==0){
                        j=i;
                    }else{
                        j=0;
                    }
                    Log.d("PackageTool",mainText.toString());
                    if(isRepeat==false||!containList.contains(repeatNum)){
                        TodoItem todoItem=new TodoItem(id,mainText,todoTime,longTime,notification,priority,extraDataType,isRepeat,repeatNum,timeType,j);
                        todoItemList.add(todoItem);
                        containList.add(repeatNum);
                        Log.d("PackageTool",todoItem.toString());
                    }
                    k++;
                }while (cursor.moveToNext());
            }
            cursor.close();
        }

    }
    public static void updateAll(ArrayList<TodoItem> todoItemList, MyDatabaseHelper dbHelper,Context mContext){
        packageAll(todoItemList,dbHelper,mContext);
    }
    public static void initTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
		//calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        startTimeToday= calendar.getTimeInMillis();
        Log.d("PackageTool Time","short:"+startTimeToday+"long:"+calendar.getTimeInMillis()+"systemTime"+System.currentTimeMillis());
    }
}
