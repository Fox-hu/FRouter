package com.fox.gradlepractice

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.fox.router.annotations.Destination
import com.fox.router.runtime.Router.go


@Destination(
    url = "router://page-home",
    description = "应用主页"
)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.btn).setOnClickListener {
            go(it.context, "router://fox/profile?name=hello&message=world"
            )
        }
    }
}