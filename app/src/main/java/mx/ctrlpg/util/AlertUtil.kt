package mx.ctrlpg.util

import androidx.appcompat.app.AppCompatActivity
import com.irozon.alertview.AlertActionStyle
import com.irozon.alertview.AlertStyle
import com.irozon.alertview.AlertView
import com.irozon.alertview.objects.AlertAction

class AlertUtil {

    var activity: AppCompatActivity
    constructor(activity: AppCompatActivity){
        this.activity = activity
    }


    fun showFailed(errorString: String, title: String = "Error") {
        val alert = AlertView(title,errorString, AlertStyle.BOTTOM_SHEET)
        alert.addAction(AlertAction("Aceptar", AlertActionStyle.DEFAULT){})
        alert.show(this.activity as AppCompatActivity)
    }

    fun showFailed(errorString: String, title: String = "Error", alertAction: AlertAction) {
        val alert = AlertView(title,errorString, AlertStyle.BOTTOM_SHEET)
        alert.addAction(alertAction)
        alert.show(this.activity as AppCompatActivity)
    }
}