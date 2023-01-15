package com.madeean.mathgame

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    lateinit var textScore: TextView
    lateinit var textLife: TextView
    lateinit var textTime:TextView
    lateinit var textViewCheck : TextView

    lateinit var textQuestion:TextView
    lateinit var editTextAnswer:EditText

    lateinit var buttonOk: Button
    lateinit var buttonNext: Button

    var correctAnswer = 0
    var userScore = 0
    var userLife = 3

    var timer: CountDownTimer? = null

    lateinit var dialog:Dialog

    lateinit var et_name:EditText
    lateinit var btn_save:Button
    lateinit var btn_cancel:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)


        textScore = findViewById(R.id.textViewScore)
        textLife = findViewById(R.id.textViewLife)
        textTime = findViewById(R.id.textViewTime)
        textViewCheck = findViewById(R.id.textViewCheck)

        textQuestion = findViewById(R.id.textViewQuestion)
        editTextAnswer = findViewById(R.id.editTextAnswer)

        buttonOk = findViewById(R.id.buttonOk)
        buttonNext = findViewById(R.id.buttonNext)

        gameContinue()

        textViewCheck.text=""


        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var waktu:String = (millisUntilFinished/1000).toString()
                textTime.setText(waktu+"s")

            }

            override fun onFinish() {
                gameEnd("timeout")
                textTime.setText("timeout")
            }
        }
        (timer as CountDownTimer).start()

        buttonOk.setOnClickListener {
            val input = editTextAnswer.text.toString()

            if(input.equals("")){
                Toast.makeText(this, "Please enter a number or press next button", Toast.LENGTH_SHORT).show()
            }
            else{
                val userAnswer = input.toInt()
                if (userAnswer == correctAnswer){
                    userScore += 10
                    textViewCheck.text = "Correct Answer"
                    textScore.text = userScore.toString()
                    gameContinue()
                }

                else{
                    userLife--
                    textViewCheck.text = "Wrong Answer"
                    textLife.text = userLife.toString()
                }


                if(userLife == 0){
                    gameEnd("game over")


                }

            }
        }

        buttonNext.setOnClickListener {
            gameContinue()
            editTextAnswer.setText("")
        }

        textLife.text = userLife.toString()
        textScore.text = userScore.toString()

        dialog = Dialog(this)






    }

    fun gameEnd(text:String){
        textQuestion.text = text

        buttonOk.text = "back to menu"
        buttonNext.text = "back to menu"

        timer?.cancel()

        buttonOk.setOnClickListener {
            val intent:Intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
        buttonNext.setOnClickListener {
            val intent:Intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        Handler().postDelayed({
            dialog.setContentView(R.layout.dialog)
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.setCancelable(false)
            et_name = dialog.findViewById(R.id.et_name)
            btn_save = dialog.findViewById(R.id.btn_save)
            btn_cancel = dialog.findViewById(R.id.btn_cancel)

            btn_save.setOnClickListener {
                val name = et_name.text.toString()
                if(name.equals("")){
                    Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                }
                else{

                    postRanking(name, userScore)


                }


            }

            btn_cancel.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }, 500)



    }

    private fun postRanking(name: String, userScore: Int) {
        val api:ApiRequest = Server.konekRetrofit()?.create(ApiRequest::class.java)!!
        val tampilData: Call<ModelIsiData> = api.postRanking(name, userScore)
        tampilData.enqueue(object : retrofit2.Callback<ModelIsiData> {
            override fun onResponse(
                call: Call<ModelIsiData>,
                response: retrofit2.Response<ModelIsiData>
            ){
                if (response.isSuccessful) {
                    Toast.makeText(this@GameActivity, "Your name : $name score is $userScore", Toast.LENGTH_SHORT).show()
                    val intent:Intent = Intent(this@GameActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or
                            Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
            }

            override fun onFailure(call: Call<ModelIsiData>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }


    fun gameContinue(){
        var number1:Int = Random.nextInt(0,100)
        var number2:Int = Random.nextInt(0,100)

        var operator = Random.nextInt(0,3)
        var operatorString = ""
        when(operator){
            0 -> {
                operatorString = "+"
                correctAnswer = number1 + number2
                textQuestion.text = "$number1 + $number2"
            }
            1 -> {
                operatorString = "-"
                correctAnswer = number1 - number2
                textQuestion.text = "$number1 - $number2"
            }
            2 -> {
                operatorString = "*"
                correctAnswer = number1 * number2
                textQuestion.text = "$number1 * $number2"
            }

        }

//        textQuestion.text = "$number1 + $number2"
//        correctAnswer = number1 + number2
    }
}