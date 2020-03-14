package com.example.photosearch.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.photosearch.HomeActivity
import com.example.photosearch.dagger.ViewModelFactory
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment: Fragment() {
    abstract var layout: Int

    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layout, container, false)
        onInit(view)
        return view
    }

    abstract fun onInit(view: View)

    fun handleLoader(show: Boolean) { if(show) showLoader() else hideLoader() }
    private fun showLoader() { (activity as HomeActivity).showLoader() }
    private fun hideLoader() { (activity as HomeActivity).hideLoader() }
}