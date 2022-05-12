package com.example.healthychild

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.healthychild.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.utils.CategoryUtils
import com.utils.GroupsUtils

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding: ActivityMainBinding by viewBinding()

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnNav.itemIconTintList = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
      //  binding.articleText.text = GroupsUtils.pregnancy
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.my_nav_host).navigateUp()
    }
}



