package com.example.imgursearch.data.cache.dao

import androidx.room.*
import com.example.imgursearch.data.model.Image
import io.reactivex.Completable
import io.reactivex.Single


@Dao
interface ImageDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertAll(User: List<Image>): Completable

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insert(Image: Image): Completable

	@Delete
	fun delete(Image: Image): Completable

	@Query("DELETE FROM Image")
	fun deleteAll(): Completable

	@Query("SELECT * FROM Image")
	fun getAll(): Single<List<Image>>

	@Query("SELECT * FROM Image LIMIT 1")
	fun get(): Single<Image>

	@Query("SELECT * FROM Image WHERE id = :id AND image_id = :imageId LIMIT 1")
	fun getById(id: String, imageId: String): Single<Image>

	@Query("SELECT COUNT(*) from Image")
	fun count(): Single<Int>

}
