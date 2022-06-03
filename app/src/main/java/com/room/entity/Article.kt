package com.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Article {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var title: String? = null
    var text: String? = null
    var image: String? = null
    var favourite: Int? = null
    var categoryId: Int? = null
    constructor()
    constructor(
        title: String?,
        text: String?,
        image: String?,
        favourite: Int?,
        categoryId: Int?,
    ) {
        this.title = title
        this.text = text
        this.image = image
        this.favourite = favourite
        this.categoryId = categoryId
    }

}