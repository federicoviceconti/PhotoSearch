package com.example.photosearch.home.viewmodel

import android.app.Application
import android.content.Context
import com.example.photosearch.R
import com.example.photosearch.customview.menu.MenuItem

fun MenuItem.getSubtitle(app: Application): String = getSubtitle(app.applicationContext)
fun MenuItem.getSubtitle(context: Context): String {
    return when(this) {
        MenuItem.FAVORITE -> context.getString(R.string.what_you_like)
        MenuItem.DISCOVER -> context.getString(R.string.empty)
    }
}

fun MenuItem.getTitle(app: Application): String = getTitle(app.applicationContext)
fun MenuItem.getTitle(context: Context): String {
    return when(this) {
        MenuItem.FAVORITE -> context.getString(R.string.favorite)
        MenuItem.DISCOVER -> context.getString(R.string.discover)
    }
}

fun MenuItem.getIcon(): String {
    return when(this) {
        MenuItem.DISCOVER -> "ic_home"
        MenuItem.FAVORITE -> "ic_favorite"
    }
}