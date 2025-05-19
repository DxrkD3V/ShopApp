package com.example.tienda.recycler

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tienda.R

class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val productImage: ImageView = itemView.findViewById(R.id.imagenProducto)
    val nombreProducto: TextView = itemView.findViewById(R.id.nombreProducto)
    val precioProducto: TextView = itemView.findViewById(R.id.precioProducto)
    val btnAddToCart: ImageButton = itemView.findViewById(R.id.btnAddToCart)
}