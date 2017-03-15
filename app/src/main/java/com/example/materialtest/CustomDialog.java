package com.example.materialtest;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by 龙标F on 2017/3/7 0007.
 */
////先暂时用那个组装的，这个以后有时间再说

public class CustomDialog extends AlertDialog {
    private Context mContext;
    protected CustomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext=context;

    }

    @Override
    public void setView(View view) {
        view= LayoutInflater.from(mContext)
                .inflate(R.layout.custom_text,null);
        super.setView(view);
    }
}
