package mx.ctrlpg

import mx.ctrlpg.ui.login.LoginResult
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url


interface APIService {
    @POST
    fun Login(@Body request: RequestBody, @Url url: String): Call<LoginResult>

}