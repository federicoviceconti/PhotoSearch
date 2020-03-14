package com.example.photosearch.home.view

import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class LoadingScroll(private val layoutManager: StaggeredGridLayoutManager) : NestedScrollView.OnScrollChangeListener {
    var shouldLoad: Boolean = true
    var listener: LoadingScrollListener? = null

    override fun onScrollChange(
        v: NestedScrollView,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
        if (v.getChildAt(v.childCount - 1) != null) {
            if (scrollY >= v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight && scrollY > oldScrollY) {
                var pastVisibleItems = 0
                val visibleItemCount = layoutManager.getChildCount()
                val totalItemCount = layoutManager.getItemCount()
                var firstVisibleItems: IntArray? = null
                firstVisibleItems = layoutManager.findFirstVisibleItemPositions(firstVisibleItems)
                if (firstVisibleItems != null && firstVisibleItems.isNotEmpty()) {
                    pastVisibleItems = firstVisibleItems[0]
                }

                if (shouldLoad) {
                    if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                        listener?.loadMore()
                    }
                }
            }
        }
    }

    interface LoadingScrollListener {
        fun loadMore()
    }
}