package com.example.materialtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by 龙标F on 2017/1/6 0006.
 */

public class FruitActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);
        Intent intent=getIntent();//没有注册这个活动
        String fruitName=intent.getStringExtra("fruit_name");
        int fruitImageId=intent.getIntExtra("fruit_image_id",0);
        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar2);
        TextView fruitContentText=(TextView)findViewById(R.id.fruit_content_text);
        ImageView fruitImageView=(ImageView)findViewById(R.id.fruit_image_view);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(fruitName);
        Glide.with(this).load(fruitImageId).into(fruitImageView);
        Log.d("FruitActivity",fruitName);
        String fruitContent=getContentText(fruitName);
        fruitContentText.setText(fruitContent);
    }
    private String getContentText(String fruitName){
        StringBuilder fruitContent=new StringBuilder();
        for(int i=0;i<500;i++){
            fruitContent.append(fruitName);
        }
        return fruitContent.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
