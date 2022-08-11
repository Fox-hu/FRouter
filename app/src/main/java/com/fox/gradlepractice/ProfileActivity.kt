package com.fox.gradlepractice

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fox.router.annotations.Destination

@Destination(
    url = "router://fox/profile",
    description = "个人信息"
)
class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val textView = TextView(this)
        textView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        textView.setBackgroundColor(Color.WHITE)
        textView.setTextColor(Color.BLACK)
        textView.textSize = 16f
        textView.gravity = Gravity.CENTER

        setContentView(textView)

        val name = intent.getStringExtra("name")
        val message = intent.getStringExtra("message")

        textView.text = "Profile -> $name : $message"
    }
}