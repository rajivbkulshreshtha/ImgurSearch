package com.example.imgursearch.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

	var bag: CompositeDisposable = CompositeDisposable()
	protected val _loading: MutableLiveData<Boolean> = MutableLiveData()
	protected val _error: MutableLiveData<Boolean> = MutableLiveData()

	fun getLoading(): LiveData<Boolean> = _loading
	fun getError(): LiveData<Boolean> = _error

	override fun onCleared() {
		super.onCleared()
		bag.dispose()
	}
}
