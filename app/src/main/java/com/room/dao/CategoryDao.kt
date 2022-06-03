package com.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.room.entity.Article
import com.room.entity.Categories
import io.reactivex.rxjava3.core.Flowable

@Dao
interface CategoryDao {
    @Insert
    fun insertCategory(category: Categories)

    @Query("select *from  Categories")
    fun getAllCategories(): List<Categories>

    @Query("select *from  Categories")
    fun getAllCategoriesFlowable(): Flowable<List<Categories>>

    @Query("select *from Categories where id = :categoryId")
    fun getCategoryById(categoryId: Int): Categories
}