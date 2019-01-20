package com.example.khoi.shopifychallenge

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.collection_row.view.*

private val TAG = "123"
class CollectionsAdapter(private val collectionFeed: CollectionsListActivity.CollectionFeed): RecyclerView.Adapter<CustomViewHolder>() {


    // this basicly creates the cells for our recyclerView (list)
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cell = layoutInflater.inflate(R.layout.collection_row, parent,false)
        return CustomViewHolder(cell)
    }

    override fun onBindViewHolder(viewHoler: CustomViewHolder, index: Int) {
        val collectionAtIndex = collectionFeed.custom_collections[index]
        val collectionTitle = collectionAtIndex.title
        viewHoler.view.textView_collection_title.text = collectionTitle

        //this gets passed in the constructor of 'CustomViewHolder' Class
        viewHoler.collection = collectionAtIndex
    }

    override fun getItemCount(): Int {
        return collectionFeed.custom_collections.count()
    }
}

class CustomViewHolder(
        val view : View, var collection: CollectionsListActivity.Collection? = null): RecyclerView.ViewHolder(view){

    companion object {
        private val TAG = "CustomViewHolder"
        val collection_title = "collection_title"
        val collection_id = "collection_id"
    }
    init {
        view.setOnClickListener {
            Log.d(CustomViewHolder.TAG, "Clicked a row")
            val intent = Intent(view.context, CollectionsDetailActivity::class.java)
            intent.putExtra(collection_title, collection?.title)
            intent.putExtra(collection_id, collection?.id)

            view.context.startActivity(intent)
        }
    }
}