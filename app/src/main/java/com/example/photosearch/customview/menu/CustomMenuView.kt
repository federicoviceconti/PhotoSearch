package com.example.photosearch.customview.menu

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photosearch.R
import com.example.photosearch.core.extension.initWithLinearAdapter
import com.example.photosearch.core.ui.BaseCustomView
import kotlinx.android.synthetic.main.component_menu_view.view.*

class CustomMenuView : BaseCustomView, MenuAdapter.MenuAdapterListener {
    var listener: CustomMenuViewListener? = null
    var adapter = MenuAdapter(mutableListOf())

    override fun getLayout(): Int = R.layout.component_menu_view

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onInitFinished(view: View) {}

    fun populateMenu(items: List<CustomMenuItem>) {
        adapter.listener = this
        adapter.refreshList(items)

        menuItemsRv.initWithLinearAdapter(
            adapter,
            context,
            LinearLayoutManager.HORIZONTAL
        )
    }

    override fun onMenuItemPressed(model: CustomMenuItem) {
        listener?.onItemPressed(model)
    }

    interface CustomMenuViewListener {
        fun onItemPressed(itemMenu: CustomMenuItem)
    }
}