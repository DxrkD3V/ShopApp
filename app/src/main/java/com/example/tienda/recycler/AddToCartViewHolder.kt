package com.example.tienda.viewholder

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.tienda.R

class AddToCartViewHolder(view: View) {
    val nombre: TextView = view.findViewById(R.id.nombreProductoCarrito)
    val precio: TextView = view.findViewById(R.id.precioProductoCarrito)
    val imagen: ImageView = view.findViewById(R.id.imagenProductoCarrito)
    val cantidadInput: EditText = view.findViewById(R.id.inputCantidad)
    val btnConfirmar: Button = view.findViewById(R.id.btnConfirmarCarrito)
}
