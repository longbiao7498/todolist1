package com.example.materialtest;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 龙标F on 2017/2/24 0024.
 */

public class CheckActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    private int id;
    private String mainText;
    private String todoTime;
    private long longTime;
    private int isRepeat;
    private int isDone;
    private int extraDataType;
    private int notification;
    private int operationType;
    //这里还有很多其他的数据，包括操作数据，附件文件的路径，重复数据等等；

    private EditText mainTextText;
    private EditText todoTimeText;
    private ToggleButton isRepeatButton;
    private ToggleButton isDoneButton;
    private EditText extraDataTypeText;
    private EditText levelText;
    private EditText operationTypeText;
    private Button saveButton;
    private Button deleteButton;
    private Button cancelButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        Intent intent=getIntent();
        id=intent.getIntExtra("id",0);
        Log.d("CheckActivity",""+id);
        dbHelper=new MyDatabaseHelper(this,"ListData.db",null,1);
        mainTextText=(EditText)findViewById(R.id.check_main_text);
        todoTimeText=(EditText)findViewById(R.id.check_todo_time);
        isRepeatButton=(ToggleButton)findViewById(R.id.check_repeat_button);
        isDoneButton=(ToggleButton)findViewById(R.id.check_done_button);
        extraDataTypeText=(EditText)findViewById(R.id.check_extra_data_type);
        levelText=(EditText)findViewById(R.id.check_level);
        operationTypeText=(EditText)findViewById(R.id.check_operation_type);
        saveButton=(Button)findViewById(R.id.check_save_button);
        deleteButton=(Button)findViewById(R.id.check_delete_button);
        cancelButton=(Button)findViewById(R.id.check_cancel_button);
        init(id);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDateChangeBroadcast();
                deleteDate(id);
                finish();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tini();
                updateData(id);
                sendDateChangeBroadcast();
                finish();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void init(int id){
        if(id==0){
            Toast.makeText(CheckActivity.this,"Error",Toast.LENGTH_SHORT).show();
            finish();
        }
        queryDate(id);
        mainTextText.setText(mainText);
        todoTimeText.setText(todoTime);
        if(isRepeat==0){
            isRepeatButton.setChecked(false);
        }else {
            isRepeatButton.setChecked(true);
        }
        if(isDone==0){
            isDoneButton.setChecked(false);
        }else {
            isDoneButton.setChecked(true);
        }
        extraDataTypeText.setText(""+extraDataType);
        levelText.setText(""+notification);
        operationTypeText.setText(""+operationType);
    }
    private void tini(){
        mainText=mainTextText.getText().toString();
        todoTime=todoTimeText.getText().toString();
        if(isRepeatButton.isChecked()==false){
            isRepeat=0;
        }
        else {
            isRepeat=1;
        }
        if(isDoneButton.isChecked()==false){
            isDone=0;
        }
        else{
            isDone=1;
        }
        extraDataType=Integer.parseInt(extraDataTypeText.getText().toString());
        notification=Integer.parseInt(levelText.getText().toString());
        operationType=Integer.parseInt(operationTypeText.getText().toString());
        longTime=getLongTime(todoTime);
    }
    private void queryDate(int id){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM MainList WHERE id=?" +
                "ORDER BY longTime",new String[]{""+id});
        if(cursor!=null&&cursor.moveToFirst()){
            isDone=cursor.getInt(cursor.getColumnIndex("isDone"));
            isRepeat=cursor.getInt(cursor.getColumnIndex("isRepeat"));
            extraDataType=cursor.getInt(cursor.getColumnIndex("extraDataType"));
            mainText=cursor.getString(cursor.getColumnIndex("mainText"));
            todoTime=cursor.getString(cursor.getColumnIndex("todoTime"));
            longTime=(long)cursor.getDouble(cursor.getColumnIndex("longTime"));
            notification=cursor.getInt(cursor.getColumnIndex("notification"));
            operationType=cursor.getInt(cursor.getColumnIndex("operationType"));
        }
        cursor.close();/////////////////////////////////////////////////////别忘了关闭
    }
    private void updateData(int id){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("mainText",mainText);
        values.put("todoTime",todoTime);
        values.put("longTime",(double)longTime);
        values.put("isDone",isDone);
        values.put("isRepeat",isRepeat);
        values.put("repeatNum",extraDataType);
        values.put("notification",notification);
        values.put("operationType",operationType);
        db.update("MainList",values,"id=?",new String[]{""+id});
    }
    private void deleteDate(int id){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete("MainList","id=?",new String[]{""+id});
    }
    private long getLongTime(String todoTime){
        int i=0;
        int[] toDoTimeArray=new int[6];
        Matcher m= Pattern.compile("\\d+").matcher(todoTime);
        while(m.find()){
            toDoTimeArray[i]=Integer.parseInt(m.group());
            int todoTimes=toDoTimeArray[i];
            Log.d("AddActivity getLongTime",i+" "+todoTimes);
            i++;
            if(i==6){
                break;
            }
        }
        Calendar calendar=Calendar.getInstance();
        calendar.set(toDoTimeArray[0],toDoTimeArray[1]-1,toDoTimeArray[2],toDoTimeArray[3],toDoTimeArray[4],toDoTimeArray[5]);
//        Log.d("AddActivity getLongTime",""+toDoTimeArray[0]+toDoTimeArray[1])+toDoTimeArray[2]
//                +toDoTimeArray[3]+toDoTimeArray[4]+toDoTimeArray[5]);
        longTime=calendar.getTimeInMillis();
        Log.d("AddActivity getLongTime",longTime+"");
        return longTime;
    }
    private void sendDateChangeBroadcast(){
        Intent intent=new Intent("com.example.broadcast.DATE_CHANGE");
        sendBroadcast(intent);
    }
}
