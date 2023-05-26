package com.example.myrunjwl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.PrintStream

class ChallegeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challege)
        // select -> check by dialog -> change color
        // write txt now challenge at first line
        // txt will written all challenges -> have to read and show
        val output = PrintStream(openFileOutput("challenge.txt", MODE_PRIVATE))
        //한 번 읽으면 단어, 한번 더 읽으면 뜻
        output.println("0")
        output.close()
    }
}