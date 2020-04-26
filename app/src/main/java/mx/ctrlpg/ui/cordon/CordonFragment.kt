package mx.ctrlpg.ui.event

import ResponseSucursalCordones
import mx.ctrlpg.data.model.SucursalCordon
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_event.*
import mx.ctrlpg.ApiUtils
import mx.ctrlpg.R
import mx.ctrlpg.ui.BasicAdapter
import mx.ctrlpg.util.AlertUtil
import mx.ctrlpg.util.PreferenceHelper
import mx.ctrlpg.util.VariableConstants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CordonFragment : Fragment() {

    private lateinit var viewManager: LinearLayoutManager
    private var sucursal: Long? = null
    private lateinit var sucursalCordon: SucursalCordon
    private lateinit var cordonViewModel: CordonViewModel
    private lateinit var recyclerView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cordonViewModel =
            ViewModelProviders.of(this).get(CordonViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_event, container, false)
        sucursalCordon = arguments?.getSerializable("cordon") as SucursalCordon
        val sucursalCordonId = sucursalCordon.sucCorPri


        viewManager = LinearLayoutManager(context)
        recyclerView = root.findViewById(R.id.recycler_cordones)
        //getSucursalCorodones()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        text_event_title.text = sucursalCordon.sucDescripcion
        text_event_description.text = sucursalCordon.sucDireccion


    }



    private fun getSucursalCorodones(){

        val call = ApiUtils.apiService.getSucursalCordones(
            PreferenceHelper.read(VariableConstants.USUARIOSESION,""),
            PreferenceHelper.read(VariableConstants.AUTHORIZATION,""),
            sucursal
        )

        call.enqueue(object : Callback<ResponseSucursalCordones> {
            @SuppressLint("CommitPrefEdits")
            val alertUtil = AlertUtil(this@CordonFragment.activity as AppCompatActivity)
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
}