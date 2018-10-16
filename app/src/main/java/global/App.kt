package global

import android.app.Application
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import downloader.FactsImageDownloader

class App : Application()
{
    lateinit var imageLoaderConfig:ImageLoaderConfiguration;

    override fun onCreate() {
        super.onCreate()

        imageLoaderConfig= ImageLoaderConfiguration.Builder(this)
                .imageDownloader(FactsImageDownloader(this))
                .build();

        ImageLoader.getInstance().init(imageLoaderConfig);
    }
}