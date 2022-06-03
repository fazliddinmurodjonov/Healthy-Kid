package com.example.healthychild

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.App
import com.example.healthychild.R.id.home
import com.example.healthychild.databinding.ActivityMainBinding
import com.room.database.HealthyChildDatabase
import com.utils.LoadDataFromFireStore
import com.utils.NetworkHelper

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private var onBack = false
    var db = HealthyChildDatabase.getInstance(App.instance)
    private val binding: ActivityMainBinding by viewBinding()
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController
    var networkHelper = NetworkHelper(App.instance)
    private lateinit var handler: Handler
    var connect = true

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding)
        {
            handler = Handler(Looper.getMainLooper())
            if (db.articleDao().getAllArticles().isEmpty()) {
                progressBar.isVisible = true
                handler.postDelayed(runnable, 0)
            }
            val window = window
            window.statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.white)
            binding.btnNav.itemIconTintList = null
            loadUI()
            setSupportActionBar(toolbar)
            navController = findNavController(R.id.nav_host_fragment)
            appBarConfiguration = AppBarConfiguration(setOf(home,
                R.id.menu,
                R.id.health,
                R.id.bookmark,
                R.id.article,
                R.id.articleOfCategory,
                R.id.bmi,
                R.id.bmi_result
            ))
            setupActionBarWithNavController(navController, appBarConfiguration)
            btnNav.setupWithNavController(navController)
            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                btnNav.isVisible = destination.id !in destinationWithoutBottomNav
            }

        }

    }

    private val destinationWithoutBottomNav =
        listOf(R.id.articleOfCategory, R.id.article, R.id.bmi, R.id.bmi_result)

    private fun loadUI() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun onBackPressed() {
        val currentDestination = navController.currentDestination!!.id == home
        if (onBack) {
            super.onBackPressed()
            return
        }
        if (currentDestination) {
            Handler(Looper.getMainLooper()).postDelayed({ onBack = false }, 2000)
            onBack = true
            Toast.makeText(this,
                "Ilovadan chiqish uchun yana bir marta bosing",
                Toast.LENGTH_SHORT)
                .show()
        } else {
            super.onBackPressed()
            return
        }

    }

    private var runnable = object : Runnable {
        override fun run() {
            if (networkHelper.isNetworkConnected() && connect) {
                LoadDataFromFireStore.loadData(App.instance)
                connect = false
            }
            if (db.articleDao().getAllArticles().isNotEmpty()) {
                binding.progressBar.isVisible = false
                handler.removeCallbacks(this)
            }
            handler.postDelayed(this, 1000)
        }

    }

}



