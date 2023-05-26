package com.example.myrunjwl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myrunjwl.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        initLayout()
    }

    private fun initLayout() {
        binding.apply {
            var scan = Scanner(openFileInput("challenge.txt"))
            val km = scan.nextLine()
            btnChall.text = km
            scan = Scanner(openFileInput("setting.txt"))
            var sets = ""
            while (scan.hasNextLine()){
                sets += scan.nextLine() + "\n"
            }
            btnSet.text = sets
            btnChall.setOnClickListener {
                startActivity(Intent(this@MainActivity, ChallegeActivity::class.java))
            }
            btnSet.setOnClickListener {
                startActivity(Intent(this@MainActivity, SettingActivity::class.java))
            }
        }
    }
}