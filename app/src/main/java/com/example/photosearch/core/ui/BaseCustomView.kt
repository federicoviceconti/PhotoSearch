package com.example.photosearch.core.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout

abstract class BaseCustomView : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        initView()
    }

    private fun initView() {
        val view = LayoutInflater.from(context).inflate(getLayout(), this, true)
        onInitFinished(view)
    }

    abstract fun getLayout(): Int
    abstract fun onInitFinished(view: View)
}