package com.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Categories {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var categoryName: String? = null
    var categoryNameUzb: String? = null
    var categoryImg: String? = null

    constructor()
    constructor(categoryName: String?, categoryNameUzb: String?, categoryImg: String?) {
        this.categoryName = categoryName
        this.categoryNameUzb = categoryNameUzb
        this.categoryImg = categoryImg
    }


}