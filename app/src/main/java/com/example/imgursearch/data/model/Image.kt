package com.example.imgursearch.data.model


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(primaryKeys = ["id", "image_id"], tableName = "Image")
data class Image(
	@ColumnInfo(name = "id")
	@SerializedName("id")
	var id: String, // pMeygDP

	@ColumnInfo(name = "image_id")
	@SerializedName("image_id")
	var imageId: String, // KeMIFh8

	@ColumnInfo(name = "image_url")
	@SerializedName("image_url")
	var imageUrl: String?, // https://i.imgur.com/KeMIFh8.jpg

	@ColumnInfo(name = "title")
	@SerializedName("title")
	var title: String?, // Vanilla-cat

	@ColumnInfo(name = "comment")
	@SerializedName("comment")
	var comment: String? = null // Random comment

) : Parcelable
