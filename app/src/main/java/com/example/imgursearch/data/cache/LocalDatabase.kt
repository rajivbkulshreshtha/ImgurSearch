package com.example.imgursearch.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.imgursearch.data.cache.dao.ImageDao
import com.example.imgursearch.data.model.Image

@Database(entities = [Image::class], version = 1, exportSchema = false)
//@TypeConverters()
abstract class LocalDatabase : RoomDatabase() {
	abstract fun imageDao(): ImageDao
}
