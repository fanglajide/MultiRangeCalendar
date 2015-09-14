package com.cheheihome;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lecalendar.model.DayModel;
import lecalendar.views.DayView;

/**
 * Created by chanlevel on 15/9/14.
 */
public class LeCalendarPlus extends LinearLayout {
    LinearLayout weekHeader;
    LeCalendar mCalendar;
    TextView txt_friday;
    DisplayMetrics displayMetrics;
    CheckBox[] week = new CheckBox[7];

    public LeCalendarPlus(Context context) {
        super(context);
        init();
    }

    public LeCalendarPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LeCalendarPlus(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        displayMetrics = getContext().getResources().getDisplayMetrics();
        int density = (int) displayMetrics.density;
        setOrientation(VERTICAL);
        View dividerView = new View(getContext());
        dividerView.setBackgroundColor(Color.GRAY);
        addView(dividerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
        // weekHeader = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.calendar_header, null);
        weekHeader = week();
        addView(weekHeader, new ViewGroup.LayoutParams(headWidth(), headHeight()));
        // addView(dividerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
        mCalendar = new LeCalendar(getContext());
        ViewGroup.LayoutParams clp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mCalendar.setGravity(Gravity.FILL_HORIZONTAL);
        addView(mCalendar, clp);

    }

    private LinearLayout week() {
        LinearLayout l = new LinearLayout(getContext());
        l.setOrientation(HORIZONTAL);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        for (int i = 0; i < 7; i++) {
            week[i] = new CheckBox(getContext());
            week[i].setGravity(Gravity.CENTER);

            week[i].setButtonDrawable(null);

            String s = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
            final Calendar t = c;
            week[i].setText(s);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
            if (i == 5 || i == 6) {
                week[i].setTextColor(Color.RED);
                final int finalI = i+1;
                week[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked)
                            mCalendar.chooseDays(finalI);
                        else
                            mCalendar.unchooseDays(finalI);
                    }
                });
            }

            l.addView(week[i], lp);
            c.add(Calendar.DAY_OF_WEEK, 1);
        }
        return l;
    }

    private int headWidth() {
        return (displayMetrics.widthPixels / 7) * 7;
    }

    private int headHeight() {
        return displayMetrics.widthPixels / 7;
    }


    public void setType(DayView.SElECTTYPE type) {
        mCalendar.setType(type);
    }


    public void onRefresh() {
        mCalendar.onRefresh();
    }

    public void setDays(ArrayList<DayModel> days) {
        mCalendar.setDays(days);
    }

    public List<Date> getTotalSelected() {
        return mCalendar.getTotalSelected();
    }

    public void setSelectListener(LeCalendar.LeSelectListener listener) {
        mCalendar.setSelectListener(listener);
    }

    public void setAdapter(BaseAdapter adapter) {
        mCalendar.setAdapter(adapter);
    }

    public void chooseDays(int dayOfWeek) {
        mCalendar.chooseDays(dayOfWeek);
    }
     public void cleanSelection(){
     mCalendar.cleanSelection();
    }
}
