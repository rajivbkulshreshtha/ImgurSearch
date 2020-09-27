package com.example.imgursearch.ui.main.viewmodel

import android.content.Context
import android.util.Log
import android.widget.SearchView
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.imgursearch.R
import com.example.imgursearch.data.model.Image
import com.example.imgursearch.data.repo.DataRepo
import com.example.imgursearch.other.Constants.IMAGE_JPEG
import com.example.imgursearch.other.Constants.IMAGE_PNG
import com.example.imgursearch.other.Constants.SEARCH_DEBOUNCE
import com.example.imgursearch.other.Event
import com.example.imgursearch.other.Resource
import com.example.imgursearch.other.plus
import com.example.imgursearch.ui.base.BaseViewModel
import com.jakewharton.rxbinding3.widget.queryTextChangeEvents
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ImgurViewModel @ViewModelInject constructor(
	@ApplicationContext val context: Context,
	val repo: DataRepo, @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel(), LifecycleObserver {

	companion object {
		public const val TAG = "ImgurViewModel"
	}

	private val _pageNo: MutableLiveData<Int> = MutableLiveData(1)
	fun getPageNo(): LiveData<Int> = _pageNo

	private val _images = MutableLiveData<Event<Resource<List<Image>>>>()
	fun getImages(): LiveData<Event<Resource<List<Image>>>> = _images

	private val _image = MutableLiveData<Event<Resource<Image>>>()
	fun getImage(): LiveData<Event<Resource<Image>>> = _image

	private val _commentSaved = MutableLiveData<Event<Resource<Boolean>>>()
	fun getCommentSaved(): LiveData<Event<Resource<Boolean>>> = _commentSaved

	fun bindSearchView(
		searchView: SearchView
	) {

		bag + searchView.queryTextChangeEvents()
			.skipInitialValue()
			.debounce(SEARCH_DEBOUNCE, TimeUnit.MILLISECONDS)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe({
				val searchText = it.queryText.toString()
				if (!searchText.isBlank()) {
					getData(searchText)
				}
			}, {
				_images.value =
					Event(
						Resource.error(
							context.getString(R.string.failed_something_went_wrong),
							null
						)
					)
			})
	}

	private fun getData(query: String) {
		getDataFromServer(query)
	}

	private fun getDataFromServer(query: String) {
		_images.value = Event(Resource.loading(null))
		val pageNo: Int = getPageNo().value ?: 1
		bag + repo.search(pageNo, query)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.map { it.data?.filter { typeValidator(it.images?.first()?.type) } }
			.map {
				it.map { data ->
					Image(
						id = data.id,
						title = data.title,
						imageId = data.images?.first()?.id ?: "0",
						imageUrl = data.images?.first()?.link
					)
				}
			}
			.subscribe({
				_images.value = Event(Resource.success(it))
			}, {
				_images.value =
					Event(
						Resource.error(
							context.getString(R.string.failed_something_went_wrong),
							null
						)
					)
			})
	}


	fun localImage(image: Image) {
		_image.value = Event(Resource.loading(null))
		bag + repo.getById(image.id, image.imageId)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe({
				Log.d(TAG, "localImage: succses => $it")
				_image.value = Event(Resource.success(it))
			}, {
				Log.d(TAG, "localImage: error => $image")
				_image.value = Event(Resource.success(image))
			})
	}

	fun saveImage(image: Image) {
		_commentSaved.value = Event(Resource.loading(null))
		bag + repo.insert(image)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe({
				_commentSaved.value = Event(Resource.success(true))
			}, {
				_commentSaved.value =
					Event(
						Resource.error(
							context.getString(R.string.failed_something_went_wrong),
							null
						)
					)
			})
	}

	private fun typeValidator(type: String?): Boolean {
		return if (type.isNullOrEmpty()) {
			false
		} else {
			type == IMAGE_JPEG || type == IMAGE_PNG
		}
	}

}
