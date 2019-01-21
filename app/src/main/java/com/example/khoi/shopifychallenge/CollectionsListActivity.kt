package com.example.khoi.shopifychallenge

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_collections_list.*
import okhttp3.*
import java.io.IOException
import com.google.gson.GsonBuilder
import android.support.v7.widget.DividerItemDecoration

class CollectionsListActivity : AppCompatActivity() {
    companion object {
        private val TAG = "CollectionsListActivity"
        private const val URL = "https://shopicruit.myshopify.com/admin/custom_collections.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collections_list)
        supportActionBar?.title = "Awesome Collection of Stuff"

        // our adapter for the recycler view
        recyclerView_collections.layoutManager = LinearLayoutManager(this@CollectionsListActivity)
        recyclerView_collections.addItemDecoration(DividerItemDecoration(
            recyclerView_collections.context, DividerItemDecoration.VERTICAL))

        fetchJSON()
    }

    private fun fetchJSON(){
        Log.d(CollectionsListActivity.TAG, "Attempting to fetch JSON")

        val client = OkHttpClient()
        val request = Request.Builder().url(URL).build()
        client.newCall(request).enqueue(object: Callback{
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().create()
                val collectionFeed = gson.fromJson(body, Models.CollectionFeed::class.java)
                runOnUiThread {
                    recyclerView_collections.adapter = CollectionsAdapter(collectionFeed)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d(CollectionsListActivity.TAG, "Failed request")
            }
        })
    }
}
