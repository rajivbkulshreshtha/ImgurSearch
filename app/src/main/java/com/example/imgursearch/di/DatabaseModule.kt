package com.example.imgursearch.di

import android.content.Context
import androidx.room.Room
import com.example.imgursearch.data.cache.LocalDatabase
import com.example.imgursearch.other.Constants.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

	@Singleton
	@Provides
	fun provideDatabase(
		@ApplicationContext app: Context
	) = Room.databaseBuilder(app, LocalDatabase::class.java, DB_NAME).build()


	@Singleton
	@Provides
	fun provideImageDao(db: LocalDatabase) = db.imageDao()

}
