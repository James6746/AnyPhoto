package com.example.anyphoto.model.photos

data class Photo(
	val id: String? = null,
	val urls: Urls? = null,
	val user: User? = null,
	val description: Any? = null,

	val createdAt: String? = null,
	val links: Links? = null,
	val categories: List<Any?>? = null,
	val likes: Int? = null
) {

	override fun toString(): String {
		return "Photo(id=$id, urls=$urls, user=$user, description=$description, createdAt=$createdAt, links=$links, categories=$categories, likes=$likes)"
	}
}

