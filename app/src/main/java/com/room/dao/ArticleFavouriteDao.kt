package com.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.room.entity.ArticleFavourite

@Dao
interface ArticleFavouriteDao {
    @Insert
    fun insertArticle(article: ArticleFavourite)

    @Delete
    fun deleteArticle(article: ArticleFavourite)

    @Query("select *from ArticleFavourite")
    fun getAllFavouriteArticles(): List<ArticleFavourite>

    @Query("select *from ArticleFavourite where id = :id")
    fun getArticleFavouriteById(id: Int): ArticleFavourite

}