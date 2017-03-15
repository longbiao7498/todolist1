package com.example.materialtest;

/**
 * Created by 龙标F on 2017/1/4 0004.
 */

public class Fruit {
    private  int imageId;
    private String name;
    public Fruit(int imageId,String name){
        this.name=name;
        this.imageId=imageId;
    }
    public int getImageId(){
        return imageId;
    }
    public String getName(){
        return name;
    }
}
