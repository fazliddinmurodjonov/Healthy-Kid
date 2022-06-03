package com.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.App
import com.example.healthychild.R
import com.example.healthychild.databinding.ItemFavouriteArticleBinding
import com.like.LikeButton
import com.like.OnLikeListener
import com.room.dao.ArticleDao
import com.room.database.HealthyChildDatabase
import com.room.entity.Article
import com.room.entity.ArticleFavourite

class FavouriteArticleAdapter :
    ListAdapter<ArticleFavourite, FavouriteArticleAdapter.ViewHolder>(MyDiffUtil()) {
    lateinit var itemClick: OnItemClickListener
    lateinit var categoryClick: OnCategoryClickListener
    lateinit var favouriteClick: OnFavouriteClickListener
    var db = HealthyChildDatabase.getInstance(App.instance)

    fun interface OnItemClickListener {
        fun onClick(articleId: Int)
    }

    fun interface OnCategoryClickListener {
        fun onClick(categoryId: Int)
    }

    fun interface OnFavouriteClickListener {
        fun onClick(articleId: Int, position: Int)
    }

    fun setOnFavouriteClickListener(listener: OnFavouriteClickListener) {
        favouriteClick = listener
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClick = listener
    }

    fun setOnCategoryClickListener(listener: OnCategoryClickListener) {
        categoryClick = listener
    }

    inner class ViewHolder(var binding: ItemFavouriteArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(article: ArticleFavourite, position: Int) {
            val context = App.instance
            binding.articleTitle.text = article.title
            val category = db.categoryDao().getCategoryById(article.categoryId!!)
            binding.articleCategoryBtn.text = category.categoryNameUzb
            val image = context.resources.getIdentifier(article.image,
                "drawable",
                context.packageName)
            binding.root.animation = AnimationUtils.loadAnimation(context, R.anim.item_anim)
            binding.articleImg.setImageResource(image)
            binding.root.setOnClickListener {
                itemClick.onClick(article.id!!)
            }
            binding.bookmark.setOnClickListener {
                favouriteClick.onClick(article.id!!, position)
            }
            binding.articleCategoryBtn.setOnClickListener {
                categoryClick.onClick(article.categoryId!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemFavouriteArticleBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position), position)
    }

    class MyDiffUtil : DiffUtil.ItemCallback<ArticleFavourite>() {
        override fun areItemsTheSame(oldItem: ArticleFavourite, newItem: ArticleFavourite): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ArticleFavourite, newItem: ArticleFavourite): Boolean {
            return oldItem == newItem
        }

    }
}