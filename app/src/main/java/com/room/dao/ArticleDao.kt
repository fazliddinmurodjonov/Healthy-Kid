package com.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.room.entity.Article
import io.reactivex.rxjava3.core.Flowable

@Dao
interface ArticleDao {
    @Insert
    fun insertArticle(article: Article)

    @Update
    fun updateArticle(article: Article)

    @Query("select *from Article where id = :articleId")
    fun getArticleById(articleId: Int): Article

    @Query("select *from Article")
    fun getAllArticlesFlowable(): Flowable<List<Article>>

    @Query("select *from Article")
    fun getAllArticles(): List<Article>

    @Query("select *from Article where categoryId = :categoryId")
    fun getAllArticlesByCategory(categoryId: Int): List<Article>

    @Query("select *from Article where favourite = 1")
    fun getAllFavouriteArticles(): List<Article>
}