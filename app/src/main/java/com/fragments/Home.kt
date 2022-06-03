package com.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.adapters.HomeArticleAdapter
import com.example.App
import com.example.healthychild.R
import com.example.healthychild.databinding.HomeBinding
import com.room.database.HealthyChildDatabase
import com.room.entity.Article
import com.room.entity.ArticleFavourite
import com.room.entity.Categories
import com.utils.ArticleFavouriteManage
import com.utils.LoadDataFromFireStore
import com.utils.NetworkHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class Home : Fragment(R.layout.home) {
    private val binding: HomeBinding by viewBinding()
    var db = HealthyChildDatabase.getInstance(App.instance)
    private var articleList = db.articleDao().getAllArticles().shuffled()
    private val homeAdapter: HomeArticleAdapter = HomeArticleAdapter()
    private lateinit var handler: Handler
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler = Handler(Looper.getMainLooper())
        homeAdapter.submitList(articleList)
        binding.homeArticleRV.adapter = homeAdapter
        for (article in articleList) {
            val a = db.articleDao().getArticleById(article.id!!)
            article.favourite = a.favourite
        }
        if (db.articleDao().getAllArticles().isEmpty()) {
            handler.postDelayed(runnable, 0)
        }
        binding.swipeHome.setOnRefreshListener {
            val runnable = Runnable {
                if (articleList.isEmpty()) {
                    articleList = db.articleDao().getAllArticles().shuffled()
                    homeAdapter.submitList(articleList)
                }
                binding.swipeHome.isRefreshing = false
            }
            handler.postDelayed(runnable, 150)
            binding.swipeHome.setColorSchemeResources(
                R.color.blue,
            )
        }
        adapterClick()
    }

    private fun adapterClick() {
        homeAdapter.setOnCategoryClickListener { categoryId ->
            val bundle = bundleOf("categoryId" to categoryId)
            findNavController().navigate(R.id.articleOfCategory, bundle)
        }
        homeAdapter.setOnItemClickListener { articleId ->
            val bundle = bundleOf("articleId" to articleId)
            findNavController().navigate(R.id.article, bundle)
        }
        homeAdapter.setOnFavouriteClickListener { favourite, a ->
            if (favourite == 1) {
                a.favourite = 1
                db.articleDao().updateArticle(a)
                val articleFavourite =
                    ArticleFavourite(a.id, a.title, a.text, a.image, a.favourite, a.categoryId)
                ArticleFavouriteManage.insertArticle(articleFavourite)
            } else {
                a.favourite = 0
                db.articleDao().updateArticle(a)
                val articleFavourite = ArticleFavouriteManage.getArticleFavourite(a.id!!)
                ArticleFavouriteManage.deleteArticle(articleFavourite)
            }
        }
    }

    private var runnable = object : Runnable {
        override fun run() {
            if (db.articleDao().getAllArticles().isNotEmpty()) {
                handler.removeCallbacks(this)
                articleList = db.articleDao().getAllArticles().shuffled()
                homeAdapter.submitList(articleList)

            } else {
                handler.postDelayed(this, 0)
            }
        }
    }

    override fun onResume() {
        super.onResume()
//        if (db.articleDao().getAllArticles().isNotEmpty()) {
//            articleList = db.articleDao().getAllArticles().shuffled()
//            homeAdapter.submitList(articleList)
//        }
        homeAdapter.submitList(articleList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable)
    }
}
