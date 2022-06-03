package com.utils

import androidx.room.Entity
import androidx.room.PrimaryKey

class ArticleFirebase {
    var id: Int? = null
    var title: String? = null
    var text: String? = null
    var image: String? = null
    var favourite: Boolean? = null
    var categoryId: Int? = null
    constructor()
    constructor(
        title: String?,
        text: String?,
        image: String?,
        favourite: Boolean?,
        categoryId: Int?,
    ) {
        this.title = title
        this.text = text
        this.image = image
        this.favourite = favourite
        this.categoryId = categoryId
    }

}