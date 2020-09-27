package com.example.imgursearch.ui.main.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imgursearch.R
import com.example.imgursearch.other.Constants.GRID_SPAN_COUNT
import com.example.imgursearch.other.Status
import com.example.imgursearch.ui.main.view.adapter.ImageAdapter
import com.example.imgursearch.ui.main.viewmodel.ImgurViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search.*


@AndroidEntryPoint
class SearchFragment(
	val imageAdapter: ImageAdapter
) : Fragment(R.layout.fragment_search) {

	companion object {
		public const val TAG = "SearchFragment"
	}


	private val viewModel: ImgurViewModel by navGraphViewModels(R.id.nav_graph) {
		defaultViewModelProviderFactory
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setupRecyclerView()
		viewModel.bindSearchView(searchView)
		subscribeToObservers()
		clickHandler()
	}


	private fun setupRecyclerView() {
		recyclerView.apply {
			adapter = imageAdapter
			layoutManager = GridLayoutManager(requireContext(), GRID_SPAN_COUNT)
		}

	}

	private fun subscribeToObservers() {
		viewModel.getImages().observe(viewLifecycleOwner, Observer {
			it?.getContentIfNotHandled()?.let { result ->
				when (result.status) {
					Status.SUCCESS -> {
						progressBar.visibility = View.GONE

						imageAdapter.images = result.data ?: mutableListOf()
					}
					Status.ERROR -> {
						Snackbar.make(
							requireActivity().rootLayout,
							R.string.failed_unknown_error,
							Snackbar.LENGTH_SHORT
						).apply {
							this.view.setBackgroundColor(Color.RED)
						}.show()
						progressBar.visibility = View.GONE
					}
					Status.LOADING -> {
						progressBar.visibility = View.VISIBLE
					}
				}
			}
		})
	}


	private fun clickHandler() {
		imageAdapter.setOnItemClickListener {
			findNavController().navigate(
				SearchFragmentDirections.actionSearchFragmentToDetailFragment(
					it
				)
			)
		}
	}


}
