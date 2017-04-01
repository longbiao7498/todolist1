package com.example.materialtest.tool;

import android.content.Context;

import com.example.materialtest.R;
import com.example.materialtest.constants.TimeType_constants;
import com.example.materialtest.constants.TodoItem_constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by 龙标F on 2017/3/17 0017.
 */

public class DataTransformTool {
    public static String repeatDataTransform(String repeatData, int isRepeat, Context mContext){
        String repeatDataString;
        if(isRepeat==1){
            switch (Integer.parseInt(repeatData)){
                case TodoItem_constants.EVERYDAY:
                    repeatDataString=mContext.getString(R.string.repeat_everyday);
                    break;
                case TodoItem_constants.EVERYMONTH:
                    repeatDataString=mContext.getString(R.string.repeat_every_month);
                    break;
                case TodoItem_constants.EVERYWEEK:
                    repeatDataString=mContext.getString(R.string.repeat_every_week);
                    break;
                case TodoItem_constants.EVERYYEAR:
                    repeatDataString=mContext.getString(R.string.repeat_every_year);
                    break;
                default:
                    repeatDataString=mContext.getString(R.string.repeat_no_repeat);
            }
        }else{
            repeatDataString=mContext.getString(R.string.repeat_no_repeat);
        }
        return repeatDataString;
    }
    public static String operationDataTransform(int operationType,String operaionData,Context mContext){
        String operationDataString;
        switch(operationType){
            case TodoItem_constants.OPERATIONTYPE_NOTHING:
                operationDataString=mContext.getString(R.string.operation_nothing);
                break;
            case TodoItem_constants.OPERATIONTYPE_OPEN_EMAIL:
                operationDataString=mContext.getString(R.string.operation_open_email);
                break;
            case TodoItem_constants.OPERATIONTYPE_OPEN_CALL:
                operationDataString=mContext.getString(R.string.operation_open_call);
                break;
            case TodoItem_constants.OPERATIONTYPE_OPEN_APP_NOTIFICATION:
                operationDataString=mContext.getString(R.string.operation_open_appNotification);
                break;
            case TodoItem_constants.OPERATIONTYPE_OPEN_INTERNET_LINK:
                operationDataString=mContext.getString(R.string.operation_open_link);
                break;
            case TodoItem_constants.OPERATIONTYPE_OPEN_MESSAGE:
                operationDataString=mContext.getString(R.string.operation_open_message);
                break;
            default:
                operationDataString=mContext.getString(R.string.operation_nothing);
                break;
        }
        return operationDataString;
    }
    public static String notificationTransform(int notification,Context mContext){
        String notificationString;
        switch(notification){
            case TodoItem_constants.NOTIFICATIONT_TYPE_NOTHING:
                notificationString=mContext.getString(R.string.notification_nothing);
                break;
            case TodoItem_constants.NOTIFICATIONT_TYPE_NO_VOICE:
                notificationString=mContext.getString(R.string.notification_no_voice);
                break;
            case TodoItem_constants.NOTIFICATIONT_TYPE_VOICE:
                notificationString=mContext.getString(R.string.notification_voice);
                break;
            default:
                notificationString=mContext.getString(R.string.notification_nothing);
                break;
        }
        return notificationString;
    }
    public static String priorityTransform(int priority,Context mConText){
        String priorityString;
        switch (priority){
            case TodoItem_constants.PRIORITY_1:
                priorityString=mConText.getString(R.string.priority_1);
                break;
            case TodoItem_constants.PRIORITY_2:
                priorityString=mConText.getString(R.string.priority_2);
                break;
            case TodoItem_constants.PRIORITY_3:
                priorityString=mConText.getString(R.string.priority_3);
                break;
            case TodoItem_constants.PRIORITY_4:
                priorityString=mConText.getString(R.string.priority_4);
                break;
            case TodoItem_constants.PRIORITY_5:
                priorityString=mConText.getString(R.string.priority_5);
                break;
            default:
                priorityString=mConText.getString(R.string.priority_5);
                break;
        }
        return priorityString;
    }
    public static String categoryTransform(int category,Context mContext){
        return  mContext.getString(R.string.default_category);
    }
    public static String extraDataTransform(int extraDataType,String repeatData,Context mContext){//d
        //当然这里不能这么处理，附件不只是几个文字而已
        String extraDataTring;
        switch (extraDataType){
            case TodoItem_constants.EXTRADATATYPE_NOTHING:
                extraDataTring=mContext.getString(R.string.extradata_nothing);
                break;
            case TodoItem_constants.EXTRADATATYPE_IMAGE:
                extraDataTring=mContext.getString(R.string.extradata_image);
                break;
            case TodoItem_constants.EXTRADATATYPE_TEXT:
                extraDataTring=mContext.getString(R.string.extradata_text);
                break;
            case TodoItem_constants.EXTRADATATYPE_EMAIL:
                extraDataTring=mContext.getString(R.string.extradata_email);
                break;
            case TodoItem_constants.EXTRADATATYPE_PHONENUMBER:
                extraDataTring=mContext.getString(R.string.extradata_phone_number);
                break;
            case TodoItem_constants.EXTRADATATYPE_APP_NOTIFICATION:
                extraDataTring=mContext.getString(R.string.extradata_app_notification);
                break;
            case TodoItem_constants.EXTRADATATYPE_MESSAGE:
                extraDataTring=mContext.getString(R.string.extradata_message);
                break;
            default:
                extraDataTring=mContext.getString(R.string.extradata_nothing);
                break;
        }
        return extraDataTring;
    }
    public static String longTimeToToDoTime(long longTime, int timeType, Context context){
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(longTime);
        String todoTime;
        SimpleDateFormat df;
        switch (timeType){
            case TimeType_constants.NOTIME:
                todoTime=context.getString(R.string.time_type_text_notimeanddata);
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
                todoTime=context.getString(R.string.time_type_text_notimeanddata);
                break;

        }
        return todoTime;
    }

}
