package com.example.anyphoto.model.topics

data class Topic(
	val id: String? = null,
	val title: String? = null,
	val slug: String? = null,
	val description: String? = null,
	val status: String? = null,
	val totalPhotos: Int? = null,
	val coverPhoto: CoverPhoto? = null,

	val featured: Boolean? = null,
	val owners: List<OwnersItem?>? = null,
	val previewPhotos: List<PreviewPhotosItem?>? = null,
	val totalCurrentUserSubmissions: Any? = null,
	val currentUserContributions: List<Any?>? = null,
	val links: Links? = null,
	val endsAt: String? = null
)
