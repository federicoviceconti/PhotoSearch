package com.example.photosearch.customview.menu

import android.view.View
import com.example.photosearch.R
import com.example.photosearch.core.adapter.BaseAdapter
import com.example.photosearch.core.extension.getDrawableByName
import com.example.photosearch.home.viewmodel.getIcon
import com.example.photosearch.home.viewmodel.getTitle
import kotlinx.android.synthetic.main.rv_menu_item.view.*

class MenuAdapter(list: List<CustomMenuItem>): BaseAdapter<CustomMenuItem>(list) {
    var listener: MenuAdapterListener? = null

    override fun getHolderLayout(): Int = R.layout.rv_menu_item

    override fun onCreateHolder(itemView: View): BaseHolder = MenuHolder(itemView)

    inner class MenuHolder(view: View): BaseHolder(view) {
        override fun initViewReference(itemView: View) {
            itemView.setOnClickListener { onItemMenuPressed(model) }
        }

        override fun onModelCreated() {
            val icon = itemView.context.getDrawableByName(model.itemMenu.getIcon())
            itemView.menuItemTv.text = model.itemMenu.getTitle(itemView.context)
            itemView.menuItemTv.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
        }

    }

    private fun onItemMenuPressed(model: CustomMenuItem) {
        listener?.onMenuItemPressed(model)
    }

    interface MenuAdapterListener {
        fun onMenuItemPressed(model: CustomMenuItem)
    }
}