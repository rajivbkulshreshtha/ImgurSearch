package com.example.imgursearch.ui.main.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.imgursearch.R
import com.example.imgursearch.data.model.Image
import kotlinx.android.synthetic.main.adapter_image.view.*
import javax.inject.Inject

class ImageAdapter @Inject constructor(
	private val glide: RequestManager
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
	class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

	private val diffCallback = object : DiffUtil.ItemCallback<Image>() {
		override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
			return oldItem.imageUrl == newItem.imageUrl
		}

		override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
			return oldItem.imageUrl == newItem.imageUrl
		}
	}

	private val differ = AsyncListDiffer(this, diffCallback)

	var images: List<Image>
		get() = differ.currentList
		set(value) = differ.submitList(value)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
		return ImageViewHolder(
			LayoutInflater.from(parent.context).inflate(
				R.layout.adapter_image,
				parent,
				false
			)
		)
	}

	private var onItemClickListener: ((Image) -> Unit)? = null

	fun setOnItemClickListener(listener: (Image) -> Unit) {
		onItemClickListener = listener
	}

	override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
		val image = images[position]
		holder.itemView.apply {
			glide.load(image.imageUrl).into(imageView)
			tvTitle.text = image.title

			setOnClickListener {
				onItemClickListener?.let { click ->
					click(image)
				}
			}
		}
	}

	override fun getItemCount(): Int {
		return images.size
	}
}



