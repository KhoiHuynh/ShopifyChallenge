package com.example.khoi.shopifychallenge

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_row.view.*

class DetailsAdapter(private val collects: Models.Collects,
                     private val allProducts: Models.Products,
                     collectionTitle: String): RecyclerView.Adapter<CustomDetailsViewHolder>() {

    val collectTitle = "Collection: $collectionTitle"
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomDetailsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cell = layoutInflater.inflate(R.layout.detail_row, parent,false)
        return CustomDetailsViewHolder(cell)
    }
    override fun getItemCount(): Int {
        return collects.collects.count()
    }

    override fun onBindViewHolder(viewHolder: CustomDetailsViewHolder, index: Int) {

        val productName = allProducts.products[index].title
        val availableInventory = "Inventory: " + allProducts.products[index].variants.size.toString()
        val collectionImageUrl: Uri = Uri.parse(allProducts.products[index].image.src)
        val collectionThumbNail = viewHolder.view.imageView_product

        viewHolder.view.textView_product_name.text = productName
        viewHolder.view.textView_available_inventory.text = availableInventory
        viewHolder.view.textView_collection_title.text = collectTitle
        Picasso.get().load(collectionImageUrl).into(collectionThumbNail)
    }
}

class CustomDetailsViewHolder(val view : View): RecyclerView.ViewHolder(view)