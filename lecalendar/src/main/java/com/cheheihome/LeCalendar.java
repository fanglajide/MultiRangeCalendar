package com.cheheihome;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lecalendar.model.DayModel;
import lecalendar.views.DayView;

/**
 * Created by chanlevel on 15/9/10.
 */
public class LeCalendar extends InterceptGridView implements InterceptGridView.SuperCallBack {
    private DayView.SElECTTYPE mSelectType = DayView.SElECTTYPE.STATUS;
    private
    ArrayList<DayModel> days = new ArrayList<>();
    private LeAdapter adapter;

    public LeCalendar(Context context) {
        super(context);
        init();
    }

    public LeCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LeCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
       // View header = LayoutInflater.from(getContext()).inflate(R.layout.calendar_header, null);
     //   addHeaderView(header);

    }

    public void setType(DayView.SElECTTYPE type) {
        if (mSelectType != type) {
            mSelectType = type;
            onRefresh();
        }
    }


    public void onRefresh() {
        if (adapter == null) {
            adapter = new LeAdapter(getContext(), days, mSelectType);
            setAdapter(adapter);
        }
    }

    public void setDays(ArrayList<DayModel> days) {
        this.days = days;

        if (adapter == null) {
            adapter = new LeAdapter(getContext(), days, mSelectType);
            setAdapter(adapter);
        }
        setSuperCallBack(this);
    }


    @Override
    public void onDown(int postion) {

        DayModel startModel = (DayModel) adapter.getItem(postion);
        //  startModel.setRoom_num(startModel.getRoom_num() == 0 ? 1 : 0);
        open = startModel.getRoom_num() == 0;
        selecting = !startModel.getSelecting();
        //       DayView dayView = (DayView) gridView .getChildAt(postion - getFirstVisiblePosition());
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

    boolean selecting;
    boolean open;
    int tempB = -1, tempE = -1;

    private void update(int startpostion, int endpostion, boolean update) {

        //  if (tempB == startpostion && tempE == endpostion) return;
        Log.d("super-upadte", startpostion + "---" + endpostion);


        if (startpostion > endpostion) {
            int t = startpostion;
            startpostion = endpostion;
            endpostion = t;
        }
        if (tempB == -1) tempB = startpostion;
        if (tempE == -1) tempE = endpostion;


        if (tempB > startpostion) tempB = startpostion;
        if (tempE < endpostion) tempE = endpostion;

        Log.d("super-onScroll", "tempB:" + tempB + "--tempE:" + tempE);

        for (int i = tempB; tempB <= i && i <= tempE; i++) {
            int child = i - getFirstVisiblePosition();
            Log.d("super-child", child + "");
            DayView dayView = (DayView) getChildAt(child);
            Log.d("super-onScroll", "tempB:" + tempB + "-s:" + startpostion + "--tempE:" + tempE + "-e:" + endpostion);

            if (i >= startpostion && i <= endpostion) {
                //    int color = open ? Color.GRAY : Color.WHITE;
                Log.d("SUPER-setstatus", i + "");

                //   if (dayView != null && dayView.getOpen() != open) dayView.setStatus(open);
                if (dayView != null && dayView.getSelecting() != selecting)
                    dayView.setSelectingStatus(selecting);
            } else {
                Log.d("SUPER-reset", i + "");
                //    dayView.setStatus(days.get(i).getRoom_num() > 0);
                dayView.setSelectingStatus(!selecting);
            }

        }
        if (update) {
            DayCaculation d = new DayCaculation(selecting, startpostion, endpostion);
            new Thread(d).start();
            for (int i = startpostion; i <= endpostion; i++) {
//                if (i >= 0)
                //                  days.get(i).setRoom_num(open ? 1 : 0);
                DayModel dayModel = days.get(i);
                dayModel.setSelecting(selecting);

            }
            if (selectListener != null)
                selectListener.onSelectPosition(selecting, startpostion, endpostion);
            tempB = -1;
            tempE = -1;

        }
    }

    List<Date> totalSelected = new ArrayList<>();

    public List<Date> getTotalSelected() {
        return totalSelected;
    }

    private class DayCaculation implements Runnable {

        boolean selecting;
        int start, end;

        public DayCaculation(boolean selecting, int start, int end) {
            this.start = start;
            this.end = end;
            this.selecting = selecting;
        }

        @Override
        public void run() {

            for (int i = start; i <= end; i++) {
                DayModel dayModel = days.get(i);
                if (selecting) totalSelected.add(dayModel.getDate());
                else if (totalSelected.contains(dayModel.getDate()))
                    totalSelected.remove(dayModel.getDate());

            }

        }
    }


    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };


    private LeSelectListener selectListener;

    public void setSelectListener(LeSelectListener listener) {
        this.selectListener = listener;
    }

    public interface LeSelectListener {
        //  void onSelectDayModels(boolean status, ArrayList<DayModel> selectDays);
        void onSelectPosition(boolean status, int start, int end);


    }

}
