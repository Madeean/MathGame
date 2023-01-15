package com.madeean.mathgame


import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiRequest {
    @GET("ranking")
    fun getRanking(): Call<ModelGetRanking>

    @FormUrlEncoded
    @POST("ranking")
    fun postRanking(
        @Field("name") name: String,
        @Field("score") score: Int
    ): Call<ModelIsiData>
}