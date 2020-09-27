package com.example.imgursearch.di

import android.content.Context
import android.graphics.Color
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.imgursearch.R
import com.example.imgursearch.data.cache.dao.ImageDao
import com.example.imgursearch.data.network.Routes
import com.example.imgursearch.data.repo.DataRepo
import com.example.imgursearch.data.repo.DataRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

	@Singleton
	@Provides
	fun provideRepo(
		routes: Routes,
		imageDao: ImageDao
	): DataRepo = DataRepoImpl(routes, imageDao)

	@Singleton
	@Provides
	fun provideGlideInstance(
		@ApplicationContext context: Context
	) = Glide.with(context).setDefaultRequestOptions(
		RequestOptions()
			.placeholder(CircularProgressDrawable(context).apply {
				setColorSchemeColors(Color.parseColor("#1BB76E"))
				strokeWidth = 5f
				centerRadius = 30f
				start()
			})
			.error(R.drawable.ic_warning)
	)
}
