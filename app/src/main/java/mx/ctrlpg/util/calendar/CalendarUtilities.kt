package mx.ctrlpg.util.calendar

import android.content.Context
import android.icu.text.DateFormat.DAY
import com.ognev.kotlin.agendacalendarview.CalendarManager
import com.ognev.kotlin.agendacalendarview.models.CalendarEvent
import com.ognev.kotlin.agendacalendarview.models.DayItem
import com.ognev.kotlin.agendacalendarview.utils.DateHelper
import mx.ctrlpg.data.model.Evento
import java.text.SimpleDateFormat
import java.util.*


fun listToEvent(
    lista: List<Evento>,
    context: Context?,
    eventList: MutableList<CalendarEvent>
): MutableList<CalendarEvent> {
    val calNow = Calendar.getInstance(Locale.ENGLISH)
    val calStart = Calendar.getInstance(Locale.ENGLISH)
    val calEnd = Calendar.getInstance(Locale.ENGLISH)
    val calDay = Calendar.getInstance(Locale.ENGLISH)
    val sdf = SimpleDateFormat("yyyy/MM/dd")
    val sdfDay = SimpleDateFormat("yyyy/MM/dd")
    var dayItem: DayItem? = null
    for (event in lista){
        if(event.cacHoraIncio!="")
            calStart.time = sdf.parse(event.cacHoraIncio)
        if(event.cacHoraFin!="")
            calEnd.time = sdf.parse(event.cacHoraFin)
        if(event.cacFecha!="") {
            calDay.time = sdfDay.parse(event.cacFecha)
            dayItem = getDayItem(calDay,context)
        }else{
            dayItem = null
        }
        if(calDay.get(Calendar.MONTH) == calNow.get(Calendar.MONTH)) {
            if ((event.cacTitle != "") && (event.cacCliDescripcion != "") && (dayItem != null)) {
                val day = Calendar.getInstance(Locale.ENGLISH)
                day.timeInMillis = System.currentTimeMillis()
                day.set(Calendar.DAY_OF_MONTH, calDay.get(Calendar.DAY_OF_MONTH))
                val baseEvent =
                    SampleEvent(id=event.cacId.toLong(),name = event.cacTitle, description = event.cacCliDescripcion)
                val baseCalendarEvent = MyCalendarEvent(day, day, dayItem, baseEvent)
                baseCalendarEvent.setEventInstanceDay(calDay)
                eventList.add(baseCalendarEvent)

            }
        }
    }
    return eventList

}

fun getDayItem(calendarDay: Calendar, context: Context?): DayItem? {
    val date = calendarDay.time
    var dayItem: DayItem? = null
    val monthHalfNameFormat = SimpleDateFormat("MMM")
    if(context!= null) {
        val instance = CalendarManager.getInstance(context)

        val isToday = DateHelper.sameDate(calendarDay, instance.today)
        val value = calendarDay.get(Calendar.DAY_OF_MONTH)
        dayItem = DayItem(date, value, isToday, monthHalfNameFormat.format(date))
        if (value == 1) {
            dayItem.isFirstDayOfTheMonth = true
        }
        dayItem.isToday = isToday

    }
    return dayItem
}
fun Calendar.isSameDay(newDate: Calendar): Boolean =
    this.get(Calendar.DAY_OF_MONTH) == newDate.get(Calendar.DAY_OF_MONTH)