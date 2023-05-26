package com.example.myrunjwl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myrunjwl.databinding.ActivityChallegeBinding
import java.io.PrintStream
import java.util.*

class ChallegeActivity : AppCompatActivity() {
    lateinit var binding: ActivityChallegeBinding
    lateinit var adapter: ChallengeDataAdapter
    var datas:ArrayList<ChallengeData> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChallegeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // select -> check by dialog -> change color
        // write txt now challenge at first line
        // txt will written all challenges -> have to read and show
        initData()
        initLayout()
    }

    private fun initData() {
        val scan = Scanner(resources.openRawResource(R.raw.challenges))
        while (scan.hasNextLine()){
            val km = scan.next()
            val select = scan.next()
            datas.add(ChallengeData(km, select.toInt()))
        }
    }

    private fun initLayout() {
        binding.apply {
            challengeList.layoutManager = LinearLayoutManager(this@ChallegeActivity, LinearLayoutManager.VERTICAL, false)
            adapter = ChallengeDataAdapter(datas)
            adapter.itemClickListener = object:ChallengeDataAdapter.OnItemClickListener{
                override fun onItemClick(data: ChallengeData, adapterPosition: Int) {
                    data.select = when (data.select) {
                        0 -> {
                            val output = PrintStream(openFileOutput("challenge.txt", MODE_PRIVATE))
                            output.println(data.km)
                            output.close()
                            // challenges.txt 에도 적용시키자.
                            // 한 개만 활성화 되게 바꾸자.
                            1
                        }
                        else -> 0
                    }
                    adapter.notifyItemChanged(adapterPosition)
                }
            }
            challengeList.adapter = adapter
        }
    }
}