package activities

import adapters.FactsRecyclerViewAdapter
import android.content.*
import android.graphics.drawable.GradientDrawable
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.poc.facts.R
import downloader.FactsDataDownloader
import global.Constants
import kotlinx.android.synthetic.main.activity_main.*
import models.FactNews
import org.parceler.Parcels

class MainActivity : AppCompatActivity() {

    lateinit var factsBroadcastReceiver:BroadcastReceiver;
    lateinit var adapter: FactsRecyclerViewAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager:LinearLayoutManager = LinearLayoutManager(this);
        adapter = FactsRecyclerViewAdapter(applicationContext);
        rvFacts.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvFacts.adapter = adapter;


        factsBroadcastReceiver = object : BroadcastReceiver(){

            override fun onReceive(ctx: Context?, intent: Intent?) {

                val data = intent!!.extras[Constants.DATA];

                var factNews: FactNews = Parcels.unwrap(data as Parcelable);
                populateFacts(factNews);
            }
        }

        LocalBroadcastManager.getInstance(applicationContext).registerReceiver(factsBroadcastReceiver, IntentFilter(Constants.FACTS_DOWNLOADED_ACTION))
        DownloadTask(this.applicationContext).execute(Constants.FACTS_URL);
    }

    fun populateFacts(factNews: FactNews)
    {
        adapter.clear()
        adapter.addFacts(factNews.rows);
    }

    class DownloadTask(ctx:Context):AsyncTask<String,Void,Void>()
    {
        lateinit var ctx:Context;

        init {
            this.ctx = ctx;
        }

        override fun doInBackground(vararg url: String): Void? {

            FactsDataDownloader(ctx,url.get(0)).start(ctx);
            return null;

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(factsBroadcastReceiver);
    }
}
