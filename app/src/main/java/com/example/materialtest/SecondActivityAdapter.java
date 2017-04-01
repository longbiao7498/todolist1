package com.example.materialtest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.materialtest.constants.MainActivity_view_constants;
import com.example.materialtest.constants.TodoItem_constants;

import java.util.List;

/**
 * Created by 龙标F on 2017/1/7 0007.
 */

public class SecondActivityAdapter extends RecyclerView.Adapter<SecondActivityAdapter.ViewHolder> {
    private List<TodoItem> todoItemList;
    private Context mContext;
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView sectionText;
        ImageView notificationImage;
        TextView mainText;
        ImageView extraTypeImage;
        TextView timeText;
        ImageView repeatImage;
        TextView priorityText;
        View todoView;
        public ViewHolder(View view) {
            super(view);
            sectionText=(TextView)view.findViewById(R.id.second_activity_item_type);
            notificationImage=(ImageView)view.findViewById(R.id.second_activity_item_notification);
            mainText=(TextView)view.findViewById(R.id.second_activity_item_maintext);
            extraTypeImage=(ImageView)view.findViewById(R.id.second_activity_item_extra_data);
            repeatImage=(ImageView)view.findViewById(R.id.second_activity_item_repeat);
            timeText=(TextView)view.findViewById(R.id.second_activity_item_time);
            priorityText=(TextView)view.findViewById(R.id.second_activity_item_priority);
            todoView=view;
        }
    }
    public SecondActivityAdapter(List<TodoItem> todoItemList){
        this.todoItemList=todoItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.second_activity_todo_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.todoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                TodoItem todoItem=todoItemList.get(position);
                Intent intent=new Intent(mContext,EditActivity.class);
                intent.putExtra("id",todoItem.getId());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TodoItem todoItem=todoItemList.get(position);
        holder.timeText.setText(todoItem.getTodoTime());
        holder.timeText.setTextSize(12);
        holder.mainText.setText(todoItem.getMainText());
        switch (todoItem.getSection()){
            case MainActivity_view_constants.TIME_SECTION_TODAY:
                holder.sectionText.setTextSize(15);
                holder.sectionText.setText(mContext.getString(R.string.time_section_today));
                break;
            case MainActivity_view_constants.TIME_SECTION_TOMMORW:
                holder.sectionText.setTextSize(15);
                holder.sectionText.setText(mContext.getString(R.string.time_section_tomorrow));
                break;
            case MainActivity_view_constants.TIME_SECTION_INWEEK:
                holder.sectionText.setTextSize(15);
                holder.sectionText.setText(mContext.getString(R.string.time_section_in_week));
                break;
            case MainActivity_view_constants.TIME_SECTION_FURTURE:
                holder.sectionText.setTextSize(15);
                holder.sectionText.setText(mContext.getString(R.string.time_section_furture));
                break;
            case MainActivity_view_constants.TIME_SECTION_NOTIME:
                holder.sectionText.setTextSize(0);
                holder.sectionText.setText(null);
                break;
            default:
                holder.sectionText.setTextSize(0);
                holder.sectionText.setText(null);
                break;
        }
        switch (todoItem.getExtraDataType()){
            case TodoItem_constants.EXTRADATATYPE_NOTHING:
                holder.extraTypeImage.setImageResource(R.drawable.ic_second_activity_item_nothing);
                break;
            case TodoItem_constants.EXTRADATATYPE_PHONENUMBER:
                holder.extraTypeImage.setImageResource(R.drawable.ic_second_activity_item_extradata_phone_number);
                break;
            case TodoItem_constants.EXTRADATATYPE_MESSAGE:
                holder.extraTypeImage.setImageResource(R.drawable.ic_second_activity_item_extradata_message);
                break;
            case TodoItem_constants.EXTRADATATYPE_EMAIL:
                holder.extraTypeImage.setImageResource(R.drawable.ic_second_activity_item_extradata_email);
                break;
            case TodoItem_constants.EXTRADATATYPE_APP_NOTIFICATION:
                holder.extraTypeImage.setImageResource(R.drawable.ic_second_activity_item_extradata_notification);
                break;
            case TodoItem_constants.EXTRADATATYPE_IMAGE:
                holder.extraTypeImage.setImageResource(R.drawable.ic_second_activity_item_extradata_image);
                break;
            case TodoItem_constants.EXTRADATATYPE_TEXT:
                holder.extraTypeImage.setImageResource(R.drawable.ic_second_activity_item_extradata_text);
                break;
            default:
                holder.extraTypeImage.setImageResource(R.drawable.ic_second_activity_item_nothing);
                break;
        }

        switch (todoItem.getPriority()){
            case TodoItem_constants.PRIORITY_1:
                holder.priorityText.setText(mContext.getString(R.string.priority_1));
                break;
            case TodoItem_constants.PRIORITY_2:
                holder.priorityText.setText(mContext.getString(R.string.priority_2));
                break;
            case TodoItem_constants.PRIORITY_3:
                holder.priorityText.setText(mContext.getString(R.string.priority_3));
                break;
            case TodoItem_constants.PRIORITY_4:
                holder.priorityText.setText(mContext.getString(R.string.priority_4));
                break;
            case TodoItem_constants.PRIORITY_5:
                holder.priorityText.setText(mContext.getString(R.string.priority_5));
                break;
            default:
                holder.priorityText.setText(mContext.getString(R.string.priority_1));
        }
        if(todoItem.getTodoTime().equals("")||todoItem.getLongTime()==0){
            holder.timeText.setTextSize(0);
            holder.repeatImage.setImageResource(R.drawable.ic_second_activity_item_nothing);
            holder.notificationImage.setImageResource(R.drawable.ic_second_activity_item_nothing);
        }else{
            switch (todoItem.getNotification()){
                //如果没有设置时间，则肯定不会有通知图标现实的，虽然在Addactivity会有一成筛选，但在这里可能一些已知的数据，并没有按要求，所以还是要筛选一下
                //即使是没有通知也要进行一些操作，也要将图标的大小设置为0，并变为不可见，而不是默认
                case TodoItem_constants.NOTIFICATIONT_TYPE_NO_VOICE:
                    holder.notificationImage.setImageResource(R.drawable.ic_second_activity_item_notification_novoice);
                    break;
                case TodoItem_constants.NOTIFICATIONT_TYPE_NOTHING:
                    holder.repeatImage.setImageResource(R.drawable.ic_second_activity_item_nothing);
                    break;
                case TodoItem_constants.NOTIFICATIONT_TYPE_VOICE:
                    holder.notificationImage.setImageResource(R.drawable.ic_second_activity_item_notification_voice);
                    break;
                default:
                    holder.repeatImage.setImageResource(R.drawable.ic_second_activity_item_nothing);
                    break;
            }
            //即使是不重复也要进行一些操作，也要将图标的大小设置为0，并变为不可见，而不是默认
            if(todoItem.getIsRepeat()==true){
                holder.repeatImage.setImageResource(R.drawable.ic_second_activity_item_repeat);
            }else {
                holder.repeatImage.setImageResource(R.drawable.ic_second_activity_item_nothing);
            }
        }

    }

    @Override
    public int getItemCount() {
        return todoItemList.size();
    }
}
