package com.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ArticleFavourite {
    @PrimaryKey(autoGenerate = true)
    var favouriteId: Int? = null
    var id: Int? = null
    var title: String? = null
    var text: String? = null
    var image: String? = null
    var favourite: Int? = null
    var categoryId: Int? = null

    constructor()
    constructor(
        id: Int?,
        title: String?,
        text: String?,
        image: String?,
        favourite: Int?,
        categoryId: Int?,
    ) {
        this.id = id
        this.title = title
        this.text = text
        this.image = image
        this.favourite = favourite
        this.categoryId = categoryId
    }


}