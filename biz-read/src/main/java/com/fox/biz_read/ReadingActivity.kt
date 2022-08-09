package com.fox.biz_read

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fox.router.annotations.Destination

@Destination(
    url = "router://reading",
    description = "阅读页"
)
class ReadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}