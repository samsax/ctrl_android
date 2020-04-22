package mx.ctrlpg.util.calendar

import com.ognev.kotlin.agendacalendarview.models.*
import java.util.*

class MyCalendarEvent: BaseCalendarEvent {

    override lateinit var startTime: Calendar
    override lateinit var endTime: Calendar
    override var event: Any? = null

    override lateinit var instanceDay: Calendar


    override lateinit var dayReference: IDayItem

    override lateinit var weekReference: IWeekItem

    override fun setEventInstanceDay(instanceDay: Calendar): MyCalendarEvent {
        this.instanceDay = instanceDay
        this.instanceDay.set(Calendar.HOUR, 0)
        this.instanceDay.set(Calendar.MINUTE, 0)
        this.instanceDay.set(Calendar.SECOND, 0)
        this.instanceDay.set(Calendar.MILLISECOND, 0)
        this.instanceDay.set(Calendar.AM_PM, 0)
        return this
    }

    constructor(calendarEvent: CalendarEvent) {
    }


    constructor(startTime: Calendar,
                endTime: Calendar,
                dayItem: DayItem,
                event: SampleEvent?)  {
        this.startTime = startTime
        this.endTime = endTime
        this.dayReference = dayItem
        this.event = event
    }


    override
    fun copy(): MyCalendarEvent {
        return MyCalendarEvent(this)
    }

    override fun hasEvent(): Boolean {
        return event != null
    }

    override
    fun toString(): String {
        return super.toString()
    }
}