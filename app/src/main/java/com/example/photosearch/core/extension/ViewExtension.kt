package com.example.photosearch.core.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photosearch.core.adapter.BaseAdapter
import com.example.photosearch.core.ui.SpannedGridLayoutManager
import android.util.DisplayMetrics
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.airbnb.lottie.LottieAnimationView


fun <S> RecyclerView.initWithLinearAdapter(base: BaseAdapter<S>, context: Context?, orientation: Int): LinearLayoutManager {
    val manager = LinearLayoutManager(context, orientation, false)

    apply {
        layoutManager = manager
        adapter = base
    }

    return manager
}

fun <S> RecyclerView.initWithSpannableGridAdapter(
    base: BaseAdapter<S>,
    column: Int,
    gridSpan: SpannedGridLayoutManager.GridSpanLookup,
    defaultItemHeight: Int = 1
): SpannedGridLayoutManager {
    val manager = SpannedGridLayoutManager(gridSpan, column, defaultItemHeight.toFloat())

    apply {
        layoutManager = manager
        adapter = base
    }

    return manager
}

fun <S> RecyclerView.initWithStaggered(
    base: BaseAdapter<S>,
    spanCount: Int,
    orientation: Int
): StaggeredGridLayoutManager {
    val manager = StaggeredGridLayoutManager(spanCount, orientation)

    apply {
        layoutManager = manager
        adapter = base
    }

    return manager
}

fun View.enableView(enable: Boolean) { visibility = if(enable) View.VISIBLE else View.GONE }

fun Context.getDrawableByName(name: String): Drawable {
    val resourceId = resources.getIdentifier(name, "drawable", packageName)
    return resources.getDrawable(resourceId)
}

fun LottieAnimationView.showAndPlayAnimation() {
    visibility = View.VISIBLE
    playAnimation()
}

fun LottieAnimationView.hideAndPauseAnimation() {
    visibility = View.GONE
    pauseAnimation()
}