package com.example.imgursearch.data.repo

import com.example.imgursearch.data.model.Image
import com.example.imgursearch.data.model.SearchResponse
import io.reactivex.Completable
import io.reactivex.Single

interface DataRepo {
	fun search(pageNo: Int, query: String): Single<SearchResponse>

	fun insert(Image: Image): Completable

	fun getById(id: String, imageId: String): Single<Image>
}
