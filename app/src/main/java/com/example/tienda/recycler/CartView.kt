package com.example.tienda.recycler

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tienda.R

class CartView(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imagenProducto: ImageView = itemView.findViewById(R.id.cartImagenProducto)
    val nombreProducto: TextView = itemView.findViewById(R.id.cartNombreProducto)
    val precioUnidad: TextView = itemView.findViewById(R.id.cartPrecioUnidad)
    val precioTotal: TextView = itemView.findViewById(R.id.cartPrecioTotal)
    val cantidad: TextView = itemView.findViewById(R.id.cartCantidad)
    val btnRestar: ImageButton = itemView.findViewById(R.id.btnRestarCantidad)
    val btnSumar: ImageButton = itemView.findViewById(R.id.btnSumarCantidad)
}
