package com.madeean.mathgame

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterHighScore(var nama: ArrayList<ModelIsiData>,
                       var context: Context
) : RecyclerView.Adapter<AdapterHighScore.AdapterHighScoreViewHolder>() {

    class AdapterHighScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name:TextView = itemView.findViewById(R.id.tv_name)
        var score:TextView = itemView.findViewById(R.id.tv_score)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHighScoreViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_data_highscore,parent,false)

        return AdapterHighScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterHighScoreViewHolder, position: Int) {
        holder.name.text = nama[position].name
        holder.score.text = nama[position].score.toString()
    }

    override fun getItemCount(): Int {
        if(nama.size > 0){
            return nama.size
        }else{
            return 0
        }
    }


}