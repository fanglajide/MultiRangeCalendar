package com.cheheihome.supercalendar

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
import android.widget.BaseAdapter
import android.widget.Toast
import com.cheheihome.SuperGridView
import kotlinx.android.synthetic.activity_main.dayView
import kotlinx.android.synthetic.activity_main.gridView
import kotlinx.android.synthetic.activity_main.toolbar
import lecalendar.model.DayModel
import lecalendar.views.DayView
import java.util.ArrayList
import java.util.Calendar
import java.util.Date

public class MainActivity : AppCompatActivity(), SuperGridView.SuperCallBack {

    var mContext: Context = this;
    override fun onCreate(savedInstanceState: Bundle?) {
        super<AppCompatActivity>.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //gridView = findViewById(R.id.gridView) as GridView

        toolbar.setTitle(11.toString())
        setGridView()
        setDayView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.getItemId()

        //        //noinspection SimplifiableIfStatement
        //        if (id == R.id.action_settings) {
        //
        //            return true
        //        }

        when (id) {
            R.id.action_settings -> {
                setDayView() ;return true
            }
        }


        return super<AppCompatActivity>.onOptionsItemSelected(item)
    }

    fun setDayView() {
        var dayModel: DayModel = DayModel(false, false, null, 0, 0, null, Date(), false, false, false, false);

        dayModel.isFestival = true
        dayModel.festival = "lala"
        dayModel.isFirstWeekinMonth = true;
        dayModel.isFirstDayofMonth = true
        dayModel.room_num = 200
        dayModel.isLastWeekinMonth = true
        dayModel.price = 2000
        dayModel.init();
        dayView.setDayModel(dayModel)

    }


    var days: ArrayList<DayModel> = ArrayList<DayModel>();
    var temp = Calendar.getInstance();
    fun getDates(begin: Calendar, end: Calendar): ArrayList<DayModel>? {
        temp = begin;
        // if (this.days == null) this.days = ArrayList<DayModel>();
        while (temp.before(end)) {
            var dayModel: DayModel = DayModel(false, false, null, 0, 0, null, temp.getTime(), false, false, false, false);
            dayModel.init();
            dayModel.price=(Math.random()*1000f).toInt();
            this.days.add(dayModel)
            temp.add(Calendar.DATE, 1)
        }
        return days;

    }


    var i = 0

    private fun setGridView() {
        var b: Calendar = Calendar.getInstance();
        b.add(Calendar.MONTH, -1);
        b.set(Calendar.DAY_OF_WEEK, 1);
        var e: Calendar = Calendar.getInstance();
        e.add(Calendar.YEAR, 1);
        e.set(Calendar.DAY_OF_WEEK, 7)
        getDates(b, e)
        gridView.setAdapter(MyAdapter(mContext))
        gridView.setSuperCallBack(
                this

        )
        //gridView.setOnItemClickListener { view, parent, i, l ->System.out.print(i.toString())  }

    }

    public fun click(v: View?) {
        showToast("click")
    }
    override fun onDown(postion: Int) {
        Log.d("Super-onDown",postion.toString())
        throw UnsupportedOperationException()
    }

    override fun onMove(startpostion: Int, endpostion: Int) {
        Log.d("Super-MOVE-start",startpostion.toString())
        Log.d("Super-MOVE-end",endpostion.toString())
        throw UnsupportedOperationException()
    }

    override fun onUp(startpostion: Int, endpostion: Int) {
        throw UnsupportedOperationException()
    }


    private inner class MyAdapter : BaseAdapter {
        var context: Context;

        constructor(context: Context) {
            this.context :Context = context;
        }

        override fun getCount(): Int {
            return days.size()
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            //            var tv = TextView(context)
            //            tv.setHeight(150)
            //            tv.setText(position.toString())
            //            // tv.setText("21");
            //            tv.setGravity(Gravity.CENTER)
            //            //  tv.setOnClickListener {  (context as Activity).showToast(position.toString()) }
            //            tv.setOnClickListener(View.OnClickListener() {
            //
            //                Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()
            //                // (context as Activity).showToast(position.toString())
            //                //System.out.print(position.toString())
            //            })
            //            var dayView: DayView = DayView(context);
            //            dayView.setHeight(150)
            //            dayView.setDayModel(days.get(position));
            var holder: ViewHolder;
            if (convertView == null) {
                var view: View = LayoutInflater.from(context).inflate(R.layout.item_day, null)


                holder = ViewHolder();
                holder.dayView = view.findViewById(R.id.dayView) as DayView;
                view.setMinimumHeight(150)
                view.setTag(holder)
                holder.dayView?.setDayModel(days.get(position))
                return view;
            } else {
                holder = convertView.getTag() as ViewHolder ;
                holder.dayView?.setDayModel(days.get(position))
               convertView.setMinimumHeight(150)
                return convertView;
            };




            return dayView;
        }
    }

    private class ViewHolder {
        var dayView: DayView? = null;
    }

    fun Activity.showToast(str: String) {
        Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT).show()
    }


}
