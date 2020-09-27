package com.example.imgursearch

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import zerobranch.androidremotedebugger.AndroidRemoteDebugger

@HiltAndroidApp
class BaseApplication : Application() {

	companion object {
		public const val TAG = "BaseApplication"
	}

	override fun onCreate() {
		super.onCreate()

		if (BuildConfig.DEBUG) {
			AndroidRemoteDebugger.init(this)
		}
	}

}
