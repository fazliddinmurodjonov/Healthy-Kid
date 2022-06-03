package com.example.healthychild

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.healthychild.databinding.ActivitySplashBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.room.database.HealthyChildDatabase
import com.room.entity.Article
import com.utils.LoadDataFromFireStore

class SplashActivity : AppCompatActivity(R.layout.activity_splash) {
    private val binding: ActivitySplashBinding by viewBinding()
    var healthyChild = arrayOf("S", "o", "g‘", "l", "o", "m", " ", "b", "o", "l", "a")
    lateinit var fireStore: FirebaseFirestore
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
        }, 2000)
        animationText()
    }


    private fun animationText() {
        with(binding) {
            handler.postDelayed({
                first.setTextColor(Color.WHITE)
            }, 350)
            handler.postDelayed({
                second.setTextColor(Color.WHITE)
            }, 450)
            handler.postDelayed({
                third.setTextColor(Color.WHITE)
            }, 550)
            handler.postDelayed({
                forth.setTextColor(Color.WHITE)
            }, 650)
            handler.postDelayed({
                fifth.setTextColor(Color.WHITE)
            }, 750)
            handler.postDelayed({
                sixth.setTextColor(Color.WHITE)
            }, 850)

            handler.postDelayed({
                seventh.setTextColor(Color.WHITE)
            }, 950)
            handler.postDelayed({
                eighth.setTextColor(Color.WHITE)
            }, 1050)
            handler.postDelayed({
                ninth.setTextColor(Color.WHITE)
            }, 1150)
            handler.postDelayed({
                tenth.setTextColor(Color.WHITE)
            }, 1250)
        }
    }
}