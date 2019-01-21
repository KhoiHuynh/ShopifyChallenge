package com.example.khoi.shopifychallenge

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_collections_detail.*
import okhttp3.*
import java.io.IOException

class CollectionsDetailActivity : AppCompatActivity() {
    companion object {
        private val TAG = "CollectionsDetailActvty"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collections_detail)

        recyclerView_details.layoutManager = LinearLayoutManager(this@CollectionsDetailActivity)
        recyclerView_details.addItemDecoration(
            DividerItemDecoration(recyclerView_details.context, DividerItemDecoration.VERTICAL))

        // Receiving the information from previous activity throuh an intent
        val collectionTitle = intent.getStringExtra(CustomViewHolder.collection_title)
        val collectionId = intent.getStringExtra(CustomViewHolder.collection_id)

        supportActionBar?.title = collectionTitle

        fetchJSON(collectionId, collectionTitle)

    }

    private fun fetchJSON(collectionId: String, collectionTitle: String){
        Log.d(CollectionsDetailActivity.TAG, "Attempting to fetch JSON")
        val url = "https://shopicruit.myshopify.com/admin/collects.json?collection_id=$collectionId&page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6"

        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().create()
                val collects = gson.fromJson(body, Models.Collects::class.java)

                val productIdArray = ArrayList<String>()
                for (collect in collects.collects){
                    productIdArray.add(collect.product_id)
                }
                fetchAllProducts(productIdArray, collects, collectionTitle)
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d(CollectionsDetailActivity.TAG, "Failed request")
            }
        })
    }

    private fun fetchAllProducts(productIdArray: ArrayList<String>, collects: Models.Collects, collectionTitle: String){
        var stringProductIds = ""
        for (collect in productIdArray){
            stringProductIds += "$collect,"
        }
        if (stringProductIds.endsWith(","))
        {
            stringProductIds = stringProductIds.substring(0, stringProductIds.length - 1)
        }

        val url = "https://shopicruit.myshopify.com/admin/products.json?ids=$stringProductIds&page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().create()
                val products = gson.fromJson(body, Models.Products::class.java)

                runOnUiThread {
                    recyclerView_details.adapter = DetailsAdapter(collects, products, collectionTitle)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d(CollectionsDetailActivity.TAG, "Failed request for all products")
            }
        })
    }
}
