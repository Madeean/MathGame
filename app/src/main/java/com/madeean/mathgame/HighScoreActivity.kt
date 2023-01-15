package com.madeean.mathgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call

class HighScoreActivity : AppCompatActivity() {
    lateinit var rv:RecyclerView
    var listData=ArrayList<ModelIsiData>()
    lateinit var adapter:AdapterHighScore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_score)

        rv = findViewById(R.id.rv_highscore)
        rv.layoutManager = LinearLayoutManager(this)
        listData.clear()
        getData()
//        for (i in 0..10){
//            listData.add("Data $i")
//        }
//        adapter = AdapterHighScore(listData, this)
//        rv.adapter = adapter
//        adapter.notifyDataSetChanged()


    }

    private fun getData() {
        val api:ApiRequest = Server.konekRetrofit()?.create(ApiRequest::class.java)!!
        val tampilData: Call<ModelGetRanking> = api.getRanking();

        tampilData.enqueue(object : retrofit2.Callback<ModelGetRanking> {
            override fun onResponse(
                call: Call<ModelGetRanking>,
                response: retrofit2.Response<ModelGetRanking>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    listData.clear()
                    listData.addAll(data!!)
                    adapter = AdapterHighScore(listData, this@HighScoreActivity)
                    rv.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ModelGetRanking>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}