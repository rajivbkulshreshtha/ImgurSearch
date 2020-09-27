package com.example.imgursearch.ui.main.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.imgursearch.R
import com.example.imgursearch.di.MainFragmentFactory
import com.example.imgursearch.ui.main.viewmodel.ImgurViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {



	companion object {
		const val TAG = "MainActivity"
	}

	@Inject
	lateinit var fragmentFactory: MainFragmentFactory

	override fun onCreate(savedInstanceState: Bundle?) {

		super.onCreate(savedInstanceState)
		supportFragmentManager.fragmentFactory = fragmentFactory
		setContentView(R.layout.activity_main)

	}
}
