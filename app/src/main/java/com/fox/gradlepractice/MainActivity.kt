package com.fox.gradlepractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fox.router.annotations.Destination

//@Destination(url = "",description = "")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}