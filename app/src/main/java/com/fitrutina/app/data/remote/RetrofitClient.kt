package com.fitrutina.app.data.remote

import com.fitrutina.app.data.remote.api.WgerApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Cliente singleton de Retrofit para conectarse a la API de wger.de
 */
object RetrofitClient {

    private const val BASE_URL = "https://wger.de/api/v2/"

    val apiService: WgerApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WgerApiService::class.java)
    }
}
