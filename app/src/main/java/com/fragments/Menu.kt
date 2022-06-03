package com.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.adapters.CategoryAdapter
import com.example.App
import com.example.healthychild.R
import com.example.healthychild.databinding.MenuBinding
import com.room.database.HealthyChildDatabase
import com.utils.LoadDataFromFireStore
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class Menu : Fragment(R.layout.menu) {
    private val binding: MenuBinding by viewBinding()
    var db: HealthyChildDatabase = HealthyChildDatabase.getInstance(App.instance)
    var categoryList = db.categoryDao().getAllCategories()
    private val categoryAdapter: CategoryAdapter = CategoryAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        categoryAdapter.submitList(categoryList)
        binding.menuCategoryRV.adapter = categoryAdapter
        adapterClick()
    }

    private fun adapterClick() {
        categoryAdapter.setOnItemClickListener { categoryId ->
            val bundle = bundleOf("categoryId" to categoryId)
            findNavController().navigate(R.id.articleOfCategory, bundle)
        }
    }

}