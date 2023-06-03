package com.example.myrunjwl

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
            var scan:Scanner
            var km:String
            try {
                scan = Scanner(openFileInput("challenge.txt"))
                km = scan.nextLine()
            } catch (e : Exception) {
                km = "0"
            }
            btnChall.text = km
            var sets = ""
            try {
                scan = Scanner(openFileInput("setting.txt"))
                while (scan.hasNextLine()) {
                    sets += scan.nextLine() + "\n"
                }
            } catch (e : Exception) {
                sets = "0\n0\n0\n0\n"
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