package com.example.imgursearch.data.network

import com.example.imgursearch.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

	companion object {
		public const val TAG = "AuthInterceptor"
	}

	override fun intercept(chain: Interceptor.Chain): Response {
		val authRequest = chain.request().newBuilder()
			.addHeader("Authorization", BuildConfig.ApiKey).build()
		return chain.proceed(authRequest)
	}
}
