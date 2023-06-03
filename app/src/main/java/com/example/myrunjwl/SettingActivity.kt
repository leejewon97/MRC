package com.example.myrunjwl

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Surface
import com.example.myrunjwl.databinding.ActivityChallegeBinding
import com.example.myrunjwl.databinding.ActivitySettingBinding
import java.io.PrintStream
import java.util.*

@SuppressLint("SetTextI18n")
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
        // voice male : 0 | 1
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
        binding.btnVoiceTxt.text = "음성 피드백\n" + when (datas[0]) {
            "0" -> "on"
            else -> "off"
        }
        binding.btnAutoPauseTxt.text = "러닝 자동 일시 정지\n" + when (datas[1]) {
            "0" -> "off"
            else -> datas[1] + "초"
        }
        binding.btnCountDownTxt.text = "카운트 다운\n" + when (datas[2]) {
            "0" -> "off"
            else -> datas[2] + "초"
        }
        binding.btnVoiceMaleTxt.text = "음성 피드백 성별\n" + when (datas[3]) {
            "0" -> "여성"
            else -> "남성"
        }
}

    private fun initLayout() {
        binding.apply {
            btnVoice.setOnClickListener { // 음성 피드백; off : 0, on : 1
                datas[0] = when (datas[0]) {
                    "0" -> "1"
                    else -> "0"
                }
                btnVoiceTxt.text = "음성 피드백\n" + when (datas[0]) {
                    "0" -> "on"
                    else -> "off"
                }
            }
            btnAutoPause.setOnClickListener { // 러닝 자동 일시정지; off : 0, on : 5 | 10 | 30 초
                datas[1] = when (datas[1]) {
                    "0" -> "5"
                    "5" -> "10"
                    "10" -> "30"
                    else -> "0"
                }
                btnAutoPauseTxt.text = "러닝 자동 일시 정지\n" + when (datas[1]) {
                    "0" -> "off"
                    else -> datas[1] + "초"
                }
            }
            btnCountDown.setOnClickListener { // 카운트 다운 변경; off : 0, on : 3 | 5 | 10 초
                datas[2] = when (datas[2]) {
                    "0" -> "5"
                    "3" -> "5"
                    "5" -> "10"
                    else -> "0"
                }
                btnCountDownTxt.text = "카운트 다운\n" + when (datas[2]) {
                    "0" -> "off"
                    else -> datas[2] + "초"
                }
            }
            btnVoiceMale.setOnClickListener { // 음성 피드백 성별 변경; 여성 : 0, 남성 : 1
                datas[3] = when (datas[3]) {
                    "0" -> "1"
                    else -> "0"
                }
                btnVoiceMaleTxt.text = "음성 피드백 성별\n" + when (datas[3]) {
                    "0" -> "여성"
                    else -> "남성"
                }
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