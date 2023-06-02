package com.example.myrunjwl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        val scan = try {
            Scanner(openFileInput("challenge_list.txt"))
        }catch (e : Exception) {
            Scanner(resources.openRawResource(R.raw.challenge_list))
        }
        while (scan.hasNext()) {
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
                override fun onItemClick(data: ChallengeData, items:ArrayList<ChallengeData>, adapterPosition: Int) {
                    // 선택된 챌린지 전달
                    var output = PrintStream(openFileOutput("challenge.txt", MODE_PRIVATE))
                    var selectKm = data.km
                    data.select = when (data.select) {
                        0 -> 1
                        else -> {
                            selectKm = "0"
                            0
                        }
                    }
                    output.println(selectKm)
                    output.close()
                    // 챌린지 내에서 쓸 데이터 조작
                    output = PrintStream(openFileOutput("challenge_list.txt", MODE_PRIVATE))
                    for (i in items.indices) {
                        if (i != adapterPosition) { // 다른 챌린지가 켜져있다면, 꺼버리기
                            items[i] = ChallengeData(items[i].km, 0)
                            output.println("${items[i].km} 0")
                        }
                        else
                            output.println("${items[i].km} ${data.select}") // 선택된 챌린지 변경 적용
                    }
                    output.close()
                    adapter.notifyDataSetChanged()
                }
            }
            challengeList.adapter = adapter
        }
    }
}