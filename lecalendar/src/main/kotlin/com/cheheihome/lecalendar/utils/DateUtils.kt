package lecalendar.utils

import java.util.*

/**
 * Created by chanlevel on 15/9/8.
 */

public class DateUtils {

    public fun sameDay(a: Calendar, b: Calendar): Boolean {


        return (a.get(Calendar.YEAR) == b.get(Calendar.YEAR) && a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) == b.get(Calendar.DATE))
    }

    public fun aBeforeb(a: Calendar, b: Calendar): Boolean {
        if (a.get(Calendar.YEAR) > b.get(Calendar.YEAR)) return false;
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH)) return false;
        if (a.get(Calendar.DAY_OF_MONTH) >= b.get(Calendar.DAY_OF_MONTH)) return false;
        return true;
    }

    public fun beforeToaday(a: Calendar): Boolean {
        var today = Calendar.getInstance();
        return aBeforeb(a, today)
    }

    public fun isInLastWeekOfMonth(c: Calendar): Boolean {
        var weekofmonth = c.get(Calendar.WEEK_OF_MONTH)

        var t = c
        t.set(Calendar.DAY_OF_MONTH, 1);
        t.add(Calendar.MONTH, 1);
        t.add(Calendar.DATE, -1);
        if (weekofmonth == t.get(Calendar.WEEK_OF_MONTH)) return true;
        return false
    }

    public fun isLast7dayOfMonth(c: Calendar): Boolean {
        var dayofmonth = c.get(Calendar.DAY_OF_MONTH)
        var t = c
        t.set(Calendar.DAY_OF_MONTH, 1);
        t.add(Calendar.MONTH, 1);
        t.add(Calendar.DATE, -1);

        var tdayofmonth = c.get(Calendar.DAY_OF_MONTH);
        if (tdayofmonth - dayofmonth < 7) return true
        return false;
    }


}
