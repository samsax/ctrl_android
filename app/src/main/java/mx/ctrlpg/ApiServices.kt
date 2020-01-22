package mx.ctrlpg

import ResponseLogin
import mx.ctrlpg.data.LoginDataSource
import mx.ctrlpg.data.model.LoginUser
import mx.ctrlpg.ui.login.LoginResult
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


interface APIService {
    @POST("account/login")
    @FormUrlEncoded
    fun login(@Field("userName") userName: String, @Field("pass") pass: String): Call<ResponseLogin>

}

object ApiUtils {

    val BASE_URL = "http://solprodes.com.mx:8080/serviciosCtrlPg/servicio/"

    val apiService: APIService
        get() = BaseRetrofit.getClient(BASE_URL)!!.create(APIService::class.java)

}

object BaseRetrofit {

    var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit? {
        if (retrofit == null) {
            //TODO While release in Google Play Change the Level to NONE
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build()

            retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit

    }
}