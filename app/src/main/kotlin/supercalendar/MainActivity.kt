package supercalendar

import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.widget.AbsListView
import android.widget.Toast
import com.cheheihome.LeCalendar
import kotlinx.android.synthetic.activity_main.*
import com.cheheihome.supercalendar.R

import java.util.ArrayList
import java.util.Calendar
import java.util.Date 

import lecalendar.model.DayModel
import lecalendar.views.DayView

/**
 * Created by chanlevel on 15/9/8.
 */
public class MainActivity : AppCompatActivity(), AbsListView.OnScrollListener {

    var flag: Boolean = false
    var type: DayView.SElECTTYPE = DayView.SElECTTYPE.STATUS

    override fun onCreate(savedInstanceState: Bundle?) {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().penaltyFlashScreen().penaltyLog().build())

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        val b = Calendar.getInstance()
        b.add(Calendar.DATE, -1)
       // b.set(Calendar.DAY_OF_WEEK, 1)
        val e = Calendar.getInstance()
        e.add(Calendar.YEAR, 1)
        e.set(Calendar.DAY_OF_WEEK, 7)
        getDates(b, e)
        calendar.setDays(days)
        calendar.setSelectListener(object : LeCalendar.LeSelectListener {
            override fun onSelectPosition(status: Boolean, start: Int, end: Int) {

            }

            override fun OnTotalSelected(dates: List<Date>) {
                Toast.makeText(this@MainActivity, dates.size().toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }


    fun getDates(begin: Calendar, end: Calendar) {
        val temp = begin
        // if (this.days == null) this.days = ArrayList<DayModel>();
        while (temp.before(end)) {
            val dayModel = DayModel(false, false, null, 0, 0, null, temp.time, false, false, false, false, false)
            dayModel.room_num = (Math.random() * 5).toInt()
            dayModel.isFestival = if ((Math.random() * 5).toInt()> 3) true else false
            dayModel.price = dayModel.room_num * 10
            dayModel.init()
            this.days.add(dayModel)

            temp.add(Calendar.DATE, 1)
        }


    }

    var days = ArrayList<DayModel>()

    override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {

    }

    override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
        this.visiblecount = visibleItemCount
    }

    var visiblecount: Int = 0


}
