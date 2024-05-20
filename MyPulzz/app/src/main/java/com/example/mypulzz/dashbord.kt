package com.example.mypulzz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class dashbord : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashbord)

        val newgames=findViewById<Button>(R.id.newgames)

        newgames.setOnClickListener {
            val explicitIntent= Intent(this,MainActivity::class.java)
            startActivity(explicitIntent)
        }
    }

}