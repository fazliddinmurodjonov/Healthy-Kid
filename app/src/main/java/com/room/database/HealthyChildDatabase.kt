package com.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.room.dao.ArticleDao
import com.room.dao.ArticleFavouriteDao
import com.room.dao.CategoryDao
import com.room.entity.Article
import com.room.entity.ArticleFavourite
import com.room.entity.Categories

@Database(entities = [Article::class, ArticleFavourite::class, Categories::class], version = 1)
abstract class HealthyChildDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun categoryDao(): CategoryDao
    abstract fun articleFavouriteDao(): ArticleFavouriteDao

    companion object {
        private var instance: HealthyChildDatabase? = null

        @Synchronized
        fun getInstance(context: Context): HealthyChildDatabase {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context,
                        HealthyChildDatabase::class.java,
                        "healthy_child_db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
            }
            return instance!!
        }

    }
}