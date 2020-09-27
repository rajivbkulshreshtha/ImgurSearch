package com.example.imgursearch.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.imgursearch.ui.main.view.adapter.ImageAdapter
import com.example.imgursearch.ui.main.view.fragment.DetailFragment
import com.example.imgursearch.ui.main.view.fragment.SearchFragment
import javax.inject.Inject

class MainFragmentFactory @Inject constructor(
	private val imageAdapter: ImageAdapter,
	private val glide: RequestManager
) : FragmentFactory() {

	companion object {
		public const val TAG = "MainFragmentFactory"
	}

	override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

		return when (className) {
			SearchFragment::class.java.name -> SearchFragment(imageAdapter)
			DetailFragment::class.java.name -> DetailFragment(glide)
			else -> super.instantiate(classLoader, className)
		}
	}
}
