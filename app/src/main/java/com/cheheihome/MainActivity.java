package com.cheheihome;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;

import com.cheheihome.supercalendar.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lecalendar.model.DayModel;
import lecalendar.views.DayView;

/**
 * Created by chanlevel on 15/9/8.
 */
public class MainActivity extends AppCompatActivity implements AbsListView.OnScrollListener {
    LeCalendar gridView;
    DayView dayView;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().penaltyFlashScreen().penaltyLog().build());

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        gridView = (LeCalendar) findViewById(R.id.gridView);
        dayView = (DayView) findViewById(R.id.dayView);
        dayView.setDaymodel(new DayModel(false, true, "ablgcdp", 0, 0, null, new Date(), false, false, false, false));

        dayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DayModel dayModel = days.get(310);
//                dayModel.setRoom_num(dayModel.getRoom_num() > 0 ? 0 : 1);
//
//                DayView daycell = (DayView) gridView.getChildAt(10);
//                daycell.setStatus(flag = !flag);
                // dayView.setDayModel(dayModel);

//                gridView.setType(DayView.SElECTTYPE.PRICE);
               // Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                List<Date> a=gridView.getTotalSelected();
                for (Date d : a)
                    Log.d("super-select", d.toString());

            }
        });


        Calendar b = Calendar.getInstance();
        b.add(Calendar.WEEK_OF_YEAR, -1);
        b.set(Calendar.DAY_OF_WEEK, 1);
        Calendar e = Calendar.getInstance();
        e.add(Calendar.YEAR, 1);
        e.set(Calendar.DAY_OF_WEEK, 7);
        getDates(b, e);
     //   gridView.setDays(days);
        // setGridView();

        //     setGrid();
    }




    void getDates(Calendar begin, Calendar end) {
        Calendar temp = begin;
        // if (this.days == null) this.days = ArrayList<DayModel>();
        while (temp.before(end)) {
            DayModel dayModel = new DayModel(false, false, null, 0, 0, null, temp.getTime(), false, false, false, false);

            dayModel.setRoom_num((int) (Math.random() * 5));
            dayModel.setIsFestival(dayModel.getRoom_num() > 3 ? true : false);
            dayModel.setPrice(dayModel.getRoom_num());
            dayModel.init();
            this.days.add(dayModel);

            temp.add(Calendar.DATE, 1);
        }


    }

    ArrayList<DayModel> days = new ArrayList<>();



    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.visiblecount = visibleItemCount;
    }

    int visiblecount;

    private static class ViewHolder {
        DayView dayView;
    }
}
