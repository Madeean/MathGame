package com.madeean.mathgame

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Server {

    private const val baseURL = "http://192.168.100.105:3000/"
    private var retro: Retrofit? = null
    fun konekRetrofit(): Retrofit? {
        if (retro == null) {
            retro = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retro
    }
}