package mx.ctrlpg

import ResponseLogin
import ResponseSucursalCordones
import mx.ctrlpg.data.model.CalendarioRespuesta
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


interface APIService {
    @POST("account/login")
    @FormUrlEncoded
    fun login(@Field("userName") userName: String, @Field("pass") pass: String = "827ccb0eea8a706c4c34a16891f84e7b"): Call<ResponseLogin>
    //http://maximoag.com:8080/serviciosCtrlPg/servicio/mobile/calendarioactividadMobile/allbytecnicofechs?
    // cacTecnico=TC081&usuario=TC081&autorizacion=1f32aa4c9a1d2ea010adcf2348166a04&fechIni=2020/02/25&fechFin=2020/03/02
    @GET("mobile/calendarioactividadMobile/allbytecnicofechs")
    fun getCalendario(@Query("cacTecnico") catTecnico: String?,
                      @Query("usuario") usuario: String?,
                      @Query("autorizacion") autorizacion: String?,
                      @Query("fechIni") fechIni: String? = "2020/04/01",
                      @Query("fechFin") fechFin: String?= "2020/04/30"): Call<CalendarioRespuesta>

    @GET("mobile/sucursal/all")
    fun getSucursalCordones(
        @Query("usuario") usuario: String?,
        @Query("autorizacion") autorizacion: String?,
        @Query("sucCliId") sucCliId: Long? = 26)
            : Call<ResponseSucursalCordones>
}

object ApiUtils {

    private const val BASE_URL = "http://maximoag.com:8080/serviciosCtrlPg/servicio/"

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