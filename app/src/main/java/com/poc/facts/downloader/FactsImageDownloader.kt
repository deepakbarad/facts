package com.poc.facts.downloader

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.assist.ImageSize
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import com.poc.facts.R
import com.poc.facts.global.App

class FactsImageDownloader()
{
    companion object {

        fun loadImage(url: String?, iv: ImageView)
        {
            DownLoadTask(url,iv).execute();
        }
    }

    class DownLoadTask(urlValue: String?, imageView: ImageView):AsyncTask<Void,Void,Void>()
    {
        lateinit var iv: ImageView;
        var url:String? = null;

        init {
            this.iv = imageView;
            this.url = urlValue;
        }
        override fun doInBackground(vararg p0: Void?): Void? {

            val targetSize = ImageSize(56, 56)
            App.getImageLoaderInstance().loadImage(url, targetSize, App.getOptions(), object : SimpleImageLoadingListener() {
                override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {

                    Handler(Looper.getMainLooper()).post(Runnable { iv.setImageBitmap(loadedImage) }
                    )
                }

                override fun onLoadingFailed(imageUri: String?, view: View?, failReason: FailReason?) {
                    super.onLoadingFailed(imageUri, view, failReason)

                    Handler(Looper.getMainLooper()).post(Runnable {
                        iv.setImageBitmap(null)
                    })
                }
            })

            return null;
        }


    }
}