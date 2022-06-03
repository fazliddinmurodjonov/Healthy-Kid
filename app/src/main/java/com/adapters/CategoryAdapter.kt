package com.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.App
import com.example.healthychild.R
import com.example.healthychild.databinding.ItemCategoryBinding
import com.room.entity.Article
import com.room.entity.Categories

class CategoryAdapter : ListAdapter<Categories, CategoryAdapter.ViewHolder>(
    MyDiffUtil()) {
    lateinit var itemClick: OnItemClickListener

    fun interface OnItemClickListener {
        fun onClick(categoryId: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClick = listener
    }


    inner class ViewHolder(var binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(category: Categories) {
            val context = App.instance
            binding.categoryName.text = category.categoryNameUzb
            val image = context.resources.getIdentifier(category.categoryImg,
                "drawable",
                context.packageName)
            binding.root.animation = AnimationUtils.loadAnimation(context, R.anim.item_anim)
            binding.categoryImg.setImageResource(image)
            binding.cardView.setOnClickListener {
                itemClick.onClick(category.id!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class MyDiffUtil : DiffUtil.ItemCallback<Categories>() {
        override fun areItemsTheSame(oldItem: Categories, newItem: Categories): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Categories, newItem: Categories): Boolean {
            return oldItem == newItem
        }
    }


}