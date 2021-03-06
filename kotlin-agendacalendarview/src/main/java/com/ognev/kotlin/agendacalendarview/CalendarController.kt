package com.ognev.kotlin.agendacalendarview

import android.view.View
import android.widget.AdapterView
import com.ognev.kotlin.agendacalendarview.models.IDayItem

import java.util.Calendar

interface CalendarController {

    fun getEmptyEventLayout(): Int

    fun getEventLayout() : Int

    fun onDaySelected(dayItem: IDayItem)

    fun onScrollToDate(calendar: Calendar)

    fun onEventSelected(parent: AdapterView<*>, view: View, position: Int, id: Long)

}
