package com.example.healthychild

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.healthychild.databinding.ActivityMainBinding
import com.like.LikeButton
import com.like.OnLikeListener


class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding: ActivityMainBinding by viewBinding()
    lateinit var appBarConfiguration: AppBarConfiguration

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding)
        {
            binding.btnNav.itemIconTintList = null
            loadUI()
            setSupportActionBar(toolbar)
            toolbar.elevation = 0F
            val navController = findNavController(R.id.nav_host_fragment)
            appBarConfiguration = AppBarConfiguration(setOf(R.id.home,
                R.id.health,
                R.id.menu,
                R.id.bookmark,
                R.id.article))
            setupActionBarWithNavController(navController, appBarConfiguration)
            btnNav.setupWithNavController(navController)
            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                btnNav.isVisible = destination.id !in destinationWithoutBottomNav
            }
        }


    }

    private val destinationWithoutBottomNav = listOf(R.id.article, R.id.bmi, R.id.bmi_result)
    private fun loadUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

}



