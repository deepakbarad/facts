package com.poc.facts.global

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.widget.Toast
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.poc.facts.R

class App : Application()
{
    lateinit var imageLoaderConfig:ImageLoaderConfiguration;

    override fun onCreate() {
        super.onCreate()

        imageLoaderConfig= ImageLoaderConfiguration.Builder(this)
                .build();



        ImageLoader.getInstance().init(imageLoaderConfig);
    }

    companion object {

        fun getImageLoaderInstance():ImageLoader
        {
            return com.nostra13.universalimageloader.core.ImageLoader.getInstance()
        }

        fun getOptions():DisplayImageOptions
        {
            val options:DisplayImageOptions = DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build()

            return options;
        }

        fun registerReceiver(ctx:Context, receiver: BroadcastReceiver,intentFilter: IntentFilter)
        {
            LocalBroadcastManager.getInstance(ctx).registerReceiver(receiver, intentFilter)
        }

        fun sendLocalBroadcastMessage(ctx:Context, intent: Intent)
        {
            LocalBroadcastManager.getInstance(ctx).sendBroadcast(intent);
        }

        fun showToast(ctx:Context, message:String)
        {
            Toast.makeText(ctx,message,Toast.LENGTH_SHORT).show()
        }
    }
}