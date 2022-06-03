package com.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.adapters.FavouriteArticleAdapter
import com.adapters.HomeArticleAdapter
import com.example.App
import com.example.healthychild.R
import com.example.healthychild.databinding.BookmarkBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.room.database.HealthyChildDatabase
import com.room.entity.Article
import com.room.entity.ArticleFavourite
import com.squareup.picasso.Picasso
import com.utils.ArticleFavouriteManage
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers


class Bookmark : Fragment(R.layout.bookmark) {
    private val binding: BookmarkBinding by viewBinding()
    var db = HealthyChildDatabase.getInstance(App.instance)
    var articleList =
        db.articleFavouriteDao().getAllFavouriteArticles() as ArrayList<ArticleFavourite>
    private val favouriteArticleAdapter: FavouriteArticleAdapter = FavouriteArticleAdapter()
    var updateList = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.bookmarkTv.isVisible = articleList.size == 0
        favouriteArticleAdapter.submitList(articleList)
        binding.favouriteArticleRV.adapter = favouriteArticleAdapter
        adapterClick()
    }

    private fun adapterClick() {
        favouriteArticleAdapter.setOnCategoryClickListener { categoryId ->
            val bundle = bundleOf("categoryId" to categoryId)
            findNavController().navigate(R.id.articleOfCategory, bundle)
        }
        favouriteArticleAdapter.setOnItemClickListener { articleId ->
            val bundle = bundleOf("articleId" to articleId)
            findNavController().navigate(R.id.article, bundle)
        }
        favouriteArticleAdapter.setOnFavouriteClickListener { articleId, position ->
            val article = db.articleDao().getArticleById(articleId)
            val articleFavourite = db.articleFavouriteDao().getArticleFavouriteById(articleId)
            for (a in articleList) {
                if (a.id == article.id) {
                    articleList.remove(a)
                    break
                }
            }
            article.favourite = 0
            db.articleDao().updateArticle(article)
            ArticleFavouriteManage.deleteArticle(articleFavourite)
            favouriteArticleAdapter.notifyItemRemoved(articleList.size)
            favouriteArticleAdapter.notifyItemRangeChanged(position, articleList.size)
            favouriteArticleAdapter.submitList(articleList)
            binding.bookmarkTv.isVisible = articleList.size == 0
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        updateList = true
    }

    override fun onResume() {
        super.onResume()
        if (updateList) {
            articleList =
                db.articleFavouriteDao().getAllFavouriteArticles() as ArrayList<ArticleFavourite>
            favouriteArticleAdapter.submitList(articleList)
            updateList = false
        }
    }
}