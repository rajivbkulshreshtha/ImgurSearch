package com.example.imgursearch.data.model


import com.google.gson.annotations.SerializedName

data class SearchResponse(
	@SerializedName("data")
	var `data`: List<Data>?
)

data class Data(
	@SerializedName("id")
	var id: String, // pMeygDP
	@SerializedName("images")
	var images: List<ImageNetwork>?,
	@SerializedName("title")
	var title: String? // Vanilla-cat
)


data class ImageNetwork(
	@SerializedName("id")
	var id: String?, // KeMIFh8
	@SerializedName("link")
	var link: String?, // https://i.imgur.com/KeMIFh8.jpg
	@SerializedName("type")
	var type: String? // image/jpeg
)


