package com.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Base64.encodeToString
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.healthychild.R
import com.example.healthychild.databinding.HomeBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.room.database.HealthyChildDatabase
import com.room.entity.Article
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList

class Home : Fragment(R.layout.home) {
    private val binding: HomeBinding by viewBinding()
    lateinit var firebaseFireStore: FirebaseFirestore
    lateinit var firebaseStorage: FirebaseStorage
    lateinit var reference: StorageReference
    lateinit var healthyChildDatabase: HealthyChildDatabase
    lateinit var articleList: ArrayList<Article>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        healthyChildDatabase = HealthyChildDatabase.getInstance(requireContext())
        firebaseFireStore = FirebaseFirestore.getInstance()
        articleList = ArrayList()
        firebaseStorage = FirebaseStorage.getInstance()
        reference = firebaseStorage.getReference("category")
//        reference = firebaseStorage.getReference("health")
//        firebaseFireStore.collection("health")
//            .get()
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    val result = it.result
//                    for (queryDocumentSnapshot in result) {
//                        val category = queryDocumentSnapshot.toObject(Article::class.java)
//                        articleList.add(category)
//                    }
//
//                    var article = Article(articleList[0].articleImg.toString())
//                    healthyChildDatabase.articleDao().insertArticle(article)
//                    // binding.tv.text = articleList[0].articleText
//                    //Toast.makeText(requireContext(), "${articleList[0].articleImg}", Toast.LENGTH_SHORT).show()
//
//
////                    Picasso.get()
////                        .load(articleList[2].categoryImg.toString())
////                        .into(binding.articleImg)
//                }
//            }

        val ONE_MEGABYTE: Long = 1024 * 1024
        reference.getBytes(ONE_MEGABYTE).addOnSuccessListener {
            // Data for "images/island.jpg" is returned, use this as needed
            var str = ""

            var bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            bitmap.apply {
                str = toBase64String()
            }
//            val toString = bitmap.toString()
//            binding.articleImg.setImageBitmap(bitmap)
//            Toast.makeText(requireContext(), "${toString}", Toast.LENGTH_SHORT).show()
            var article = Article(str)
            healthyChildDatabase.articleDao().insertArticle(article)
            Toast.makeText(requireContext(), "${str.length}", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            // Handle any errors
        }

    }

    fun Bitmap.toBase64String(): String {
        ByteArrayOutputStream().apply {
            compress(Bitmap.CompressFormat.PNG, 10, this)
            return Base64.encodeToString(toByteArray(), Base64.DEFAULT)
        }
    }
}