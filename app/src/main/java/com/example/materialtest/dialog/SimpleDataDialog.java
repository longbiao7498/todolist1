package com.example.materialtest.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.materialtest.EditActivity;
import com.example.materialtest.R;

/**
 * Created by 龙标F on 2017/3/20 0020.
 */

public class SimpleDataDialog extends Dialog implements View.OnClickListener {
    public  interface SimpleDataDialogListener{
        void onTimeSet(long longTime,int timeType);
    }
    private SimpleDataDialogListener simpleDataDialogListener;
    private Context mContext;
    public SimpleDataDialog(Context mContext,SimpleDataDialogListener simpleDataDialogListener){
        super(mContext, R.style.dialog_custom);
        this.mContext=mContext;
        this.simpleDataDialogListener=simpleDataDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
       // window.setWindowAnimations(R.style.bottom_menu_animation); // 添加动画效果
        View dialogView= LayoutInflater.from(mContext).inflate(R.layout.simpletime_picker_test,null);
        setContentView(dialogView);

        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth()*7/8; // 设置dialog宽度为屏幕的7/8
        lp.height=lp.width;
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(true);// 点击Dialog外部消失
//        dialogView.findViewById(R.id.today_sometime_simple_time_picker).setOnClickListener(this);
//        dialogView.findViewById(R.id.tomorrow_sometime_simpletime_picker).setOnClickListener(this);
//        dialogView.findViewById(R.id.simpletime_picker_notime).setOnClickListener(this);
//        dialogView.findViewById(R.id.simpletime_picker_pick_time).setOnClickListener(this);
//        dialogView.findViewById(R.id.simpletime_picker_tomorrow).setOnClickListener(this);
//        dialogView.findViewById(R.id.simpletime_picker_tomorrow_afternoon).setOnClickListener(this);
//        dialogView.findViewById(R.id.simpletime_picker_tomorrow_evening).setOnClickListener(this);
//        dialogView.findViewById(R.id.simpletime_picker_nextweek).setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        int timeType;
//        switch (view.getId()){
//            case R.id.today_sometime_simple_time_picker:
//                timeType=0;
//                break;
//            case R.id.tomorrow_sometime_simpletime_picker:
//                timeType=1;
//                break;
//            case R.id.simpletime_picker_tomorrow:
//                timeType=2;
//                break;
//            case R.id.simpletime_picker_tomorrow_afternoon:
//                timeType=3;
//                break;
//            case R.id.simpletime_picker_tomorrow_evening:
//                timeType=4;
//                break;
//            case R.id.simpletime_picker_nextweek:
//                timeType=5;
//                break;
//            case R.id.simpletime_picker_notime:
//                timeType=6;
//                break;
//            case R.id.simpletime_picker_pick_time:
//                timeType=7;
//                break;
//            default:
//                timeType=0;
//                break;
//        }
        dismiss();
        //simpleDataDialogListener.onTimeSet(0,timeType);
    }
}
