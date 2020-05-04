package mx.ctrlpg.util.calendar

import android.content.Context
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
    val locale = Locale("es","ES")
    val calNow = Calendar.getInstance(locale)
    val calStart = Calendar.getInstance(locale)
    val calEnd = Calendar.getInstance(locale)
    val calDay = Calendar.getInstance(locale)
    val sdf = SimpleDateFormat("yyyy/MM/dd")
    val sdfDay = SimpleDateFormat("yyyy/MM/dd")
    var dayItem: DayItem? = null
    for (event in lista){
        if(event.cacHoraIncio!="") {
            calStart.time = sdf.parse(event.cacHoraIncio)
            calDay.time = sdfDay.parse(event.cacFecha)
            dayItem = getDayItem(calDay,context)
        }
        if(event.cacHoraFin!="")
            calEnd.time = sdf.parse(event.cacHoraFin)
        if ((event.cacTitle != "") && (event.cacCliDescripcion != "") && (dayItem != null)) {
            calStart.set(Calendar.DAY_OF_MONTH, calStart.get(Calendar.DAY_OF_MONTH))
            val day = calStart.clone() as Calendar
            day.set(Calendar.DAY_OF_MONTH, calStart.get(Calendar.DAY_OF_MONTH))
            val baseEvent =
                SampleEvent(
                    id =event.cacId.toLong(),
                    name = event.cacTitle, description = event.cacCliDescripcion, evento = event)
            val baseCalendarEvent = MyCalendarEvent(day, day, dayItem, baseEvent)
            baseCalendarEvent.setEventInstanceDay(calDay)
            eventList.add(baseCalendarEvent)
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



fun Calendar.toStringFormat(): String? {
    val time = this.time
    val format = SimpleDateFormat("yyyy/MM/dd")
    return format.format(time)
}