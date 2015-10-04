package lecalendar.model

import lecalendar.utils.DateUtils
import java.util.Calendar
import java.util.Date
import java.util.logging.Logger

/**
 * Created by chanlevel on 15/9/7.
 */

var dateUtils: DateUtils = DateUtils();

data class DayModel(
        var isToday: Boolean,
        var isFestival: Boolean,
        var festival: String?,
        var room_num: Int,
        var price: Int,
        var notice: String?,
        var date: Date,
        var isFirstDayofMonth: Boolean,
        var isFirstWeekinMonth: Boolean,
        var selecting: Boolean,
        var isWeekEnd: Boolean,
        var beforeToday:Boolean

) {
    public fun init() {

        var c: Calendar = Calendar.getInstance();
        val today = Calendar.getInstance();
        c.setTime(date);

        isToday = dateUtils.sameDay(c, today);
        beforeToday=dateUtils.beforeToday(c)
        isFirstDayofMonth = c.get(Calendar.DAY_OF_MONTH) == 1
        isFirstWeekinMonth = c.get(Calendar.DAY_OF_WEEK_IN_MONTH) ==1
      //  isLastWeekinMonth = dateUtils.isLast7dayOfMonth(c)


    }
}