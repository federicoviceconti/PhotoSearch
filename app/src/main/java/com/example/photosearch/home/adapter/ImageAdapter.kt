package com.example.photosearch.home.adapter

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.photosearch.R
import com.example.photosearch.core.adapter.BaseAdapter
import com.example.photosearch.core.extension.getDrawableByName
import com.example.photosearch.home.model.Photo
import kotlinx.android.synthetic.main.rv_item_image.view.*

class ImageAdapter(list: List<Photo>): BaseAdapter<Photo>(list) {
    var listener: ImageAdapterListener? = null

    override fun onCreateHolder(itemView: View) = TagHolder(itemView)
    override fun getHolderLayout(): Int = R.layout.rv_item_image

    inner class TagHolder(view: View): BaseHolder(view) {
        private lateinit var image: ImageView
        private lateinit var favorite: ImageView

        override fun initViewReference(itemView: View) {
            image = itemView.image
            favorite = itemView.favorite

            image.setOnClickListener { onItemPressed(model) }
            favorite.setOnClickListener { onItemLiked(model) }
        }

        override fun onModelCreated() {
            val thumb = model.url.regular

            if(thumb.isNotEmpty()) {
                Glide
                    .with(itemView.context)
                    .load(thumb)
                    .centerCrop()
                    .placeholder(R.drawable.ic_favorite_full)
                    .into(image)
            }

            image.contentDescription = model.altDescription

            val icon = if(!model.isFavourite) R.drawable.ic_favorite_red else R.drawable.ic_favorite_red_full
            favorite.setImageResource(icon)
        }
    }

    private fun onItemLiked(photo: Photo?) {
        listener?.onPhotoLiked(photo)
    }

    private fun onItemPressed(photo: Photo) {
        listener?.onPhotoPressed(photo)
    }

    interface ImageAdapterListener {
        fun onPhotoPressed(photo: Photo)
        fun onPhotoLiked(photo: Photo?)
    }
}