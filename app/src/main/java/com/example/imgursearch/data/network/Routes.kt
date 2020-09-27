package com.example.imgursearch.data.network

import com.example.imgursearch.data.model.SearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Routes {
	@GET("search/{page_no}")
	fun search(@Path("page_no") pageNo: Int, @Query("q") query: String): Single<SearchResponse>
}
