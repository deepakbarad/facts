package com.poc.facts.activities


import android.arch.lifecycle.ViewModelProviders
import android.content.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import com.poc.facts.R
import com.poc.facts.adapters.FactsRecyclerViewAdapter
import com.poc.facts.global.Constants
import com.poc.facts.models.FactNews
import com.poc.facts.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.parceler.Parcels

class MainActivity : AppCompatActivity(),SwipeRefreshLayout.OnRefreshListener {

    lateinit var factsBroadcastReceiver:BroadcastReceiver;
    lateinit var adapter: FactsRecyclerViewAdapter;
    lateinit var viewModel: MainViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val layoutManager:LinearLayoutManager = LinearLayoutManager(this);
        adapter = FactsRecyclerViewAdapter(applicationContext);
        rvFacts.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvFacts.adapter = adapter;


        factsBroadcastReceiver = object : BroadcastReceiver(){

            override fun onReceive(ctx: Context?, intent: Intent?) {

                val data = intent!!.extras[Constants.DATA];

                var factNews: FactNews = Parcels.unwrap(data as Parcelable);
                setActionBarTitle(factNews.title);
                populateFacts(viewModel.getValidFacts(factNews));
                swRefresh.isRefreshing = false;
            }
        }

        LocalBroadcastManager.getInstance(applicationContext).registerReceiver(factsBroadcastReceiver, IntentFilter(Constants.FACTS_DOWNLOADED_ACTION))

        swRefresh.setOnRefreshListener(this);
        swRefresh.setOnRefreshListener {

            getFacts();
        }

        getFacts()
    }

    fun setActionBarTitle(title:String)
    {
        this.setTitle(title);
    }

    override fun onRefresh() {

    }

    fun getFacts()
    {
        swRefresh.isRefreshing = true;
        viewModel.getFacts();
    }

    fun populateFacts(factNews: FactNews)
    {
        adapter.clear()
        adapter.addFacts(factNews.rows);
    }



    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(factsBroadcastReceiver);
    }
}