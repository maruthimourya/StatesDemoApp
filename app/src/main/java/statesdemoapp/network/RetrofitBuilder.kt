package statesdemoapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = "https://private-786ccb-states22.apiary-mock.com"

    private val retrofit: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val statesApiService: StatesListService by lazy {
        retrofit
            .build()
            .create(StatesListService::class.java)
    }

}