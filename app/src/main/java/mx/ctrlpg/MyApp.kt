package mx.ctrlpg

import android.app.Application
import mx.ctrlpg.util.PreferenceHelper

class MyApp : Application() {


    override fun onCreate() {
        super.onCreate()
        PreferenceHelper.init(this)
    }

}