package com.example.myrunjwl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.PrintStream

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        // txt
        // voice : 0 | 1
        // ap : 0 | 5 | 10 | 30
        // cd : 0 | 3 | 5 | 10
        // broadcast : rotate
        val output = PrintStream(openFileOutput("setting.txt", MODE_PRIVATE))
        //한 번 읽으면 단어, 한번 더 읽으면 뜻
        output.println("0")
        output.println("0")
        output.println("0")
        output.close()
    }
}