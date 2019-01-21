package com.example.khoi.shopifychallenge

class Models {
    //-- Models for Collections --
    class CollectionFeed(val custom_collections: List<Collection>)

    class Collection(val id: String, val title: String, val image: Image)

    class Collects(val collects: List<Collect>)

    class Collect(val product_id: String, val title: String)

    //-- Models for products --
    class Products(val products: ArrayList<Product>)

    class Product (val title: String, val variants: ArrayList<Variant>, val image: Image)

    class Variant (val title: String)

    class Image (val src: String)
}