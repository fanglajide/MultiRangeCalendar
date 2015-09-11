package com.cheheihome;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.cheheihome.supercalendar.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
                for (Date d : gridView.getTotalSelected())
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
        gridView.setDays(days);
        // setGridView();

        //     setGrid();
    }

    private void setGrid() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DayModel dayModel = days.get(position);
                //  dayModel.setRoom_num(dayModel.getRoom_num() == 0 ? 1 : 0);
                DayView dayView = (DayView) gridView.getChildAt(position - gridView.getFirstVisiblePosition());
                // dayView.setDayModel(dayModel);
                //    dayView.toggle();

            }
        });
        gridView.setSuperCallBack(new SuperGridView.SuperCallBack() {
            @Override
            public void onDown(int postion) {

                DayModel startModel = (DayModel) gridView.getAdapter().getItem(postion);
                //  startModel.setRoom_num(startModel.getRoom_num() == 0 ? 1 : 0);
                open = startModel.getRoom_num() == 0;

                //       DayView dayView = (DayView) gridView .getChildAt(postion - gridView.getFirstVisiblePosition());
                // dayView.setDayModel(startModel);
            }

            @Override
            public void onMove(int startpostion, int endpostion) {
                Log.d("super-callback", "onMove:start:" + startpostion + "--end:" + endpostion);
                update(startpostion, endpostion, false);

            }

            @Override
            public void onUp(int startpostion, int endpostion) {

                Log.d("super-callback", "onUp:start:" + startpostion + "--end:" + endpostion);
                update(startpostion, endpostion, true);

            }
        });
    }

    boolean open;
    int tempB, tempE;

    private void update(int startpostion, int endpostion, boolean update) {

        //  if (tempB == startpostion && tempE == endpostion) return;
        Log.d("super-upadte", startpostion + "---" + endpostion);
        tempB = startpostion;
        tempE = endpostion;

        if (startpostion > endpostion) {
            int t = startpostion;
            startpostion = endpostion;
            endpostion = t;
        }
        Log.d("super-onScroll", "firstvisibleitem:" + gridView.getFirstVisiblePosition() + "--lastvisiblepositon:" + gridView.getLastVisiblePosition());

        for (int i = gridView.getFirstVisiblePosition(); gridView.getFirstVisiblePosition() <= i && i < gridView.getLastVisiblePosition(); i++) {
            int child = i - gridView.getFirstVisiblePosition();
            Log.d("super-child", child + "");
            DayView dayView = (DayView) gridView.getChildAt(child);

            if (i >= startpostion && i <= endpostion) {
                //    int color = open ? Color.GRAY : Color.WHITE;
                Log.d("SUPER-setstatus", i + "");

                if (dayView != null && dayView.getOpen() != open) dayView.setStatus(open);
            } else {
                Log.d("SUPER-reset", i + "");
                //  DayModel dayModel = ;
                // dayView.setOpen(dayModel.getRoom_num()>0);
                //  Log.d("SUPER-reset", i + "--room_num:" + dayModel.getRoom_num());
                dayView.setStatus(days.get(i).getRoom_num() > 0);
                // dayView.invalidate();

            }

        }
        if (update) {
            for (int i = startpostion; i <= endpostion; i++) {
                // DayView dayView = (DayView) gridView.getChildAt(i - gridView.getFirstVisiblePosition());

                //    int color = open ? Color.GRAY : Color.WHITE;
                // if (dayView != null) dayView.setOpen(open);
                //  Log.d("SUPER-get", i + "");
                if (i >= 0)
                    days.get(i).setRoom_num(open ? 1 : 0);
                // dayView.setDayModel(dayModel);
                //  (days.get(i)).setRoom_num(open ? 1 : 0);

//                if (startpostion == endpostion) {
//                    Log.d("SUPER-click", i + "");
//
//                    DayView dayView = (DayView) gridView.getChildAt(i - gridView.getFirstVisiblePosition());
//                    dayView.toggle();
//                }
            }
            //  adapter.notifyDataSetChanged();

        }
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
    MAapter adapter;

    private void setGridView() {
        Calendar b = Calendar.getInstance();
        b.add(Calendar.WEEK_OF_YEAR, -1);
        b.set(Calendar.DAY_OF_WEEK, 1);
        Calendar e = Calendar.getInstance();
        e.add(Calendar.YEAR, 1);
        e.set(Calendar.DAY_OF_WEEK, 7);
        getDates(b, e);
        adapter = new MAapter(MainActivity.this);
        gridView.setAdapter(adapter);

        //gridView.setOnItemClickListener { view, parent, i, l ->System.out.print(i.toString())  }

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.visiblecount = visibleItemCount;
    }

    int visiblecount;

    private class MAapter extends BaseAdapter {
        Context context;

        public MAapter(Context context) {
            this.context = context;
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
                convertView.setMinimumHeight(150);
                convertView.setTag(holder);
                holder.dayView.setDayModel(days.get(position));
                return convertView;
            } else {
                holder = (ViewHolder) convertView.getTag();
                holder.dayView.setDayModel(days.get(position));
                convertView.setMinimumHeight(150);
                return convertView;
            }


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
    }

    private static class ViewHolder {
        DayView dayView;
    }
}
