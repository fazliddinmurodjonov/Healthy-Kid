package com.fragments

import android.os.Bundle
import android.view.*
import android.view.Menu
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.healthychild.R
import com.example.healthychild.databinding.BmiBinding
import com.utils.Empty
import java.lang.Math.pow
import kotlin.math.pow


class Bmi : Fragment(R.layout.bmi) {
    private val binding: BmiBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        loadActionBar()

        binding.calculateBtn.setOnClickListener {
            val weight = binding.weightEt.text.toString()
            val height = binding.heightEt.text.toString()
            val weightEmpty = Empty.empty(weight)
            val heightEmpty = Empty.empty(height)
            val weightSpace = Empty.space(weight)
            val heightSpace = Empty.space(height)
            if (weightEmpty && weightSpace && heightEmpty && heightSpace) {
                val bmi: Double = weight.toDouble() / (height.toDouble() / 100).pow(2.0);
                val bundle = bundleOf("bmi" to bmi)
                findNavController().navigate(R.id.bmi_result, bundle)
            }
        }

    }

    private fun loadActionBar() {
        (activity as AppCompatActivity).supportActionBar!!.setHomeAsUpIndicator(R.drawable.back)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().popBackStack()
        return super.onOptionsItemSelected(item)
    }

}