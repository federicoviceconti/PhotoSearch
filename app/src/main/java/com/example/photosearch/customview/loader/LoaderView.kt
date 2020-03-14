package com.example.photosearch.customview.loader

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.example.photosearch.R
import com.example.photosearch.core.ui.BaseCustomView
import kotlinx.android.synthetic.main.component_loader_view.view.*

class LoaderView: BaseCustomView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun getLayout(): Int = R.layout.component_loader_view

    override fun onInitFinished(view: View) {}

    fun showLoader() {
        visibility = View.VISIBLE
        lottieAnimation.playAnimation()
    }

    fun hideLoader() {
        visibility = View.GONE
        lottieAnimation.pauseAnimation()
    }
}