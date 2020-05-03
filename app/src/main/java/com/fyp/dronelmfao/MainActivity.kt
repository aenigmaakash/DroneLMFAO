package com.fyp.dronelmfao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        videoAct.setOnClickListener {
            startActivity(Intent(applicationContext, VideoActivity::class.java))
        }
        infoAct.setOnClickListener {
            startActivity(Intent(applicationContext, InfoActivity::class.java))
        }
    }
}
