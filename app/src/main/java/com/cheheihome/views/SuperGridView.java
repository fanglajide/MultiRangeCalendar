package com.cheheihome.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.GridView;

/**
 * Created by chanlevel on 15/9/8.
 */
public class SuperGridView extends GridView implements AbsListView.OnScrollListener {
    public SuperGridView(Context context) {
        super(context);
    }

    public SuperGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SuperGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        setOnScrollListener(this);
    }

    SlideMode mSlideMode;
    int lastPointX, lastPointY;
    boolean isNewEvent;
    int startPosion = 0, endPostion = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("touch-event", "down");
//                mSlideMode = null;
//                isNewEvent = true;
//                lastPointX = (int) event.getX();
//                lastPointY = (int) event.getY();
//                startPosion = pointToPosition(lastPointX, lastPointY);
                // if (mCallBack != null) mCallBack.onDown(startPosion);
                break;
            case MotionEvent.ACTION_MOVE:

                Log.d("touch-event", "move");
                int moveX = (int) event.getX();
                int moveY = (int) event.getY();
                if (isNewEvent) {
                    if (Math.abs(lastPointX - moveX) > 100) {
                        mSlideMode = SlideMode.SELECT;
                        isNewEvent = false;
                      //  endPostion = pointToPosition(moveX, moveY);

                    } else {
                        mSlideMode = SlideMode.SCROLL;
                        isNewEvent = false;
                        return super.onTouchEvent(event);
                    }
                } else {
                    if (mSlideMode == SlideMode.SELECT) {
                       // endPostion = pointToPosition(moveX, moveY);

                    } else return super.onTouchEvent(event);
                }
                endPostion = pointToPosition(moveX, moveY);
                if(endPostion==-1) return true;
                Log.d("super-end","end:"+endPostion+"-X:"+moveX+"-Y:"+moveY);
                Log.d("Super-MOVE-start",  "start:"+startPosion+ "---end:" +endPostion);
                Log.d("Super-MOVE-start", "ts:" +ts+ "---te:"+te);

                if (mCallBack != null && (ts != startPosion ||te != endPostion)) {
                    mCallBack.onMove(startPosion, endPostion);
                    Log.d("Super-MOVE-start", startPosion + "");
                    Log.d("Super-MOVE-end", endPostion + "");
                    ts = startPosion;
                    te = endPostion;
                } return true;
            // break;
            case MotionEvent.ACTION_UP:
                Log.d("touch-event", "up");
                if(mSlideMode==SlideMode.SELECT){
                    endPostion = pointToPosition((int) event.getX(), (int) event.getY());
                    if(endPostion==-1)break;
                    if (mCallBack != null) mCallBack.onUp(startPosion, endPostion);
                    startPosion = 0;
                    endPostion = 0;
                    ts=-1;te=-1;
                }

//                endPostion = pointToPosition((int) event.getX(), (int) event.getY());
//                if (mCallBack != null) mCallBack.onUp(startPosion, endPostion);
//                startPosion = 0;
//                endPostion = 0;
                break;


        }
        return super.onTouchEvent(event);
    }

    int ts, te;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("touch-inter", "down");

                mSlideMode = null;
                isNewEvent = true;
                lastPointX = (int) event.getX();
                lastPointY = (int) event.getY();
                startPosion = pointToPosition(lastPointX, lastPointY);

                //avoid action_down at divider
                if(startPosion==-1)startPosion=pointToPosition(lastPointX+getHorizontalSpacing(),lastPointY+getVerticalSpacing());
                if (startPosion >= 0)
                    if (mCallBack != null) mCallBack.onDown(startPosion);
                Log.d("Super-onDown", startPosion + "");
                break;
            case MotionEvent.ACTION_MOVE:

                Log.d("touch-inter", "move");
                int moveX = (int) event.getX();
                if (isNewEvent)
                    if (Math.abs(lastPointX - moveX) > 100)
                    {mSlideMode=SlideMode.SELECT;
                        return true;
                    }
                break;
            case MotionEvent.ACTION_UP:

                Log.d("touch-inter", "up");
               int upPointX = (int) event.getX();
                int upPointY = (int) event.getY();
                    endPostion = pointToPosition((int) event.getX(), (int) event.getY());

                if ((Math.abs(lastPointX - upPointX) < 80)&&(Math.abs(lastPointY - upPointY) < 80))
                      if (mCallBack != null) mCallBack.onUp(startPosion, endPostion);

            //    endPostion = pointToPosition((int) event.getX(), (int) event.getY());

              //  if (mCallBack != null) mCallBack.onUp(startPosion, endPostion);
//                startPosion = 0;
//                endPostion = 0;
                break;
        }
        return super.onInterceptTouchEvent(event);
    }


    private enum SlideMode {
        SELECT,
        SCROLL;
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    private SuperCallBack mCallBack;

    public void setSuperCallBack(SuperCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    public interface SuperCallBack {
        void onDown(int postion);

        void onMove(int startpostion, int endpostion);

        void onUp(int startpostion, int endpostion);

    }

    @Override
    public void deferNotifyDataSetChanged() {
        Log.d("super", "deferNotifyDataSetChanged");

        super.deferNotifyDataSetChanged();
    }

    @Override
    public void invalidate() {
        Log.d("super", "invalidate");
        if (mSlideMode == SlideMode.SELECT) return;
        super.invalidate();
    }
}
