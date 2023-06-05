package com.example.myrunjwl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
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

    fun checkAlertDialog(adapter: ChallengeDataAdapter, data: ChallengeData, adapterPosition: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("한 달에 ${data.km}km 챌린지를 새로 시작하겠습니까?")
            .setPositiveButton("시작") {
                    _, _ -> changeData(adapter, data, adapterPosition)
            }.setNegativeButton("창 닫기") {
                    dlg, _ -> dlg.dismiss()
            }
        val dlg = builder.create()
        dlg.show()
    }

    fun cancelAlertDialog(adapter: ChallengeDataAdapter, data: ChallengeData, adapterPosition: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("한 달에 ${data.km}km 챌린지를 종료하겠습니까?")
            .setPositiveButton("종료") {
                    _, _ -> changeData(adapter, data, adapterPosition)
            }.setNegativeButton("창 닫기") {
                    dlg, _ -> dlg.dismiss()
            }
        val dlg = builder.create()
        dlg.show()
    }

    private fun changeData(adapter: ChallengeDataAdapter, data: ChallengeData, adapterPosition: Int) {
        data.select = when (data.select) {
            0 -> 1
            else -> 0
        }
        // 챌린지 내에서 쓸 데이터 조작
        val output = PrintStream(openFileOutput("challenge_list.txt", MODE_PRIVATE))
        for (i in datas.indices) {
            if (i != adapterPosition) { // 다른 챌린지가 켜져있다면, 꺼버리기
                datas[i] = ChallengeData(datas[i].km, 0)
                output.println("${datas[i].km} 0")
            }
            else
                output.println("${datas[i].km} ${data.select}") // 선택된 챌린지 변경 적용
        }
        output.close()
        adapter.notifyDataSetChanged()
    }

    private fun initLayout() {
        binding.apply {
            challengeList.layoutManager = LinearLayoutManager(this@ChallegeActivity, LinearLayoutManager.VERTICAL, false)
            adapter = ChallengeDataAdapter(datas)
            adapter.itemClickListener = object:ChallengeDataAdapter.OnItemClickListener{
                override fun onItemClick(data: ChallengeData, adapterPosition: Int) {
                    if (data.select == 1)
                        cancelAlertDialog(adapter, data, adapterPosition)
                    else
                        checkAlertDialog(adapter, data, adapterPosition)
                }
            }
            challengeList.adapter = adapter
        }
    }

    override fun onPause() {
        super.onPause()
        // 선택된 챌린지 전달
        val output = PrintStream(openFileOutput("challenge.txt", MODE_PRIVATE))
        var selectSomething = false
        for (data in datas) {
            if (data.select == 1) {
                selectSomething = true
                output.println(data.km)
            }
        }
        if (!selectSomething)
            output.println("0")
        output.close()
    }
}