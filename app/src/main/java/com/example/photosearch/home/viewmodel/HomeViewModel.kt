package com.example.photosearch.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.photosearch.customview.menu.CustomMenuItem
import com.example.photosearch.customview.menu.MenuItem
import com.example.photosearch.database.PhotoDao
import com.example.photosearch.database.TagDao
import com.example.photosearch.home.model.Photo
import com.example.photosearch.home.model.PhotoDetail
import com.example.photosearch.home.model.Tag
import com.example.photosearch.home.repository.HomeRepository
import com.example.photosearch.services.UnsplashService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    val app: Application,
    tagDao: TagDao,
    photoDao: PhotoDao,
    unsplashService: UnsplashService
): AndroidViewModel(app) {
    private lateinit var selectedMenu: CustomMenuItem
    private var indexStart = 0

    private val photoList: MutableLiveData<MutableList<Photo>> = MutableLiveData()
    fun getPhotoList() = photoList

    private val tagList: MutableLiveData<MutableList<Tag>> = MutableLiveData()
    fun getTagList() = tagList

    private val menuList: MutableLiveData<MutableList<CustomMenuItem>> = MutableLiveData()
    fun getMenuModel() = menuList

    private val photoDetail: MutableLiveData<PhotoDetail> = MutableLiveData()
    fun getPhotoDetail() = photoDetail

    private val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    fun isLoading() = isLoading

    private val contextualLoader: MutableLiveData<Boolean> = MutableLiveData(false)
    fun isContextualLoading() = contextualLoader

    private val menuOpened: MutableLiveData<Boolean> = MutableLiveData()
    fun shouldOpenMenu() = menuOpened

    private val titlePage: MutableLiveData<String> = MutableLiveData()
    fun titlePage() = titlePage

    private val subtitlePage: MutableLiveData<String> = MutableLiveData()
    fun subtitlePage() = subtitlePage

    private val showTags: MutableLiveData<Boolean> = MutableLiveData()
    fun showTagList() = showTags

    private var homeRepository: HomeRepository = HomeRepository(photoDao, tagDao, unsplashService)

    init {
        loadMenu()
        loadTags()
        loadPhotos(false)

        setMenuDefault()
    }

    private fun setMenuDefault() {
        menuOpened.postValue(false)

        val menuItem = menuList.value?.first()
        selectedMenu = menuItem!!

        initMenuItem(menuItem)
    }

    private fun initMenuItem(menuItem: CustomMenuItem?) {
        val title = menuItem?.itemMenu?.getTitle(app)

        if(title != null) {
            titlePage.postValue(title)
            subtitlePage.postValue(menuItem.itemMenu.getSubtitle(app))
        }
    }

    private fun loadMenu() {
        val home = CustomMenuItem(MenuItem.DISCOVER)
        val favorite = CustomMenuItem(MenuItem.FAVORITE)
        val menuItems = mutableListOf(home, favorite)
        menuList.value = menuItems.toMutableList()
    }

    private fun loadTags() {
        homeRepository.insertStandardTag()
        this.tagList.value = homeRepository.getAllTags().toMutableList()
    }

    private fun loadPhotos(fromRefresh: Boolean) {
        if(!fromRefresh) {
            isLoading.value = true
        } else {
            contextualLoader.value = true
        }

        val photoDisposable = homeRepository.getPhotosByTag(indexStart, getSelectedTag())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ it ->
                isLoading.value = false
                contextualLoader.value = false

                val imagesToAdd = it.filter { image -> image.url.regular.isNotEmpty() }.toMutableList()
                imagesToAdd.forEach {
                    val photo = homeRepository.photoDao.getPhoto(it.id)
                    if(photo != null) { it.isFavourite = photo.isFavourite }
                }

                if(fromRefresh) {
                    photoList.value?.addAll(imagesToAdd)
                    photoList.postValue(photoList.value)
                } else {
                    photoList.value = imagesToAdd
                }

                incrementIndex()
            }, {
                isLoading.value = false
                contextualLoader.value = false
            })
    }

    private fun getSelectedTag(): String {
        val tag = tagList.value?.first { it.isSelected }
        return tag?.name.orEmpty()
    }

    private fun incrementIndex() {
        indexStart += UnsplashService.PER_PAGE
    }

    fun onTagPressed(tag: Tag) {
        selectSingleTag(tag)
        tagList.postValue(tagList.value)

        resetIndex()
        loadPhotos(false)
    }

    private fun resetIndex() { indexStart = 0 }

    private fun selectSingleTag(tag: Tag) {
        var found = false
        tagList.value?.forEach {
            if(it.name == tag.name && it.isSelected) {
                it.isSelected = true
            } else if (it.name == tag.name && !found) {
                it.isSelected = !it.isSelected
                found = true
            } else {
                it.isSelected = false
            }
        }
    }

    fun onMenuItemPressed(item: CustomMenuItem) {
        titlePage.postValue(item.itemMenu.getTitle(getApplication()))
        menuOpened.postValue(false)
        handleMenuTapped(item)

        this.selectedMenu = item
    }

    fun onMenuPressed() {
        val openMenu = !menuOpened.value!!
        menuOpened.postValue(openMenu)

        if(openMenu) { loadMenu() }
    }

    private fun handleMenuTapped(item: CustomMenuItem) {
        if(item.itemMenu == MenuItem.FAVORITE) {
            onFavoriteMenuChoose()
        } else if(item.itemMenu == MenuItem.DISCOVER) {
            onDiscoverMenuChoose()
        }

        initMenuItem(item)
    }

    private fun onDiscoverMenuChoose() {
        resetIndex()
        loadPhotos(false)

        showTags.postValue(true)
    }

    private fun onFavoriteMenuChoose() {
        val photos = homeRepository.photoDao.getAllFavouritePhotos()
        photoList.postValue(photos.toMutableList())

        showTags.postValue(false)
    }

    fun onPhotoPressed(photo: Photo) {
        val foundPhoto = photoList.value?.first { it.id == photo.id }
        photoDetail.postValue(foundPhoto?.let { PhotoDetail(false, it) })
    }

    fun onScrollPhotoEnded() {
        if(selectedMenu.itemMenu != MenuItem.FAVORITE)
            loadPhotos(true)
    }

    fun onPhotoLiked(photo: Photo?) {
        val found = photoList.value?.first { it.id == photo?.id }

        if(found != null) {
            found.isFavourite = !found.isFavourite

            if(found.isFavourite) {
                homeRepository.photoDao.insertPhoto(photo!!)
            } else {
                homeRepository.photoDao.deletePhoto(photo!!)
            }
        }

        photoList.postValue(photoList.value)
    }
}