package mx.ctrlpg.util.calendar

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.ognev.kotlin.agendacalendarview.CalendarManager
import com.ognev.kotlin.agendacalendarview.models.CalendarEvent
import com.ognev.kotlin.agendacalendarview.render.DefaultEventAdapter
import com.ognev.kotlin.agendacalendarview.utils.DateHelper
import mx.ctrlpg.R
import java.text.SimpleDateFormat
import java.util.*

class EventAgendaAdapter (var context: Context) : DefaultEventAdapter() {
        private var format: SimpleDateFormat? = null

        init {
            format = SimpleDateFormat(context.getString(com.ognev.kotlin.agendacalendarview.R.string.header_date),
                Locale.ENGLISH)
        }

        override fun getHeaderLayout(): Int {
            return R.layout.view_agenda_header
        }

        override fun getHeaderItemView(view: View, day: Calendar) {
            val txtDayOfMonth = view.findViewById(R.id.view_agenda_day_of_month) as TextView
            val today = Calendar.getInstance()

            if (DateHelper.sameDate(day, today)) {
                txtDayOfMonth.setTextColor(context.resources.getColor(R.color.colorPrimary))
            } else{
                txtDayOfMonth.setTextColor(context.resources.getColor(R.color.text_light_color))
            }

            txtDayOfMonth.text = format!!.format(day.time)
        }

        override fun getEventItemView(view: View, event: CalendarEvent, position: Int) {
            val myEvent = event as MyCalendarEvent
            val myObject: SampleEvent? = myEvent.event as SampleEvent?

            if(myEvent.hasEvent()) {
                (view.findViewById(R.id.name)
                        as TextView).text = myObject!!.name

                (view.findViewById(R.id.description)
                        as TextView).text = myObject.description
            }

            view.setOnClickListener {
                val bundle = bundleOf("cacId" to myObject!!.id)
                it.findNavController().navigate(R.id.action_nav_home_to_dayFragment,bundle)
            }
        }

        override fun getEventLayout(hasEvent: Boolean): Int {
            return if(hasEvent) R.layout.view_agenda_event else R.layout.view_agenda_empty_event
        }
 }
