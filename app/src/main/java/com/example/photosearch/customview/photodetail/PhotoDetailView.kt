package com.example.photosearch.customview.photodetail

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.example.photosearch.R
import com.example.photosearch.core.ui.BaseCustomView
import com.example.photosearch.home.model.PhotoDetail

class PhotoDetailView: BaseCustomView {
    override fun getLayout(): Int = R.layout.component_photo_detail_view

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onInitFinished(view: View) {}

    fun populateDetail(photo: PhotoDetail) {
        showDetail(photo.showing)
    }

    fun showDetail(show: Boolean) {

    }
}