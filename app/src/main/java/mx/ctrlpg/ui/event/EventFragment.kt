package mx.ctrlpg.ui.event

import ResponseBitacora
import ResponseSucursalCordones
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_event.*
import mx.ctrlpg.ApiUtils
import mx.ctrlpg.ApiUtils.apiService
import mx.ctrlpg.R
import mx.ctrlpg.data.model.Evento
import mx.ctrlpg.ui.BasicAdapter
import mx.ctrlpg.util.AlertUtil
import mx.ctrlpg.util.PreferenceHelper
import mx.ctrlpg.util.VariableConstants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class EventFragment : Fragment() {

    private lateinit var viewManager: LinearLayoutManager
    private var sucursal: Long? = null
    private lateinit var evento: Evento
    private lateinit var eventViewModel: CordonViewModel
    private lateinit var recyclerView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        eventViewModel =
            ViewModelProviders.of(this).get(CordonViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_event, container, false)
        evento = arguments?.getSerializable("evento") as Evento
        sucursal = evento.cacSucId


        viewManager = LinearLayoutManager(context)
        recyclerView = root.findViewById(R.id.recycler_cordones)
        getSucursalCorodones()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        text_event_title.text = evento.cacTitle
        text_event_description.text = evento.cacIndicaciones
        evento.cacId

        bt_nueva_bitacira.setOnClickListener{ bitacoraNuevo() }
    }



    private fun getSucursalCorodones(){

        val call = ApiUtils.apiService.getSucursalCordones(
            PreferenceHelper.read(VariableConstants.USUARIOSESION,""),
            PreferenceHelper.read(VariableConstants.AUTHORIZATION,""),
            sucursal
        )

        call.enqueue(object : Callback<ResponseSucursalCordones> {
            @SuppressLint("CommitPrefEdits")
            val alertUtil = AlertUtil(this@EventFragment.activity as AppCompatActivity)
            override fun onResponse(call: Call<ResponseSucursalCordones>, response: Response<ResponseSucursalCordones>) {

                val responseSucursalCordones = response.body()
                if (responseSucursalCordones!=null) {
                    if(!responseSucursalCordones.error) {
                        setListToView(responseSucursalCordones)
                    }else {
                        alertUtil.showFailed(responseSucursalCordones.toString())
                    }
                } else {
                    alertUtil.showFailed("Error de conexión")
                }
            }
            override fun onFailure(call: Call<ResponseSucursalCordones>, t: Throwable) {
                alertUtil.showFailed("Error de conexión")
            }
        })
    }

    private fun setListToView(responseSucursalCordones: ResponseSucursalCordones) {
        val viewAdapter = BasicAdapter(responseSucursalCordones.sucursalCordon)
        recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

    }

    private fun bitacoraNuevo(){
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val currentDate = sdf.format(Date())
        val call = apiService.bitacoraNuevo(
            bisId =getRandomString(13),
            bisStatus = "1",
            bisCacId = evento.cacId,
            bisCheckIn = currentDate,
            bisUsuarioBit= PreferenceHelper.read(VariableConstants.USUARIOSESION,""),
            bisObservaciones="prueba desde app",
            bisLatitud="17.9868908",
            bisLongitud= "-92.9302826",
            usuario= PreferenceHelper.read(VariableConstants.USUARIOSESION,""),
            autorizacion = PreferenceHelper.read(VariableConstants.AUTHORIZATION,"")
        )

        call.enqueue(object : Callback<ResponseBitacora> {
            @SuppressLint("CommitPrefEdits")
            override fun onResponse(call: Call<ResponseBitacora>, response: Response<ResponseBitacora>) {
                val bitacoraResponse = response.body()

                if (!bitacoraResponse?.error!!) {
                    Toast.makeText(this@EventFragment.context,bitacoraResponse.bitacora.toString(),Toast.LENGTH_LONG).show()



                } else {
                    Toast.makeText(
                        this@EventFragment.context,
                        bitacoraResponse.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseBitacora>, t: Throwable) {
                Toast.makeText(
                    this@EventFragment.context,
                    t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }


     fun getRandomString(length: Int) : String {
            val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
            return (1..length)
                .map { allowedChars.random() }
                .joinToString("")
        }

}