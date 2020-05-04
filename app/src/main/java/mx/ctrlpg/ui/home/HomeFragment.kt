package mx.ctrlpg.ui.home

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ognev.kotlin.agendacalendarview.CalendarController
import com.ognev.kotlin.agendacalendarview.CalendarManager
import com.ognev.kotlin.agendacalendarview.builder.CalendarContentManager
import com.ognev.kotlin.agendacalendarview.models.CalendarEvent
import com.ognev.kotlin.agendacalendarview.models.DayItem
import com.ognev.kotlin.agendacalendarview.models.IDayItem
import kotlinx.android.synthetic.main.fragment_home.*
import mx.ctrlpg.ApiUtils
import mx.ctrlpg.R
import mx.ctrlpg.data.model.CalendarioRespuesta
import mx.ctrlpg.util.AlertUtil
import mx.ctrlpg.util.PreferenceHelper
import mx.ctrlpg.util.VariableConstants.AUTHORIZATION
import mx.ctrlpg.util.VariableConstants.USUARIONOMBRECOMPLETO
import mx.ctrlpg.util.VariableConstants.USUARIOSESION
import mx.ctrlpg.util.calendar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment(), CalendarController{

    private var oldDate: Calendar? = null
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var eventList: MutableList<CalendarEvent>
    private lateinit var tempEventList: MutableList<CalendarEvent>
    var dayItem: IDayItem? = null
    var calendarDay: Calendar? = null
    private var contentManager: CalendarContentManager? = null
    private var startMonth: Int = Calendar.getInstance().get(Calendar.MONTH)
    private var endMonth: Int = Calendar.getInstance().get(Calendar.MONTH)
    private var loadingTask: LoadingTask? = null
    private lateinit var minDate: Calendar
    private lateinit var maxDate: Calendar
    val sdf = SimpleDateFormat("yyyy/MM/dd")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text_home.text = String.format("Bienvenido: %s ",PreferenceHelper.read(USUARIONOMBRECOMPLETO,""))
        val agendaView = agenda_calendar_view.agendaView.setOnClickListener { v -> print(v) }
        oldDate = Calendar.getInstance()
        oldDate = Calendar.getInstance()
        minDate = Calendar.getInstance()
        maxDate = Calendar.getInstance()

        minDate.add(Calendar.MONTH, -10)
        minDate.add(Calendar.YEAR, -1)
        minDate.set(Calendar.DAY_OF_MONTH, 1)
        maxDate.add(Calendar.YEAR, 1)


        contentManager = context?.let { EventAgendaAdapter(it) }?.let {
            CalendarContentManager(this, agenda_calendar_view,
                it
            )
        }

        contentManager!!.locale = Locale("es", "ES")
        contentManager!!.setDateRange(minDate, maxDate)
        eventList = arrayListOf()
        val maxLength = Calendar.getInstance().getMaximum(Calendar.DAY_OF_MONTH)
        for (i in 1..maxLength) {
            val day = Calendar.getInstance(Locale("es","ES"))
            day.timeInMillis = System.currentTimeMillis()
            day.set(Calendar.DAY_OF_MONTH, i)

            eventList.add(MyCalendarEvent(day, day,
                DayItem.buildDayItemFromCal(day), null).setEventInstanceDay(day))
        }

        val fechaIni = Calendar.getInstance()
        fechaIni.set(Calendar.DAY_OF_MONTH,1)
        val fechaFin = Calendar.getInstance()
        fechaFin.set(Calendar.DAY_OF_MONTH, fechaIni.getActualMaximum(Calendar.DAY_OF_MONTH))
        fechaIni.toStringFormat()?.let { fechaFin.toStringFormat()?.let { it1 ->
            getCalendario(it,
                it1,
                true
            )
        } }
    }

    private fun getCalendario(fechIni:String, fechFin:String, addFromStart: Boolean){

        val call = ApiUtils.apiService.getCalendario(
            PreferenceHelper.read(USUARIOSESION,""),
            PreferenceHelper.read(USUARIOSESION,""),
            PreferenceHelper.read(AUTHORIZATION,""),
            fechIni,
            fechFin
        )

        call.enqueue(object : Callback<CalendarioRespuesta> {
            @SuppressLint("CommitPrefEdits")
            val alertUtil = AlertUtil(this@HomeFragment.activity as AppCompatActivity)
            override fun onResponse(call: Call<CalendarioRespuesta>, response: Response<CalendarioRespuesta>) {

                val calendarioRespuesta = response.body()
                if (calendarioRespuesta!=null) {
                    if(!calendarioRespuesta.error) {
                        setListToView(calendarioRespuesta)
                        if (addFromStart) {
                            contentManager?.loadItemsFromStart(tempEventList)
                        } else {
                            contentManager?.loadFromEndCalendar(tempEventList)
                        }
                        agenda_calendar_view.hideProgress()
                    }else{
                        alertUtil.showFailed(calendarioRespuesta.toString())
                    }
                } else {
                    alertUtil.showFailed("Error de conexión")
                }
            }
            override fun onFailure(call: Call<CalendarioRespuesta>, t: Throwable) {
                alertUtil.showFailed("Error de conexión")
            }
        })
    }
    fun setListToView(calendarResponse: CalendarioRespuesta){

        tempEventList = listToEvent(calendarResponse.list,this@HomeFragment.context,eventList)

    }

    override fun getEmptyEventLayout(): Int {
        return R.layout.view_agenda_empty_event
    }

    override fun getEventLayout(): Int {
        return R.layout.view_agenda_event
    }

    override fun onDaySelected(dayItem: IDayItem) {
        this.dayItem = dayItem
    }

    override fun onScrollToDate(calendar: Calendar) {
        val lastPosition = agenda_calendar_view.agendaView.agendaListView.lastVisiblePosition + 1

        val isSameDay = oldDate?.isSameDay(calendar) ?: false
        if (isSameDay && lastPosition == this@HomeFragment.context?.let {
                CalendarManager.getInstance(
                    it
                ).events.size
            }) {
            if (!agenda_calendar_view.isCalendarLoading()) { // condition to prevent asynchronous requests
                loadItemsAsync(false)
            }
        }

        if (agenda_calendar_view.agendaView.agendaListView.firstVisiblePosition == 0) {
            val minCal = Calendar.getInstance()
            minCal.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
            if (calendar.get(Calendar.DAY_OF_MONTH) == minCal.get(Calendar.DAY_OF_MONTH)) {
                if (!agenda_calendar_view.isCalendarLoading()) { // condition to prevent asynchronous requests
                    loadItemsAsync(true)
                }
            }
        }

        oldDate = calendar
    }

    override fun onEventSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

    }

    inner class LoadingTask(private val addFromStart: Boolean) : AsyncTask<Unit, Unit, Unit>() {

        private val startMonthCal: Calendar = Calendar.getInstance()
        private var endMonthCal: Calendar = Calendar.getInstance()

        override fun onPreExecute() {
            super.onPreExecute()
            agenda_calendar_view.showProgress()
        }

        override fun doInBackground(vararg params: Unit?) {
            if (addFromStart) {
                if (startMonth == 0) {
                    startMonth = 11
                } else {
                    startMonth--
                }

                startMonthCal.set(Calendar.MONTH, startMonth)

                if (startMonth == 11) {
                    var year = startMonthCal.get(Calendar.YEAR)
                    startMonthCal.set(Calendar.YEAR, ++year)
                }
                startMonthCal.set(Calendar.DAY_OF_MONTH, 1)
                endMonthCal = startMonthCal.clone() as Calendar
                endMonthCal.set(Calendar.DAY_OF_MONTH,startMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH))

                startMonthCal.toStringFormat()?.let { endMonthCal.toStringFormat()?.let { it1 ->
                    getCalendario(it,
                        it1,
                        true
                    )
                } }
            } else {
                if (endMonth >= 11) {
                    endMonth = 0
                } else {
                    endMonth++
                }

                endMonthCal.set(Calendar.MONTH, endMonth)
                if (endMonth == 0) {
                    var year = endMonthCal.get(Calendar.YEAR)
                    endMonthCal.set(Calendar.YEAR, ++year)
                }
                endMonthCal = startMonthCal.clone() as Calendar
                startMonthCal.set(Calendar.DAY_OF_MONTH, 1)
                endMonthCal.set(Calendar.DAY_OF_MONTH,startMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH))

                startMonthCal.toStringFormat()?.let { endMonthCal.toStringFormat()?.let { it1 ->
                    getCalendario(it,
                        it1,
                        false
                    )
                } }
            }
        }
    }

    private fun loadItemsAsync(addFromStart: Boolean) {
        loadingTask?.cancel(true)

        loadingTask = LoadingTask(addFromStart)
        loadingTask?.execute()
    }

}

