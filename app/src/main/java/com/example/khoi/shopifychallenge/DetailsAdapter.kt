package com.example.khoi.shopifychallenge

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.detail_row.view.*


//val productIdArray = ArrayList<String>()

class DetailsAdapter(private val products: CollectionsDetailActivity.Products): RecyclerView.Adapter<CustomDetailsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomDetailsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cell = layoutInflater.inflate(R.layout.detail_row, parent,false)
        return CustomDetailsViewHolder(cell)
    }

    override fun getItemCount(): Int {
        return products.collects.count()
    }

    override fun onBindViewHolder(viewHoler: CustomDetailsViewHolder, index: Int) {
        val productId = products.collects[index].product_id
//        productIdArray.add(productId)
        viewHoler.view.textView_product_name.text = productId
    }
}

class CustomDetailsViewHolder(val view : View): RecyclerView.ViewHolder(view){
    companion object {
        private val TAG = "CustomDetailsViewHolder"
    }
//    init {
//        for (i in productIdArray) {
//            println("hello"+i)
//        }
//    }

}