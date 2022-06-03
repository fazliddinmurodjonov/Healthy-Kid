package com.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.healthychild.R
import com.example.healthychild.databinding.BmiResultBinding


class bmi_result : Fragment(R.layout.bmi_result) {
    private val binding: BmiResultBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bmi = arguments?.getDouble("bmi")
        with(binding)
        {
            (activity as AppCompatActivity).supportActionBar!!.hide()
            bmiValueTV.text = "%.1f".format(bmi)
            if (bmi!! < 18.5) {
                statusBarColor(R.color.yellow)
                binding.mainLayout.setBackgroundColor(ContextCompat.getColor(requireContext(),
                    R.color.yellow))
                bmiFlagImgView.setImageResource(R.drawable.exclamation_mark)
                bmiLabelTV.setText(R.string.under_weight)
                advice1TV.setText(R.string.under_first)
                advice2TV.setText(R.string.under_second)
                advice3TV.setText(R.string.under_third)
                advice1IMG.setImageResource(R.drawable.no_water)
                advice2IMG.setImageResource(R.drawable.big_meal)
                advice3IMG.setImageResource(R.drawable.sleep)
            } else if (18.5 <= bmi && bmi < 25) {
                statusBarColor(R.color.green)
                binding.mainLayout.setBackgroundColor(ContextCompat.getColor(requireContext(),
                    R.color.green))
                bmiFlagImgView.setImageResource(R.drawable.correct)
                bmiLabelTV.setText(R.string.normal_weight)
                advice1TV.setText(R.string.normal_first)
                advice2TV.setText(R.string.normal_second)
                advice3TV.setText(R.string.normal_third)
                advice1IMG.setImageResource(R.drawable.active)
                advice2IMG.setImageResource(R.drawable.salad)
                advice3IMG.setImageResource(R.drawable.sleep)
            } else
                if (bmi > 24.9) {
                    statusBarColor(R.color.red)
                    binding.mainLayout.setBackgroundColor(ContextCompat.getColor(requireContext(),
                        R.color.red))
                    bmiFlagImgView.setImageResource(R.drawable.warning)
                    bmiLabelTV.setText(R.string.over_weight)
                    advice1TV.setText(R.string.over_first)
                    advice2TV.setText(R.string.over_second)
                    advice3TV.setText(R.string.over_third)
                    advice1IMG.setImageResource(R.drawable.water)
                    advice2IMG.setImageResource(R.drawable.two_eggs)
                    advice3IMG.setImageResource(R.drawable.nosugar)
                }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).supportActionBar!!.show()
        statusBarColor(R.color.white)
    }

    private fun statusBarColor(color: Int) {
        val window = requireActivity().window
        window.statusBarColor = ContextCompat.getColor(requireActivity(), color)
    }
}