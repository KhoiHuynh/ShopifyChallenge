package com.example.khoi.shopifychallenge

import android.graphics.Color
import android.media.Image
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_collections_list.*
import okhttp3.*
import java.io.IOException
import java.net.URL
import java.util.*
import com.google.gson.GsonBuilder
import com.google.gson.Gson
import android.support.v7.widget.DividerItemDecoration





class CollectionsListActivity : AppCompatActivity() {
    companion object {
        private val TAG = "CollectionsListActivity"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collections_list)

//        val shopifyGreen = resources.getColor(R.color.shopifyGreen, theme)
//        recyclerView_collections.setBackgroundColor(shopifyGreen)

        // our adapter for the recycler view
        recyclerView_collections.layoutManager = LinearLayoutManager(this@CollectionsListActivity)
        recyclerView_collections.addItemDecoration(DividerItemDecoration(recyclerView_collections.context, DividerItemDecoration.VERTICAL))

        fetchJSON()
    }

    private fun fetchJSON(){
        Log.d(CollectionsListActivity.TAG, "Attempting to fetch JSON")
        val url = "https://shopicruit.myshopify.com/admin/custom_collections.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6"

        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object: Callback{
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                Log.d(CollectionsListActivity.TAG, "$body")

                val gson = GsonBuilder().create()
                val collectionFeed = gson.fromJson(body, CollectionFeed::class.java)
                runOnUiThread {
                    recyclerView_collections.adapter = CollectionsAdapter(collectionFeed)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d(CollectionsListActivity.TAG, "Failed request")
            }
        })
    }

    class CollectionFeed(val custom_collections: List<Collection>)

    class Collection(val id: String, val title: String, val image: Image)

    class Image(val src: String)
}

//{
//    "id": 68424466488,
//    "handle": "aerodynamic-collection",
//    "title": "Aerodynamic collection",
//    "updated_at": "2018-12-17T13:51:58-05:00",
//    "body_html": "The top of the line of aerodynamic products all in the same collection.",
//    "published_at": "2018-12-17T13:50:07-05:00",
//    "sort_order": "best-selling",
//    "template_suffix": "",
//    "published_scope": "web",
//    "admin_graphql_api_id": "gid:\/\/shopify\/Collection\/68424466488",
//    "image": {
//    "created_at": "2018-12-17T13:51:57-05:00",
//    "alt": null,
//    "width": 300,
//    "height": 300,
//    "src": "https:\/\/cdn.shopify.com\/s\/files\/1\/1000\/7970\/collections\/Aerodynamic_20Cotton_20Keyboard_grande_b213aa7f-9a10-4860-8618-76d5609f2c19.png?v=1545072718"
//}
