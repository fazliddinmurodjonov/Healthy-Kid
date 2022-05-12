package com.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.healthychild.databinding.HomeBinding
import com.utils.GroupsUtils

class Home : Fragment() {
    private val binding: HomeBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }
}