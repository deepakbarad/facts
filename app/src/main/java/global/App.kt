package global

import android.app.Application
import android.support.v4.content.ContextCompat
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.poc.facts.R
import downloader.FactsImageDownloader

class App : Application()
{
    lateinit var imageLoaderConfig:ImageLoaderConfiguration;

    override fun onCreate() {
        super.onCreate()

        imageLoaderConfig= ImageLoaderConfiguration.Builder(this)
                .imageDownloader(FactsImageDownloader(this))
                //.memoryCache(LruMemoryCache(2 * 1024 * 1024))
                //.memoryCacheSize(2 * 1024 * 1024)
                //.diskCache(UnlimitedDiskCache(cacheDir))
                //.diskCacheSize(10 * 1024 * 1024)
                //.diskCacheFileCount(50)
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
                    .showImageForEmptyUri(R.drawable.ic_image_blank)
                    .showImageOnFail(R.drawable.ic_image_blank)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build()

            return options;
        }
    }
}