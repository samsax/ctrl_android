package mx.ctrlpg.data

import ResponseLogin
import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import mx.ctrlpg.APIService
import mx.ctrlpg.ApiUtils
import mx.ctrlpg.ApiUtils.apiService
import mx.ctrlpg.data.model.LoggedInUser
import mx.ctrlpg.data.model.LoginUser
import mx.ctrlpg.ui.login.LoggedInUserView
import mx.ctrlpg.ui.login.LoginResult
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    /*fun login(eMail: String, ePass: String) {

            try {
                val json = JSONObject()
                json.put("email", eMail)
                json.put("password", ePass)
                val requestBody: RequestBody =
                    RequestBody.create(MediaType.parse("application/json"), json.toString())
                    val call: Call<ResponseLogin> = apiService.login(requestBody)
                    call.enqueue(
                        object : Callback<ResponseLogin> {
                            @SuppressLint("CommitPrefEdits")
                            override fun onResponse(
                                call: Call<ResponseLogin>?,
                                response: Response<ResponseLogin>?
                            ) {
                                if (response!!.isSuccessful) {

                                    //startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                    //finish()
                                    //finishAndRemoveTask()
                                } else {

                                }
                            }

                            override fun onFailure(call: Call<ResponseLogin>?, t: Throwable?) {
                                Log.d("loginAuthendication", "failure--" + t.toString())


                            }
                        })

                }
             catch (e: Exception) {
                Log.d("loginAuthendication", e.toString())
            }
    }*/

    fun logout() {
        // TODO: revoke authentication
    }
}

