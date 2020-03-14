package com.example.photosearch.customview.tag

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photosearch.R
import com.example.photosearch.core.extension.initWithLinearAdapter
import com.example.photosearch.core.ui.BaseCustomView
import com.example.photosearch.home.adapter.TagAdapter
import com.example.photosearch.home.model.Tag
import kotlinx.android.synthetic.main.component_tag_search_view.view.*

class SearchTagView: BaseCustomView, TagAdapter.TagAdapterListener {
    var listener: SearchTagViewListener? = null
    lateinit var adapter: TagAdapter

    override fun getLayout(): Int = R.layout.component_tag_search_view

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onInitFinished(view: View) {
        adapter = TagAdapter(mutableListOf())
        adapter.listener = this

        view.tagRv.initWithLinearAdapter(adapter, context, LinearLayoutManager.HORIZONTAL)
    }

    fun populateView(tags: List<Tag>) {
        adapter.refreshList(tags)

        if(tags.isEmpty()) { openSearchBar() }
        else { closeSearchBar() }
    }

    private fun openSearchBar() {
        tagRv.visibility = View.GONE
        searchEt.visibility = View.VISIBLE
    }

    private fun closeSearchBar() {
        tagRv.visibility = View.VISIBLE
        searchEt.visibility = View.GONE
    }

    override fun onItemPressed(model: Tag) { listener?.onTagPressed(model) }

    interface SearchTagViewListener {
        fun onTagPressed(tag: Tag)
    }
}