package com.example.materialtest;

/**
 * Created by 龙标F on 2017/2/18 0018.
 */

public class TodoItem {
    private int id;//序列号，用于向数据库查询数据
    private int section;//开始项标记位；
    private String mainText;//待办事项的正文
    private String todoTime;///待定，关于时间，应该或许用其他的类型；
    private double longTime;//用于在list中的排序
    private int notification; //待办事项的等级这里应该是枚举类
    private int priority;
    private boolean isRepeat;//这个待办事项是否重复
    private int repeatNum;//重复编码
    private int extraDataType;//操作类型///10
    private int timeType;

    public TodoItem(int id,String mainText,String todoTime,double longTime,int notification,int priority,int extraDataType,boolean isRepeat,int repeatNum,int timeType,int section){
        this.id=id;//
        this.mainText=mainText;//
        this.todoTime=todoTime;
        this.longTime=longTime;
        this.notification=notification;//
        this.priority=priority;
        this.extraDataType=extraDataType;
        this.isRepeat=isRepeat;
        this.repeatNum=repeatNum;
        this.section=section;
        this.timeType=timeType;
    }

    public int getId(){//9
        return id;
    }
    public String getMainText(){//7
        return mainText;
    }
    public String getTodoTime(){//6
        return todoTime;
    }
    public double getLongTime(){//5
        return longTime;
    }
    public int getNotification(){//4
        return notification;
    }
    public int getExtraDataType(){//3
        return extraDataType;
    }
    public int getPriority(){return  priority;}
    public boolean getIsRepeat(){//2
        return isRepeat;
    }
    public int getRepeatNum(){//1
        return repeatNum;
    }
    public int getSection(){
        return this.section;
    }
////接下来还要写相等的部分，以及hashCode值，或者compareTo，还有几个集合之间该用哪个更快

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TodoItem){
            TodoItem todoItem=(TodoItem)obj;
            if((this.id==todoItem.getId())&&(this.mainText.equals(todoItem.getMainText()))){
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString() {
        return new String("id:"+id+" mainText:"+mainText+" isRepeat:"+isRepeat+" notification:"+notification+" operationType:"
                +extraDataType+" todoTime:"+todoTime);
    }
}
