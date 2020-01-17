package mx.ctrlpg.RetrofitUtil;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRetrofit {

    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://solprodes.com.mx:8080/serviciosCtrlPg/servicio/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
