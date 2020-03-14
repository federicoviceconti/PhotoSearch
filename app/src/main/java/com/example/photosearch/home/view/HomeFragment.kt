package com.example.photosearch.home.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.photosearch.R
import com.example.photosearch.core.extension.showAndPlayAnimation
import com.example.photosearch.core.ui.BaseFragment
import com.example.photosearch.core.ui.SpannedGridLayoutManager
import com.example.photosearch.core.ui.SpannedGridLayoutManager.SpanInfo
import com.example.photosearch.customview.menu.CustomMenuItem
import com.example.photosearch.customview.menu.CustomMenuView
import com.example.photosearch.customview.photodetail.PhotoDetailView
import com.example.photosearch.customview.tag.SearchTagView
import com.example.photosearch.home.adapter.ImageAdapter
import com.example.photosearch.home.model.Photo
import com.example.photosearch.home.model.Tag
import com.example.photosearch.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.view.*
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.photosearch.core.extension.hideAndPauseAnimation
import com.example.photosearch.core.extension.initWithStaggered


class HomeFragment : BaseFragment(),
    SearchTagView.SearchTagViewListener,
    ImageAdapter.ImageAdapterListener,
    CustomMenuView.CustomMenuViewListener,
    LoadingScroll.LoadingScrollListener {

    private var imageAdapter: ImageAdapter? = null
    private var scrollPhotoListener: LoadingScroll? = null
    private lateinit var spanLookup: SpannedGridLayoutManager.GridSpanLookup

    override var layout: Int = R.layout.fragment_home

    private lateinit var photoRv: RecyclerView
    private lateinit var topMenu: CustomMenuView
    private lateinit var searchTag: SearchTagView
    private lateinit var homeIv: ImageView
    private lateinit var nestScroll: NestedScrollView
    private lateinit var lottieProgressAnimation: LottieAnimationView
    private lateinit var titleTv: TextView
    private lateinit var subtitleTv: TextView

    private lateinit var photoDetailView: PhotoDetailView
    private lateinit var homeViewModel: HomeViewModel

    override fun onInit(view: View) {
        initReference(view)
        configReference()
    }

    private fun initReference(view: View) {
        photoRv = view.photoRv
        topMenu = view.topMenu
        searchTag = view.searchTagView
        homeIv = view.homeIv
        titleTv = view.titleTv
        subtitleTv = view.subtitleTv
        nestScroll = view.nestScroll
        lottieProgressAnimation = view.lottieProgressAnimation

        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    private fun configReference() {
        spanLookup = initSpanGrid()
        searchTag.listener = this
        topMenu.listener = this
        homeIv.setOnClickListener { homeViewModel.onMenuPressed() }
        handleViewModels()
    }

    private fun handleViewModels() {
        homeViewModel.getPhotoList().observe(this, Observer {
            initPhotoRecycler(it)
        })
        homeViewModel.getTagList().observe(this, Observer { searchTag.populateView(it) })
        homeViewModel.getMenuModel().observe(this, Observer { topMenu.populateMenu(it) })
        homeViewModel.getPhotoDetail().observe(this, Observer { /*photoDetailView.populateDetail(it)*/ })
        homeViewModel.isLoading().observe(this, Observer { handleLoader(it) })
        homeViewModel.shouldOpenMenu().observe(this, Observer {
            topMenu.visibility = if(it) View.VISIBLE else View.GONE
            val icon = if(it) R.drawable.ic_close else R.drawable.ic_menu
            homeIv.setImageResource(icon)
        })
        homeViewModel.titlePage().observe(this, Observer { titleTv.text = it })
        homeViewModel.subtitlePage().observe(this, Observer {
            if(it.isNullOrEmpty()) {
                subtitleTv.visibility = View.GONE
            } else {
                subtitleTv.text = it
                subtitleTv.visibility = View.VISIBLE
            }
        })
        homeViewModel.showTagList().observe(this, Observer { searchTag.visibility = if(it) View.VISIBLE else View.GONE })

        homeViewModel.isContextualLoading().observe(this, Observer {
            if(it) {
                lottieProgressAnimation.showAndPlayAnimation()
                scrollPhotoListener?.shouldLoad = false
            } else {
                lottieProgressAnimation.hideAndPauseAnimation()
                scrollPhotoListener?.shouldLoad = true
            }
        })
    }

    private fun initPhotoRecycler(items: List<Photo>) {
        if(imageAdapter == null) {
            imageAdapter = ImageAdapter(items)
            imageAdapter?.listener = this

            val layoutManager = photoRv.initWithStaggered(imageAdapter!!, 2, StaggeredGridLayoutManager.VERTICAL)
            photoRv.isNestedScrollingEnabled = true
            photoRv.setHasFixedSize(true)

            scrollPhotoListener = LoadingScroll(layoutManager)
            if(scrollPhotoListener?.listener == null) {
                scrollPhotoListener?.listener = this
                nestScroll.setOnScrollChangeListener(scrollPhotoListener)
            }
        } else {
            imageAdapter?.refreshList(items)
        }
    }

    private fun initSpanGrid(): SpannedGridLayoutManager.GridSpanLookup {
        return SpannedGridLayoutManager.GridSpanLookup {
            return@GridSpanLookup if (it % 6 == 0 || it % 6 == 4) {
                SpanInfo(2, 2)
            } else {
                SpanInfo(1, 1)
            }
        }
    }

    override fun onTagPressed(tag: Tag) {
        homeViewModel.onTagPressed(tag)
    }

    override fun onPhotoPressed(photo: Photo) {
        homeViewModel.onPhotoPressed(photo)
    }

    override fun onPhotoLiked(photo: Photo?) {
        homeViewModel.onPhotoLiked(photo)
    }

    override fun onItemPressed(itemMenu: CustomMenuItem) {
        homeViewModel.onMenuItemPressed(itemMenu)
    }

    override fun loadMore() {
        homeViewModel.onScrollPhotoEnded()
    }
}
