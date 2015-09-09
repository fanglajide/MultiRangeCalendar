package lecalendar.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import lecalendar.model.DayModel
import java.util.Calendar
import java.util.Date

/**
 * Created by chanlevel on 15/9/7.
 */


public class DayView : TextView {

    var daymodel: DayModel = DayModel(false, false, null, 0, 0, null, Date(), false, false, false, false);
    var mPaint = Paint();
    var upColor = Color.RED;
    var midColor = Color.BLACK;
    var belowColor = Color.CYAN;
    var backgroudColor = Color.WHITE;

    var open = true;

    public constructor(context: Context) : super(context) {
        init()
    }

    public constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    public constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }


    private fun init() {
      //  if (daymodel != null)

        setOnClickListener {

            v ->
        //    toggle();
            Toast.makeText(getContext(), daymodel.toString(), Toast.LENGTH_SHORT).show()


        }

    }


    public fun setDayModel(daymodel: DayModel) {
        this.daymodel = daymodel;
        open = daymodel.room_num > 0
        Log.d("DAYVIEW","setdaymodel");
        invalidate()
    }

    public fun setStatus(open: Boolean) {
        this.open = open;
        Log.d("DAYVIEW","setStatus:"+open);
        invalidate()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {

    }

    override fun onDraw(canvas: Canvas) {
       // Log.d("DAYVIEW","onDraw");
        super<TextView>.onDraw(canvas)
        var c: Calendar = Calendar.getInstance();
        c.setTime(daymodel.date);


        var totalHeight = getMeasuredHeight() - getPaddingBottom() - getPaddingTop();
        var totalWidh = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        //draw festival
        var festivalHeight = totalHeight * 1f / 4f;
        var festTextHeight = totalHeight * 1f / 4f;
        var festivalPaint = Paint();
        festivalPaint.setAntiAlias(true)
        festivalPaint.setTextSize(festTextHeight);
        festivalPaint.setColor(upColor);
        festivalPaint.setTextAlign(Paint.Align.CENTER)
        var up: String ? = "";
        if (daymodel.isFestival) up = daymodel.festival;
        else if (daymodel.isFirstDayofMonth) {
            up = (1 + c.get(Calendar.MONTH)).toString() + "月"
        }
        canvas.drawText(up, totalWidh.toFloat() / 2f + getPaddingLeft().toFloat(), +getPaddingTop().toFloat() + festivalHeight.toFloat(), festivalPaint);

        //draw mid
        //  var midY = getPaddingTop() + festivalHeight + 5;
        var midTextHeight = totalHeight * 3f / 7f;
        festivalPaint.setColor(midColor);
        festivalPaint.setTextSize(midTextHeight);

        var mid = c.get(Calendar.DATE)
        canvas.drawText(mid.toString(), totalWidh.toFloat() / 2f + getPaddingLeft(), totalHeight / 2f + midTextHeight / 2f, festivalPaint);

        //draw price
        var belowY = totalHeight + getPaddingTop() ;
        var priceTextHeight = totalHeight / 4f;
        festivalPaint.setColor(belowColor);
        festivalPaint.setTextSize(priceTextHeight);
        var price = daymodel.price ;
        canvas.drawText(price.toString(), totalWidh.toFloat() / 2f + getPaddingLeft(), belowY.toFloat(), festivalPaint);

        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(5f)
        if (daymodel.isFirstWeekinMonth) {
            mPaint.setColor(Color.YELLOW)
            canvas.drawLine(0f, 0f, getMeasuredWidth().toFloat(), 0f, mPaint)
        };
        if (daymodel.isFirstDayofMonth) {
            mPaint.setColor(Color.BLUE)
            canvas.drawLine(0f, 0f, 0f, getMeasuredHeight().toFloat().toFloat(), mPaint)
        };
        if (daymodel.isLastWeekinMonth) {
            mPaint.setColor(Color.RED)
            canvas.drawLine(0f, getMeasuredHeight().toFloat(), getMeasuredWidth().toFloat(), getMeasuredHeight().toFloat(), mPaint)
        };

        if (!open) backgroudColor = Color.GRAY;
        else if (daymodel.isToday) backgroudColor = Color.RED;
        else backgroudColor = Color.WHITE
        setBackgroundColor(backgroudColor)

    }

    public fun toggle() {
        setStatus(!open)


    }

    var callBack: DayViewCallBack ? = null;

    public fun setdayViewCallBack(callback: DayViewCallBack) {
        this.callBack = callback;
    }

    public interface DayViewCallBack {
        fun callBack(v: View, dayModel: DayModel?)
    }
    override fun  setBackgroundColor(color:Int){
       // Log.d("DAYVIEW","setBackgroundColor");
        super<TextView>.setBackgroundColor(color);
    }


}