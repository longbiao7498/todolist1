package com.example.materialtest;

/**
 * Created by 龙标F on 2017/3/17 0017.
 */

public class ToDoItemComplex {
    private int id;
    private int repeatNum;
    private int isDone;
    private int  isRepeat;
    private String mainText;
    private long longTime;
    private int timeType;
    private String repeatData;
    private int notification;
    private int priority;
    private int extraDataType;
    private String extraData;
    private int operationType;
    private String operationData;
    private String remarks;
    private int category;//15
    public ToDoItemComplex(int id,int repeatNum,int isDone,int isRepeat,String mainText,long longTime,int timeType,String repeatData,int notification,int priority
    ,int extraDataType,String  extraData,int operationType,String operationData,String remarks,int category){
        this.id=id;
        this.repeatNum=repeatNum;
        this.isDone=isDone;
        this.isRepeat=isRepeat;
        this.mainText=mainText;
        this.longTime=longTime;
        this.timeType=timeType;
        this.repeatData=repeatData;
        this.notification=notification;
        this.priority=priority;
        this.extraDataType=extraDataType;
        this.extraData=extraData;
        this.operationType=operationType;
        this.operationData=operationData;
        this.remarks=remarks;
        this.category=category;//15
    }
    public int getId(){
        return id;
    }
    public int getRepeatNum(){
        return repeatNum;
    }
    public int getIsDone(){
        return isDone;
    }
    public int  getIsRepeat(){
        return isRepeat;
    }
    public String getMainText(){
        return mainText;
    }
    public long getLongTime(){
        return longTime;
    }
    public int getTimeType(){
        return timeType;
    }
    public String getRepeatData(){
        return repeatData;
    }
    public int getNotification(){
        return notification;
    }
    public int getPriority(){
        return priority;
    }
    public int getExtraDataType(){
        return extraDataType;
    }
    public String getExtraData(){
        return extraData;
    }
    public int getOperationType(){
        return operationType;
    }
    public String getOperationData(){
        return operationData;
    }
    public int getCategory(){
        return category;
    }
    public String getRemarks(){
        return remarks;
    }
}
