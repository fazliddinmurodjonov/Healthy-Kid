package com.utils

import com.example.App
import com.room.database.HealthyChildDatabase
import com.room.entity.ArticleFavourite

object ArticleFavouriteManage {
    var db = HealthyChildDatabase.getInstance(App.instance)
    fun insertArticle(article: ArticleFavourite) {
        db.articleFavouriteDao().insertArticle(article)
    }

    fun deleteArticle(article: ArticleFavourite) {
        db.articleFavouriteDao().deleteArticle(article)
    }

    fun getArticleFavourite(id: Int): ArticleFavourite {
        return db.articleFavouriteDao().getArticleFavouriteById(id)
    }

}