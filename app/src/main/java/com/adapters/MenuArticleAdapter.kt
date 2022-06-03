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
import com.example.healthychild.databinding.ItemMenuArticleBinding
import com.like.LikeButton
import com.like.OnLikeListener
import com.room.database.HealthyChildDatabase
import com.room.entity.Article
import com.room.entity.Categories

class MenuArticleAdapter : ListAdapter<Article, MenuArticleAdapter.ViewHolder>(MyDiffUtil()) {
    lateinit var itemClick: OnItemClickListener
    lateinit var favouriteClick: OnFavouriteClickListener

    fun interface OnItemClickListener {
        fun onClick(articleId: Int)
    }

    fun interface OnFavouriteClickListener {
        fun onClick(favourite: Int, article: Article)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClick = listener
    }

    fun setOnFavouriteClickListener(listener: OnFavouriteClickListener) {
        favouriteClick = listener
    }

    inner class ViewHolder(var binding: ItemMenuArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(article: Article) {
            val context = App.instance
            binding.articleTitle.text = article.title
            binding.bookmark.isLiked = article.favourite == 1
            val image = context.resources.getIdentifier(article.image,
                "drawable",
                context.packageName)
            binding.articleImg.setImageResource(image)
            binding.cardView.setOnClickListener {
                itemClick.onClick(article.id!!)
            }
            binding.root.animation = AnimationUtils.loadAnimation(context, R.anim.item_anim)
            binding.bookmark.setOnLikeListener(object : OnLikeListener {
                override fun liked(likeButton: LikeButton) {
                    favouriteClick.onClick(1, article)
                }

                override fun unLiked(likeButton: LikeButton) {
                    favouriteClick.onClick(0, article)
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMenuArticleBinding.inflate(LayoutInflater.from(parent.context),
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