package com.example.imgursearch.other

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

operator fun CompositeDisposable?.plus(disposable: Disposable?): CompositeDisposable? {
	disposable?.let {
		this?.add(it)
	}
	return this
}


inline fun View.hide() {
	this.visibility = View.GONE
}

inline fun View.show() {
	this.visibility = View.VISIBLE
}

fun ActivityCompat.shortToast(context: Context, text: String) {
	Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}
