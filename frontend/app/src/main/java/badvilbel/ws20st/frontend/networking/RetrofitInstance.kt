package badvilbel.ws20st.frontend.networking

import badvilbel.ws20st.frontend.networking.LogbookApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val SERVER_URL = "http://192.168.2.103:5000"

object RetrofitInstance {
    val api: LogbookApi by lazy {
        Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LogbookApi::class.java)
    }
}