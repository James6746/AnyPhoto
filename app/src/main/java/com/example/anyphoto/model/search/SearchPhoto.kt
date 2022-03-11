package com.example.anyphoto.model.search

import com.example.anyphoto.model.photos.Photo
import com.google.gson.annotations.SerializedName

data class SearchPhoto(
    val total: Int? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    val results: ArrayList<Photo>? = null
)

