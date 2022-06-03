package com.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.App
import com.example.healthychild.R
import com.example.healthychild.databinding.ItemHomeArticleBinding
import com.like.LikeButton
import com.like.OnLikeListener
import com.room.database.HealthyChildDatabase
import com.room.entity.Article
import com.room.entity.Categories


class HomeArticleAdapter() :
    ListAdapter<Article, HomeArticleAdapter.ViewHolder>(MyDiffUtil()) {
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
        fun onClick(favourite: Int, article: Article)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClick = listener
    }

    fun setOnCategoryClickListener(listener: OnCategoryClickListener) {
        categoryClick = listener
    }

    fun setOnFavouriteClickListener(listener: OnFavouriteClickListener) {
        favouriteClick = listener
    }

    inner class ViewHolder(var binding: ItemHomeArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(article: Article) {
            val context = App.instance
            binding.articleTitle.text = article.title
            val category = db.categoryDao().getCategoryById(article.categoryId!!)
            binding.articleCategoryBtn.text = category.categoryNameUzb
            val image = context.resources.getIdentifier(article.image, "drawable",
                context.packageName)
            binding.root.animation = AnimationUtils.loadAnimation(context, R.anim.item_anim)
            binding.articleImg.setImageResource(image)
            binding.bookmark.isLiked = article.favourite == 1
            binding.cardView.setOnClickListener {
                itemClick.onClick(article.id!!)
            }
            binding.bookmark.setOnLikeListener(object : OnLikeListener {
                override fun liked(likeButton: LikeButton) {
                    favouriteClick.onClick(1, article)
                }

                override fun unLiked(likeButton: LikeButton) {
                    favouriteClick.onClick(0, article)
                }
            })
            binding.articleCategoryBtn.setOnClickListener {
                categoryClick.onClick(article.categoryId!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHomeArticleBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class MyDiffUtil : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

}