package com.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.adapters.CategoryAdapter
import com.adapters.HomeArticleAdapter
import com.example.App
import com.example.healthychild.R
import com.example.healthychild.databinding.HealthBinding
import com.example.healthychild.databinding.HomeBinding
import com.room.database.HealthyChildDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class Health : Fragment(R.layout.health) {
    var progress = 0
    lateinit var handler: Handler

    private val binding: HealthBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        handler = Handler(Looper.getMainLooper())
        binding.cardView.setOnClickListener {
            findNavController().navigate(R.id.bmi)
        }
        binding.cardView.animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.item_anim)
    }

}