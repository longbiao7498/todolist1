package com.example.materialtest;

import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.materialtest.tool.PackageTool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.Inflater;


/**
 * Created by 龙标F on 2017/1/3 0003.
 */

public class SecondActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    private DrawerLayout mDrawerLayout;
    private SecondActivityAdapter adapter;
    private RecyclerView secondActivityRecyclerView;
    private FloatingActionButton floatingActionButton;
    private ArrayList<TodoItem> todoItemList=new ArrayList<TodoItem>();
    private DateChangeReceiver dateChangeReceiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        dbHelper=new MyDatabaseHelper(this,"ListData.db",null,1);
        intentFilter=new IntentFilter();
        intentFilter.addAction("com.example.broadcast.DATE_CHANGE");
        dateChangeReceiver=new DateChangeReceiver();
        registerReceiver(dateChangeReceiver,intentFilter);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);//没有设置toolbar就只会是actionBar；
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBar actionBar =getSupportActionBar();
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navigationView.setCheckedItem(R.id.nav_undo);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                switch(item.getItemId()){
                    case R.id.nav_mail://写成了R.id.mail
                        Intent intent=new Intent(SecondActivity.this,MainActivity.class);
                        startActivity(intent);
                        break;
                    default:
                }
                return true;
            }
        });
        init();///////////////
        secondActivityRecyclerView=(RecyclerView)findViewById(R.id.second_activity_recycler_view);
        adapter=new SecondActivityAdapter(todoItemList);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        secondActivityRecyclerView.setLayoutManager(layoutManager);
        secondActivityRecyclerView.setAdapter(adapter);
        secondActivityRecyclerView.setNestedScrollingEnabled(false);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.second_activity_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SecondActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.restore:
                checkDate();
                break;
            case R.id.zoom:
                showListDialog();
                Toast.makeText(SecondActivity.this,"zoom",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
    private void init(){
        PackageTool.packageAll(todoItemList,dbHelper,SecondActivity.this);
        }
    private void checkDate(){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("MainList",null,null,null,null,null,null);
        if(cursor!=null&&cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndex("id"));
                int isDone=cursor.getInt(cursor.getColumnIndex("isDone"));
                int isRepeat=cursor.getInt(cursor.getColumnIndex("isRepeat"));
                int repeatNum=cursor.getInt(cursor.getColumnIndex("repeatNum"));
                String mainText=cursor.getString(cursor.getColumnIndex("mainText"));
                int timeType=cursor.getInt(cursor.getColumnIndex("timeType"));
                double longTime=cursor.getDouble(cursor.getColumnIndex("longTime"));
                int notification=cursor.getInt(cursor.getColumnIndex("notification"));
                int operationType=cursor.getInt(cursor.getColumnIndex("operationType"));
                int extraDataType=cursor.getInt(cursor.getColumnIndex("extraDataType"));
                Log.d("SecondActivity",""+id+" "+isDone+isRepeat+repeatNum+mainText+timeType+" "+longTime+" "+notification+operationType+extraDataType);
            }while (cursor.moveToNext());
        }
        cursor.close();;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(dateChangeReceiver);
    }

    class DateChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            PackageTool.packageAll(todoItemList,dbHelper,SecondActivity.this);
            adapter.notifyDataSetChanged();
        }
    }
    /* 复写Builder的create和show函数，可以在Dialog显示前实现必要设置
 * 例如初始化列表、默认选项等
 * @create 第一次创建时调用
 * @show 每次显示时调用
 */
    private void showListDialog() {
        final String[] items = { "我是1","我是2","我是3","我是4" };
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(SecondActivity.this){

                    @Override
                    public AlertDialog create() {
                        items[0] = "我是No.1";
                        return super.create();
                    }

                    @Override
                    public AlertDialog show() {
                        items[1] = "我是No.2";
                        return super.show();
                    }
                };
        listDialog.setTitle("我是一个列表Dialog");
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // ...To-do
            }
        });
    /* @setOnDismissListener Dialog销毁时调用
     * @setOnCancelListener Dialog关闭时调用
     */
        listDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                Toast.makeText(getApplicationContext(),
                        "Dialog被销毁了",
                        Toast.LENGTH_SHORT).show();
            }
        });
        listDialog.show();
    }
    private void showTimeandDate(){
        AlertDialog alertDialog=new AlertDialog.Builder(this).create();
        final View dialogView= LayoutInflater.from(SecondActivity.this).inflate(R.layout.simpletime_picker,null);
        alertDialog.setView(dialogView);
        alertDialog.show();
        WindowManager.LayoutParams params=alertDialog.getWindow().getAttributes();
        float density = getDensity(SecondActivity.this);
        params.width=1000;
        params.height=1080;
        params.gravity= Gravity.CENTER;
        alertDialog.getWindow().setAttributes(params);
        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (dialogView.getId()){
                    case R.id.today_sometime_simple_time_picker:
                        break;
                    case R.id.tomorrow_sometime_simpletime_picker:
                        break;
                    case R.id.simpletime_picker_tomorrow:
                        break;
                    case R.id.simpletime_picker_tomorrow_afternoon:
                        break;
                    case R.id.simpletime_picker_tomorrow_evening:
                        break;
                    case R.id.simpletime_picker_nextweek:
                        break;
                    case R.id.simpletime_picker_pick_time:
                        break;
                    case R.id.simpletime_picker_notime:
                        break;
                    default :
                        break;
                }
            }
        };
    }
    private float getDensity(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.density;
    }


}

