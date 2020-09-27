package com.example.imgursearch.data.network

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

class CacheInterceptor : Interceptor {

	private val control = CacheControl.Builder().maxStale(15, TimeUnit.MINUTES).build()

	companion object {
		public const val TAG = "CacheInterceptor"

	}

	@Throws(IOException::class)
	override fun intercept(chain: Interceptor.Chain): Response {
		synchronized(this) {
			var originalRequest = chain.request()
			originalRequest = originalRequest.newBuilder()
				.cacheControl(control)
				.build()
			return chain.proceed(originalRequest)
		}
	}

	private fun isFromCache(result: Response): Boolean {
		return result.networkResponse == null
	}

}
