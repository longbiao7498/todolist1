package com.example.materialtest;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.materialtest.constants.Database_constants;
import com.example.materialtest.constants.TimeType_constants;
import com.example.materialtest.constants.TodoItem_constants;
import com.example.materialtest.tool.AddTool;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;


/**
 * Created by 龙标F on 2017/2/22 0022.
 */

public class AddActivity extends AppCompatActivity {
    private int timeType=0;////
    private long longTime=0;
    private int isRepeat=0;
    private String repeatData=null;
    private String mainText=null;
    private int category=0;///////全部改变为category
    private int notification=1;
    private int priority=1;////////
    private int operationType=0;
    private String remarks=null;/////
    private String operationData=null;
    private int extraDataType=0;
    private String extraData=null;
    private MyDatabaseHelper dbHelper;
    private DatePickerDialog mDataPicker;
    private TimePickerDialog mTimePicker;

    private EditText mainTextText;
    private TextView todoTimeText;
    private TextView notificationText;
    private Switch isRepeatSwitch;
    private TextView repeatDataText;
    private Switch isOperationSwitch;
    private TextView operationDataText;
    private TextView extraDataText;
    private TextView priorityText;
    private EditText remarksText;
    private TextView categoryText;

    private LinearLayout todoTimeLayout;
    private RelativeLayout notificationLayout;
    private RelativeLayout repeatLayout;
    private RelativeLayout operationLayout;
    private RelativeLayout extraDataLayout;
    private RelativeLayout priorityLayout;
    private RelativeLayout remarksLayout;
    private RelativeLayout categoryLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        dbHelper=new MyDatabaseHelper(this,"ListData.db",null, Database_constants.DATABASEVERSION);
        Toolbar toolbar=(Toolbar)findViewById(R.id.activity_add_toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        final FloatingActionButton floatingActionButton=(FloatingActionButton)findViewById(R.id.activity_add_fab);

        mainTextText=(EditText) findViewById(R.id.activity_add_main_text);
        todoTimeText=(TextView)findViewById(R.id.activity_add_todoTime);
        notificationText=(TextView)findViewById(R.id.activity_add_notification);
        isRepeatSwitch=(Switch) findViewById(R.id.activity_add_isRepeat);
        repeatDataText=(TextView)findViewById(R.id.activity_add_repeatData);
        isOperationSwitch=(Switch)findViewById(R.id.activity_add_isOperation);
        operationDataText=(TextView)findViewById(R.id.activity_add_operationData);
        extraDataText=(TextView)findViewById(R.id.activity_add_extraData);
        priorityText=(TextView)findViewById(R.id.activity_add_priority);
        remarksText=(EditText)findViewById(R.id.activity_add_remarks);
        categoryText=(TextView)findViewById(R.id.activity_add_category); //11,比Layout多两个switch，mainText没有Layout

        todoTimeLayout=(LinearLayout)findViewById(R.id.layout_todoTime);
        notificationLayout=(RelativeLayout)findViewById(R.id.layout_notification);
        repeatLayout=(RelativeLayout)findViewById(R.id.layout_Repeat);
        operationLayout=(RelativeLayout)findViewById(R.id.layout_operation);
        extraDataLayout=(RelativeLayout)findViewById(R.id.layout_extraData);
        priorityLayout=(RelativeLayout)findViewById(R.id.layout_priority);
        remarksLayout=(RelativeLayout)findViewById(R.id.layout_remarks);
        categoryLayout=(RelativeLayout)findViewById(R.id.layout_Category);///8//或许这里也不需要Layout作为外部的触摸区域区分吧

        ///2017/3/12日，接下里要先把时间的事情设置搞定，然后换用另外的Dialog处理方式，然后加入分类，优先级，备注。
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                saveData();
                finish();
            }
        });

        todoTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeandDate();
            }
        });
        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNotification();
            }
        });
        repeatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIsRepeat();
            }
        });
        isRepeatSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRepeatSwitch.isChecked()){
                    isRepeat=1;
                }else{
                    isRepeat=0;
                }
            }
        });
        priorityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPriority();
            }
        });
        operationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOperation();
            }
        });
        extraDataLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setExtraData();
            }
        });
        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){

                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                beforeFinish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        beforeFinish();
    }
    private  void beforeFinish(){
        if(!TextUtils.isEmpty(mainTextText.getText())){
            AlertDialog.Builder builder=new AlertDialog.Builder(AddActivity.this);
            builder.setCancelable(false);
            builder.setMessage(getString(R.string.savedata_message));
            builder.setPositiveButton(getString(R.string.save_data), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getData();
                    saveData();
                    dialog.dismiss();
                    finish();
                }
            });
            builder.setNegativeButton(getString(R.string.no_save_data), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            builder.create().show();
        }else {
            finish();
        }
    }

    private void getData(){
        mainText=mainTextText.getText().toString();
        if(isRepeatSwitch.isChecked()){
            isRepeat=1;
        }else {
            isRepeat=0;
        }
        if(!TextUtils.isEmpty(remarksText.getText())){
            remarks=remarksText.getText().toString();
        }
        //还有等级，操作以及附件的类型与数据要赋值，暂时不赋值；
    }
    private void saveData(){
        //判断是否重复，再调用不同的方法来添加数据；，暂时不做处理；
        if(isRepeat==TodoItem_constants.ISREPEAT_1){
            AddTool.addDateRepeat(mainText,category,timeType,longTime,repeatData,notification,
                    priority,operationType,operationData,extraDataType,extraData,remarks,dbHelper,AddActivity.this);
        }else{
            AddTool.addDate(mainText,category,timeType,longTime,notification,priority,isRepeat,0,
                    operationType,operationData,extraDataType,extraData,remarks,dbHelper,AddActivity.this);
        }

    }

    private void setNotification(){
        final String noVoice=getString(R.string.notification_no_voice);
        final String nothing=getString(R.string.notification_nothing);
        final String voice=getString(R.string.notification_voice);
        final String items[];
        if(timeType==0){
            items=new String[]{nothing};
        }else{
            items=new String[]{nothing,noVoice,voice};
        }
        Arrays.sort(items);
        int id=Arrays.binarySearch(items,notificationText.getText().toString());
        if(id<0){
            id=0;
        }
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle(getString(R.string.choose_notification)); //设置标题
        builder.setSingleChoiceItems(items,id,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(items[which].equals(nothing)){
                    notification=1;
                    notificationText.setText(nothing);
                }
                if(items[which].equals(noVoice)){
                    notification=2;
                    notificationText.setText(noVoice);
                }
                if(items[which].equals(voice)){
                    notification=3;
                    notificationText.setText(voice);
                }
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void setIsRepeat(){
        final String everyday=getString(R.string.repeat_everyday);
        final String everyweek=getString(R.string.repeat_every_week);
        final String everymonth=getString(R.string.repeat_every_month);
        String everyyear=getString(R.string.repeat_every_year);
        String noRepeat=getString(R.string.repeat_no_repeat);
        final String items2[]={noRepeat,everyday,everyweek,everymonth,everyyear};
        final String items1[]={noRepeat};
        final String items[];
        if(timeType==0){
            items=items1;
        }else{
            items=items2;
        }
        Arrays.sort(items);
        int id=Arrays.binarySearch(items,repeatDataText.getText().toString());
        if(id<0){
            id=0;
        }
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle(getString(R.string.choose_repeat)); //设置标题
        builder.setSingleChoiceItems(items,id,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(items[which].equals(getString(R.string.repeat_no_repeat))){
                    isRepeat=TodoItem_constants.ISREPEAT_0;
                    repeatData=null;
                    isRepeatSwitch.setChecked(false);
                }else {
                    if(items[which].equals(everyday)){
                        repeatData=""+TodoItem_constants.EVERYDAY;
                    }
                    if(items[which].equals(everyweek)){
                        repeatData=""+TodoItem_constants.EVERYWEEK;
                    }
                    if(items[which].equals(everymonth)){
                        repeatData=""+TodoItem_constants.EVERYMONTH;
                    }
                    if(items[which].equals(everymonth)){
                        repeatData=""+TodoItem_constants.EVERYYEAR;
                    }
                    isRepeatSwitch.setChecked(true);
                }
                isRepeat=TodoItem_constants.ISREPEAT_1;
                repeatDataText.setText(items[which]);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void setPriority(){
        final String items[]={getString(R.string.priority_1),getString(R.string.priority_2),
                getString(R.string.priority_3),getString(R.string.priority_4),
                getString(R.string.priority_5)};
        Arrays.sort(items);
        int id=Arrays.binarySearch(items,priorityText.getText().toString());
        if(id<0){
            return;
        }
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle(getString(R.string.choose_priority)); //设置标题
        builder.setSingleChoiceItems(items,id,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                priorityText.setText(items[which]);
                priority=which+1;
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    //设置操作和附件后要自动向mainText里面添加正文，但是现在暂时不用
    //而分类
    private void setExtraData(){
        final String email=getString(R.string.extradata_email);
        final String phoneNumber=getString(R.string.extradata_phone_number);
        final String image=getString(R.string.extradata_image);
        final String appNotification=getString(R.string.extradata_app_notification);
        final String message=getString(R.string.extradata_message);
        final String noExtraData=getString(R.string.extradata_nothing);
        final String items[]={noExtraData,email,phoneNumber,image
                ,appNotification,message};
        Arrays.sort(items);
        int id=Arrays.binarySearch(items,extraDataText.getText().toString());
        if(id<0){
            return;
        }
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle(getString(R.string.choose_extraData)); //设置标题
        builder.setSingleChoiceItems(items,id,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                extraDataText.setText(items[which]);
                if(items[which].equals(email)){
                    extraDataType= TodoItem_constants.EXTRADATATYPE_EMAIL;
                }
                if(items[which].equals(phoneNumber)){
                    extraDataType= TodoItem_constants.EXTRADATATYPE_PHONENUMBER;
                }
                if(items[which].equals(message)){
                    extraDataType= TodoItem_constants.EXTRADATATYPE_MESSAGE;
                }
                if(items[which].equals(appNotification)){
                    extraDataType= TodoItem_constants.EXTRADATATYPE_APP_NOTIFICATION;
                }
                if(items[which].equals(image)){
                    extraDataType= TodoItem_constants.EXTRADATATYPE_IMAGE;
                }
                if(items[which].equals(noExtraData)){
                    extraDataType= TodoItem_constants.EXTRADATATYPE_NOTHING;
                }
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void setOperation(){
        final String email=getString(R.string.operation_open_email);
        final String phoneNumber=getString(R.string.operation_open_call);
        final String link=getString(R.string.operation_open_link);
        final String appNotification=getString(R.string.operation_open_appNotification);
        final String message=getString(R.string.operation_open_message);
        final String noOperation=getString(R.string.operation_nothing);
        final String items[]={noOperation,email,phoneNumber,link
                ,appNotification,message};
        Arrays.sort(items);
        int id=Arrays.binarySearch(items,operationDataText.getText().toString());
        if(id<0){
            return;
        }
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.choose_operation));
        builder.setSingleChoiceItems(items,id,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                operationDataText.setText(items[which]);
                if(items[which].equals(email)){
                    operationType= TodoItem_constants.OPERATIONTYPE_OPEN_EMAIL;
                }
                if(items[which].equals(phoneNumber)){
                    operationType= TodoItem_constants.OPERATIONTYPE_OPEN_CALL;
                }
                if(items[which].equals(message)){
                    operationType= TodoItem_constants.OPERATIONTYPE_OPEN_MESSAGE;
                }
                if(items[which].equals(appNotification)){
                    operationType= TodoItem_constants.OPERATIONTYPE_OPEN_APP_NOTIFICATION;
                }
                if(items[which].equals(link)){
                    operationType= TodoItem_constants.OPERATIONTYPE_OPEN_INTERNET_LINK;
                }
                if(items[which].equals(noOperation)){
                    operationType= TodoItem_constants.OPERATIONTYPE_NOTHING;
                }
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void setCategory(){

    }



    private void showTimeandDate(){
        final AlertDialog alertDialog=new AlertDialog.Builder(this).create();
        final View dialogView= LayoutInflater.from(AddActivity.this).inflate(R.layout.simpletime_picker,null);
        alertDialog.setView(dialogView);
        alertDialog.show();
        WindowManager.LayoutParams params=alertDialog.getWindow().getAttributes();
        float density = getDensity(AddActivity.this);
        params.width=1000;
        params.height=1080;
        params.gravity= Gravity.CENTER;
        alertDialog.getWindow().setAttributes(params);
        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.today_sometime_simple_time_picker:
                        alertDialog.dismiss();
                        showTimeDialog(1);
                        break;
                    case R.id.tomorrow_sometime_simpletime_picker:
                        alertDialog.dismiss();
                        showTimeDialog(2);
                        break;
                    case R.id.simpletime_picker_tomorrow:
                        setTime(1);
                        alertDialog.dismiss();
                        break;
                    case R.id.simpletime_picker_tomorrow_afternoon:
                        setTime(2);
                        alertDialog.dismiss();
                        break;
                    case R.id.simpletime_picker_tomorrow_evening:
                        setTime(3);
                        alertDialog.dismiss();
                        break;
                    case R.id.simpletime_picker_nextweek:
                        setTime(4);
                        alertDialog.dismiss();
                        break;
                    case R.id.simpletime_picker_pick_time:
                        showDateAndTimeDialog();
                        alertDialog.dismiss();
                        break;
                    case R.id.simpletime_picker_notime:
                        longTime=0;
                        timeType=TimeType_constants.NOTIME;
                        todoTimeText.setText(R.string.time_type_text_notimeanddata);
                        isRepeat=TodoItem_constants.ISREPEAT_0;
                        notification=TodoItem_constants.NOTIFICATIONT_TYPE_NOTHING;
                        repeatDataText.setText(getString(R.string.repeat_no_repeat));
                        notificationText.setText(getString(R.string.notification_nothing));
                        alertDialog.dismiss();
                        break;
                    default :
                        alertDialog.dismiss();
                        break;
                }
            }
        };
        LinearLayout todaySometime=(LinearLayout) dialogView.findViewById(R.id.today_sometime_simple_time_picker);
        LinearLayout tomorrowSometime=(LinearLayout) dialogView.findViewById(R.id.tomorrow_sometime_simpletime_picker);
        LinearLayout tomorrowAfternoon=(LinearLayout)dialogView.findViewById(R.id.simpletime_picker_tomorrow_afternoon);
        LinearLayout tomorrowEveing=(LinearLayout)dialogView.findViewById(R.id.simpletime_picker_tomorrow_evening);
        LinearLayout tomorrow=(LinearLayout)dialogView.findViewById(R.id.simpletime_picker_tomorrow);
        LinearLayout nextWeek=(LinearLayout)dialogView.findViewById(R.id.simpletime_picker_nextweek);
        LinearLayout pickTime=(LinearLayout)dialogView.findViewById(R.id.simpletime_picker_pick_time);
        LinearLayout noTime=(LinearLayout)dialogView.findViewById(R.id.simpletime_picker_notime);
        todaySometime.setOnClickListener(onClickListener);
        tomorrowSometime.setOnClickListener(onClickListener);
        tomorrowAfternoon.setOnClickListener(onClickListener);
        tomorrowEveing.setOnClickListener(onClickListener);
        tomorrow.setOnClickListener(onClickListener);
        nextWeek.setOnClickListener(onClickListener);
        pickTime.setOnClickListener(onClickListener);
        noTime.setOnClickListener(onClickListener);
    }
    private float getDensity(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.density;
    }
    private void showDateAndTimeDialog() {
        final Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        DatePickerDialog datePickerDialog=new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });




        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        final View dialogView = LayoutInflater.from(AddActivity.this)
                .inflate(R.layout.custom_text,null);
        dialog.setView(dialogView);
        TextView confirmDatePickerDialog=(TextView) dialogView.findViewById(R.id.confirm_datepickerdialog);
        TextView cancelDatePickerDialog=(TextView) dialogView.findViewById(R.id.cancel_datepickerdialog);
        final TextView setTimeDatePickerDialog=(TextView) dialogView.findViewById(R.id.set_time_datepickerdialog);
        final DatePicker datePicker=(DatePicker)dialogView.findViewById(R.id.select_date);
        cancelDatePickerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirmDatePickerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day=datePicker.getDayOfMonth();
                int year=datePicker.getYear();
                int month=datePicker.getMonth();
                calendar.set(year,month,day);
                SimpleDateFormat df;
                if(!setTimeDatePickerDialog.getText().equals(getString(R.string.set_time))){
                    df = new SimpleDateFormat("yyyy/MM/dd EE HH:mm");
                    todoTimeText.setText(df.format(calendar.getTime()));
                    timeType=TimeType_constants.EXACTTIME;
                    longTime=calendar.getTimeInMillis();
                }else {
                    df = new SimpleDateFormat("yyyy/MM/dd EE");
                    todoTimeText.setText(df.format(calendar.getTime()));
                    timeType=TimeType_constants.EXACTTIME;
                    longTime=calendar.getTimeInMillis();
                }
                dialog.dismiss();

            }
        });
        setTimeDatePickerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTimeInMillis(System.currentTimeMillis());
                final TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                        setTimeDatePickerDialog.setText(df.format(calendar.getTime()));
                    }
                }, calendar1.get(Calendar.HOUR_OF_DAY), calendar1.get(Calendar.MINUTE), true);
                mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.time_type_text_notime), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setTimeDatePickerDialog.setText(getString(R.string.set_time));
                        calendar.set(Calendar.HOUR_OF_DAY, 9);
                        calendar.set(Calendar.MINUTE, 0);
                        mTimePicker.dismiss();
                    }
                });
                mTimePicker.show();
            }
        });
        dialog.show();
        WindowManager.LayoutParams params =
                dialog.getWindow().getAttributes();
        params.width = 960;
        params.height = 1600 ;
        dialog.getWindow().setAttributes(params);
    }




    private void showTimeDialog(final int type){
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(System.currentTimeMillis());
        final TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar calendar = Calendar.getInstance();
                if(type==1){
                    calendar.setTimeInMillis(System.currentTimeMillis());
                } else{
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.add(Calendar.DAY_OF_MONTH,1);
                }
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd EE HH:mm");
                timeType=TimeType_constants.EXACTTIME;
                todoTimeText.setText(df.format(calendar.getTime()));
                longTime=calendar.getTimeInMillis();
            }
        }, calendar1.get(Calendar.HOUR_OF_DAY), calendar1.get(Calendar.MINUTE), true);
        mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mTimePicker.dismiss();
            }
        });
        mTimePicker.show();
    }
    private void setTime(int type){
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        String todoTime;
        switch (type){
            case 1://tomorrow
                calendar.add(Calendar.DAY_OF_MONTH,1);
                calendar.set(Calendar.HOUR_OF_DAY,TimeType_constants.STARTTIME_SOMEDAY_time);
                calendar.set(Calendar.MINUTE,0);
                calendar.set(Calendar.SECOND,0);
                timeType= TimeType_constants.SOMEDAY;
                todoTime="";
                break;
            case 2://tomorrow afternoon
                calendar.add(Calendar.DAY_OF_MONTH,1);
                calendar.set(Calendar.HOUR_OF_DAY,TimeType_constants.AFTERNONN_SOMEDAY_time);
                calendar.set(Calendar.MINUTE,0);
                calendar.set(Calendar.SECOND,0);
                timeType=TimeType_constants.SOMEDAY_AFTERNOON;
                todoTime=getString(R.string.time_type_text_afternoon);
                break;
            case 3://tomorrow evening
                calendar.add(Calendar.DAY_OF_MONTH,1);
                calendar.set(Calendar.HOUR_OF_DAY,TimeType_constants.EVENING_SOMEDAY_time);
                calendar.set(Calendar.MINUTE,0);
                calendar.set(Calendar.SECOND,0);
                timeType=TimeType_constants.SOMEDAY_EVENING;
                todoTime=getString(R.string.time_type_text_evening);
                break;
            case 4://next week
                calendar.add(Calendar.DAY_OF_MONTH,7);
                calendar.set(Calendar.HOUR_OF_DAY,TimeType_constants.STARTTIME_SOMEDAY_time);
                calendar.set(Calendar.MINUTE,0);
                calendar.set(Calendar.SECOND,0);
                timeType= TimeType_constants.SOMEDAY;
                todoTime="";
                break;
            default:
                calendar.add(Calendar.DAY_OF_MONTH,1);
                calendar.set(Calendar.HOUR_OF_DAY,TimeType_constants.STARTTIME_SOMEDAY_time);
                calendar.set(Calendar.MINUTE,0);
                calendar.set(Calendar.SECOND,0);
                timeType= TimeType_constants.SOMEDAY;
                todoTime="";
                break;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd EE ");
        todoTimeText.setText(df.format(calendar.getTime())+todoTime);
        longTime=calendar.getTimeInMillis();
        String name="test commit and push";

    }


}
