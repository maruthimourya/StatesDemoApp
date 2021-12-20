package statesdemoapp.network

import retrofit2.Call
import retrofit2.http.GET

interface StatesListService {
    @GET("states")
    fun getStates(): Call<MutableList<States>>
}