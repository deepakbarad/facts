package com.poc.facts.downloader

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.poc.facts.global.App
import com.poc.facts.global.Constants
import com.poc.facts.models.FactNews
import org.json.JSONObject
import org.parceler.Parcel
import org.parceler.Parcels


class FactsDataDownloader(context: Context, uriValue: String) {
    lateinit var url: String


    companion object {
        lateinit var requestQueue: RequestQueue
    }


    init {
        requestQueue = Volley.newRequestQueue(context)
        url = uriValue
    }

    fun start(ctx:Context) {

        val gson:Gson = GsonBuilder().create()

        val request = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener<JSONObject> { response ->
                    val news:String = response.toString()
                    val factNews:FactNews = gson.fromJson(news, FactNews::class.java)
                    App.sendLocalBroadcastMessage(ctx,getDownloadActionIntent(factNews))
                },
                Response.ErrorListener {
                    App.sendLocalBroadcastMessage(ctx,getDownloadFailedActionIntent("Unable to fetch data!!"))
                })

        requestQueue.add(request)
        requestQueue.start()
    }

    private fun getDownloadActionIntent(factNews: FactNews): Intent {
        var intent: Intent = Intent(Constants.FACTS_DOWNLOADED_ACTION)
        intent.putExtra(Constants.DATA, Parcels.wrap(factNews))
        return intent
    }

    private fun getDownloadFailedActionIntent(message:String): Intent {
        var intent: Intent = Intent(Constants.FACTS_DOWNLOAD_FAILED_ACTION)
        intent.putExtra(Constants.DATA, message)
        return intent
    }

}