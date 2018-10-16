package viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Context
import android.os.AsyncTask
import downloader.FactsDataDownloader
import global.Constants

class MainViewModel(application: Application) : AndroidViewModel(application)
{
    fun getFacts()
    {
        DownloadTask(getApplication()).execute(Constants.FACTS_URL);
    }

    class DownloadTask(ctx: Context): AsyncTask<String, Void, Void>()
    {
        lateinit var ctx: Context;

        init {
            this.ctx = ctx;
        }

        override fun doInBackground(vararg url: String): Void? {

            FactsDataDownloader(ctx,url.get(0)).start(ctx);
            return null;

        }
    }
}