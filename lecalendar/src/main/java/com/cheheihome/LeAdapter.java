package com.cheheihome;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cheheihome.lecalendar.R;

import java.util.ArrayList;

import lecalendar.model.DayModel;
import lecalendar.views.DayView;

/**
 * Created by chanlevel on 15/9/10.
 */
public class LeAdapter extends BaseAdapter {
    Context context;
    ArrayList<DayModel> days;
    DayView.SElECTTYPE type;

    public LeAdapter(Context context) {
        this.context = context;
    }

    public LeAdapter(Context context, ArrayList<DayModel> days, DayView.SElECTTYPE type) {
        this.context = context;
        this.days = days;
        this.type = type;
    }


    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Object getItem(int position) {
        return days.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//            DayView dayView = new DayView(MainActivity.this);
//            dayView.setHeight(150);
//            dayView.setDayModel((DayModel) getItem(position));


        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_day, null);


            holder = new ViewHolder();
            holder.dayView = (DayView) convertView.findViewById(R.id.dayView);
            // convertView.setMinimumHeight(150);
            convertView.setTag(holder);
            holder.dayView.setDayModel(days.get(position));

        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.dayView.setDayModel(days.get(position));
            convertView.setMinimumHeight(150);

        }
        holder.dayView.setmSelectType(type);

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {

        Log.d("super-adapter", "notifyDataSetChanged");
        super.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetInvalidated() {
        Log.d("super-adapter", "notifyDataSetInvalidated");
        super.notifyDataSetInvalidated();
    }

    private static class ViewHolder {
        DayView dayView;
    }
}
