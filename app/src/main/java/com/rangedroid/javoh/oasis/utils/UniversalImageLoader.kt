package com.rangedroid.javoh.oasis.utils

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.assist.ImageScaleType
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener
import com.rangedroid.javoh.oasis.R
import java.lang.ref.WeakReference

class UniversalImageLoader (context: Context) {

    private val defaultImage: Int = R.drawable.ic_default_img
    private var mContext: WeakReference<Context> = WeakReference(context)


    fun getConfig(): ImageLoaderConfiguration{
        val defaultOptions: DisplayImageOptions = DisplayImageOptions.Builder()
            .showImageOnLoading(defaultImage)
            .showImageForEmptyUri(defaultImage)
            .showImageOnFail(defaultImage)
            .considerExifParams(true)
            .cacheOnDisk(true).cacheInMemory(true)
            .cacheOnDisk(true).resetViewBeforeLoading(true)
            .imageScaleType(ImageScaleType.EXACTLY)
            .displayer(FadeInBitmapDisplayer(300)).build()

        return ImageLoaderConfiguration.Builder(mContext.get())
            .defaultDisplayImageOptions(defaultOptions)
            .memoryCache(WeakMemoryCache())
            .diskCacheSize(100 * 1024 * 1024).build()
    }

    companion object{
        fun setImage(imgURL: String, image: ImageView, progressBar: ProgressBar?){
            val imageLoader: WeakReference<ImageLoader> = WeakReference(ImageLoader.getInstance())
            imageLoader.get()?.displayImage(imgURL, image, object: ImageLoadingListener{
                override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                    progressBar?.visibility = View.GONE
                }

                override fun onLoadingStarted(imageUri: String?, view: View?) {
                    progressBar?.visibility = View.VISIBLE
                }

                override fun onLoadingCancelled(imageUri: String?, view: View?) {
                    progressBar?.visibility = View.GONE
                }

                override fun onLoadingFailed(imageUri: String?, view: View?, failReason: FailReason?) {
                    progressBar?.visibility = View.GONE
                }

            })
        }
    }

}