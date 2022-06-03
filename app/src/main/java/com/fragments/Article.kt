package com.fragments

import android.os.Bundle
import android.view.*
import android.view.Menu
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.healthychild.R
import com.example.healthychild.databinding.ArticleBinding
import com.room.database.HealthyChildDatabase
import com.room.entity.Article
import com.room.entity.ArticleFavourite
import com.utils.ArticleFavouriteManage

class Article : Fragment(R.layout.article) {
    private val binding: ArticleBinding by viewBinding()
    lateinit var db: HealthyChildDatabase
    lateinit var article: Article
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        loadBackBtn()
        var articleId = arguments?.getInt("articleId")
        db = HealthyChildDatabase.getInstance(requireContext())
        article = db.articleDao().getArticleById(articleId!!)
        val image = context?.resources?.getIdentifier(article.image,
            "drawable",
            requireActivity().packageName)
        binding.articleImg.setImageResource(image!!)
        binding.articleTitle.text = article.title
        binding.articleText.text = article.text!!.replace("/n", "\n")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.bookmark, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bookmark_article -> {
                if (article.favourite == 1) {
                    item.setIcon(R.drawable.bookmark_empty_blue)
                    article.favourite = 0
                    db.articleDao().updateArticle(article)
                    val articleFavourite = ArticleFavouriteManage.getArticleFavourite(article.id!!)
                    ArticleFavouriteManage.deleteArticle(articleFavourite)
                } else {
                    item.setIcon(R.drawable.bookmark_blue)
                    article.favourite = 1
                    db.articleDao().updateArticle(article)
                    val articleFavourite = ArticleFavourite(article.id,
                        article.title,
                        article.text,
                        article.image,
                        article.favourite,
                        article.categoryId)
                    ArticleFavouriteManage.insertArticle(articleFavourite)
                }
            }
            else -> {
                findNavController().popBackStack()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadBackBtn() {
        (activity as AppCompatActivity).supportActionBar!!.setHomeAsUpIndicator(R.drawable.back)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if (article.favourite == 1) {
            menu.findItem(R.id.bookmark_article).setIcon(R.drawable.bookmark_blue)
        } else {
            menu.findItem(R.id.bookmark_article).setIcon(R.drawable.bookmark_empty_blue)
        }
        super.onPrepareOptionsMenu(menu)
    }
}