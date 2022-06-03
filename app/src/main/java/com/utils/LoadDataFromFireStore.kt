package com.utils

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.room.database.HealthyChildDatabase
import com.room.entity.Article
import com.room.entity.Categories

object LoadDataFromFireStore {

    fun loadData(context: Context) {
        val db = HealthyChildDatabase.getInstance(context)
        val fireStore = FirebaseFirestore.getInstance()

        fireStore.collection("articles")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result
                    for (queryDocumentSnapshot in result) {
                        val a = queryDocumentSnapshot.toObject(ArticleFirebase::class.java)
                        val art = Article(a.title,
                            a.text,
                            a.image,
                            0,
                            a.categoryId)
                        db.articleDao().insertArticle(art)
                    }
                }
            }
        fireStore.collection("category")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result
                    for (queryDocumentSnapshot in result) {
                        val c = queryDocumentSnapshot.toObject(Categories::class.java)
                        val category = Categories(c.categoryName, c.categoryNameUzb, c.categoryImg)
                        db.categoryDao().insertCategory(category)
                    }
                }
            }
    }


}
