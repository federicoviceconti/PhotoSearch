package com.example.photosearch.home.adapter

import android.view.View
import android.widget.TextView
import com.example.photosearch.R
import com.example.photosearch.core.adapter.BaseAdapter
import com.example.photosearch.core.extension.enableView
import com.example.photosearch.home.model.Tag
import kotlinx.android.synthetic.main.rv_item_tag.view.*

class TagAdapter(list: List<Tag>): BaseAdapter<Tag>(list) {
    override fun onCreateHolder(itemView: View) = TagHolder(itemView)
    override fun getHolderLayout(): Int = R.layout.rv_item_tag

    var listener: TagAdapterListener?= null

    inner class TagHolder(view: View): BaseHolder(view) {
        private lateinit var nameTv: TextView
        private lateinit var selectedView: View

        override fun initViewReference(itemView: View) {
            itemView.setOnClickListener { onItemPressed(model) }

            nameTv = itemView.nameTv
            selectedView = itemView.selectedRound
        }

        override fun onModelCreated() {
            nameTv.text = model.name
            selectedView.enableView(model.isSelected)

            val style = if(model.isSelected) R.style.SerifTextView_Bold else R.style.SerifTextView
            nameTv.setTextAppearance(itemView.context, style)
        }
    }

    private fun onItemPressed(model: Tag) {
        listener?.onItemPressed(model)
    }

    interface TagAdapterListener {
        fun onItemPressed(model: Tag)
    }
}
