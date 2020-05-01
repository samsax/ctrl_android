package mx.ctrlpg.util.calendar

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
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
                Locale("es","ES"))
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
            val myObject: SampleEvent? = event.event as SampleEvent?

            if(event.hasEvent()) {
                (view.findViewById(R.id.name)
                        as TextView).text = myObject!!.name

                (view.findViewById(R.id.description)
                        as TextView).text = myObject.description
            }

            view.setOnClickListener {
                if(myObject!!.evento!=null) {
                    val bundle = bundleOf("evento" to myObject!!.evento)
                    it.findNavController().navigate(R.id.action_nav_home_to_event_fragment, bundle)
                }
            }
            }

        override fun getEventLayout(hasEvent: Boolean): Int {
            return if(hasEvent) R.layout.view_agenda_event else R.layout.view_agenda_empty_event
        }
 }
