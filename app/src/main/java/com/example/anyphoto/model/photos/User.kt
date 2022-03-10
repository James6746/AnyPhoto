package com.example.anyphoto.model.photos

import com.google.gson.annotations.SerializedName

data class User(
    val id: String? = null,
    val username: String? = null,
    val firstName: String? = null,
    @SerializedName("profile_image")
    val profileImage: ProfileImage? = null,
    val bio: String? = null,

    val totalPhotos: Int? = null,
    val social: Social? = null,
    val lastName: Any? = null,
    val totalLikes: Int? = null,
    val forHire: Boolean? = null,
    val name: String? = null,
    val links: Links? = null,
)