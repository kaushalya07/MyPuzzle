package com.example.mypulzz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.content.Intent

class launchscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launchscreen)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@launchscreen, dashbord::class.java))
        }, 2000)


    }
}