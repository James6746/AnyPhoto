package com.example.anyphoto.model.topics

data class CoverPhoto(
	val topicSubmissions: TopicSubmissions? = null,
	val currentUserCollections: List<Any?>? = null,
	val color: String? = null,
	val sponsorship: Any? = null,
	val createdAt: String? = null,
	val description: Any? = null,
	val likedByUser: Boolean? = null,
	val urls: Urls? = null,
	val altDescription: Any? = null,
	val updatedAt: String? = null,
	val width: Int? = null,
	val blurHash: String? = null,
	val links: Links? = null,
	val id: String? = null,
	val categories: List<Any?>? = null,
	val promotedAt: Any? = null,
	val user: User? = null,
	val height: Int? = null,
	val likes: Int? = null
)
