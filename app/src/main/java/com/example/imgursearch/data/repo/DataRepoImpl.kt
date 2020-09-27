package com.example.imgursearch.data.repo

import com.example.imgursearch.data.cache.dao.ImageDao
import com.example.imgursearch.data.model.Image
import com.example.imgursearch.data.model.SearchResponse
import com.example.imgursearch.data.network.Routes
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class DataRepoImpl @Inject constructor(var routes: Routes, var imageDao: ImageDao) : DataRepo {

	companion object {
		public const val TAG = "DataRepoImpl"

	}

	override fun search(pageNo: Int, query: String): Single<SearchResponse> {
		return routes.search(pageNo, query)
	}

	override fun insert(Image: Image): Completable {
		return imageDao.insert(Image)
	}

	override fun getById(id: String, imageId: String): Single<Image> {
		return imageDao.getById(id, imageId)
	}

}
