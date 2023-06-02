package com.example.myrunjwl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myrunjwl.databinding.ActivityChallegeBinding
import com.example.myrunjwl.databinding.ActivitySettingBinding
import java.io.PrintStream
import java.util.*

class SettingActivity : AppCompatActivity() {
    lateinit var binding: ActivitySettingBinding
    var datas:ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // txt
        // voice : 0 | 1
        // ap : 0 | 5 | 10 | 30
        // cd : 0 | 3 | 5 | 10
        // broadcast : rotate
        initButton()
        initLayout()
    }

    private fun initButton() {
        val scan = try {
            Scanner(openFileInput("setting.txt"))
        }catch (e : Exception) {
            Scanner(resources.openRawResource(R.raw.setting))
        }
        while (scan.hasNextLine()) {
            datas.add(scan.nextLine())
        }
        binding.btnVoice.text = datas[0]
        binding.btnAutoPause.text = datas[1]
        binding.btnCountDown.text = datas[2]
}

    private fun initLayout() {
        binding.apply {
            btnVoice.setOnClickListener {
                datas[0] = when (datas[0]) {
                    "0" -> "1"
                    else -> "0"
                }
                btnVoice.text = datas[0]
            }
            btnAutoPause.setOnClickListener {
                datas[1] = when (datas[1]) {
                    "0" -> "5"
                    "5" -> "10"
                    "10" -> "30"
                    else -> "0"
                }
                btnAutoPause.text = datas[1]
            }
            btnCountDown.setOnClickListener {
                datas[2] = when (datas[2]) {
                    "0" -> "5"
                    "3" -> "5"
                    "5" -> "10"
                    else -> "0"
                }
                btnCountDown.text = datas[2]
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val output = PrintStream(openFileOutput("setting.txt", MODE_PRIVATE))
        for (data in datas) {
            output.println(data)
        }
        output.close()
    }
}