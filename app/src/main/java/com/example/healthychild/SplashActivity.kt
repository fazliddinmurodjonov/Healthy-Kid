package com.example.healthychild

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.healthychild.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity(R.layout.activity_splash) {
    private val binding: ActivitySplashBinding by viewBinding()
    var healthyChild = arrayOf("S", "o", "g‘", "l", "o", "m", " ", "b", "o", "l", "a")
    var index = 0

    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handler = Handler(Looper.getMainLooper())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2200)
        animationText()
    }

    private fun animationText() {
        with(binding) {
            handler.postDelayed({
                first.setTextColor(Color.WHITE)
            }, 300)
            handler.postDelayed({
                second.setTextColor(Color.WHITE)
            }, 400)
            handler.postDelayed({
                third.setTextColor(Color.WHITE)
            }, 500)
            handler.postDelayed({
                forth.setTextColor(Color.WHITE)
            }, 600)
            handler.postDelayed({
                fifth.setTextColor(Color.WHITE)
            }, 700)
            handler.postDelayed({
                sixth.setTextColor(Color.WHITE)
            }, 800)

            handler.postDelayed({
                seventh.setTextColor(Color.WHITE)
            }, 900)
            handler.postDelayed({
                eighth.setTextColor(Color.WHITE)
            }, 1000)
            handler.postDelayed({
                ninth.setTextColor(Color.WHITE)
            }, 1100)
            handler.postDelayed({
                tenth.setTextColor(Color.WHITE)
            }, 1200)
        }
    }
}