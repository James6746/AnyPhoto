package com.example.anyphoto.model.photos

import com.google.gson.annotations.SerializedName


data class Urls(
    val small: String? = null,
    @SerializedName("small_s3")
    val smallS3: String? = null,
    val thumb: String? = null,
    val raw: String? = null,
    val regular: String? = null,
    val full: String? = null
)