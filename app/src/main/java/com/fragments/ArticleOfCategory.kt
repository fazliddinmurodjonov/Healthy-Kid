package com.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.adapters.MenuArticleAdapter
import com.example.App
import com.example.healthychild.R
import com.example.healthychild.databinding.ArticleOfCategoryBinding
import com.room.database.HealthyChildDatabase
import com.room.entity.Article
import com.room.entity.ArticleFavourite
import com.utils.ArticleFavouriteManage
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.ArrayList

class ArticleOfCategory : Fragment(R.layout.article_of_category) {
    private val binding: ArticleOfCategoryBinding by viewBinding()
    var db = HealthyChildDatabase.getInstance(App.instance)
    private val menuArticleAdapter: MenuArticleAdapter = MenuArticleAdapter()
    var categoryID = 0
    private lateinit var articleList: ArrayList<Article>
    var firstLoad = true
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        loadActionBar()
        val categoryId = arguments?.getInt("categoryId")
        categoryID = categoryId!!
        if (firstLoad) {
            articleList =
                db.articleDao().getAllArticlesByCategory(categoryId!!) as ArrayList<Article>
            menuArticleAdapter.submitList(articleList)
            firstLoad = false
        }
        for (article in articleList) {
            val a = db.articleDao().getArticleById(article.id!!)
            article.favourite = a.favourite
        }
        val category = db.categoryDao().getCategoryById(categoryId!!)
        (activity as AppCompatActivity).supportActionBar!!.title = category.categoryNameUzb
        binding.articleOfCategoryRV.adapter = menuArticleAdapter
        adapterClick()
    }

    private fun loadActionBar() {
        (activity as AppCompatActivity).supportActionBar!!.setHomeAsUpIndicator(R.drawable.back)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun adapterClick() {
        menuArticleAdapter.setOnItemClickListener { articleId ->
            val bundle = bundleOf("articleId" to articleId)
            findNavController().navigate(R.id.article, bundle)
        }

        menuArticleAdapter.setOnFavouriteClickListener { favourite, a ->
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().popBackStack()
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        menuArticleAdapter.submitList(articleList)
    }

}