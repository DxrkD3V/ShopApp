package com.example.tienda.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tienda.R
import com.example.tienda.model.CartItemDto

class AdapterCarrito(
    private var dataSet: List<CartItemDto>,
    private val onSumarClick: (CartItemDto) -> Unit,
    private val onRestarClick: (CartItemDto, View) -> Unit,
    private val onItemClick: (CartItemDto, View) -> Unit
): RecyclerView.Adapter<CartView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartView {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartView(view)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: CartView, position: Int) {
        val cartItem = dataSet[position]

        Glide.with(holder.itemView.context)
            .load(cartItem.imageUrl)
            .into(holder.imagenProducto)

        holder.nombreProducto.text = cartItem.productName
        holder.precioUnidad.text = "$${cartItem.unitPrice}"
        holder.precioTotal.text = "Total: $${"%.2f".format(cartItem.subtotal)}"
        holder.cantidad.text = cartItem.quantity.toString()

        holder.btnSumar.setOnClickListener {
            onSumarClick(cartItem)
        }

        holder.btnRestar.setOnClickListener {
            onRestarClick(cartItem, holder.itemView)
        }

        holder.itemView.setOnClickListener {
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, android.R.color.darker_gray)
            )
            onItemClick(cartItem, holder.itemView)
        }
        //DEBUG
        Log.d("AdapterCarrito", "Mostrando: ${cartItem.productName}")
    }

    fun updateProductos(newDataSet: List<CartItemDto>) {
        dataSet = newDataSet
        //DEBUG
        Log.d("AdapterCarrito", "Datos nuevos: ${newDataSet.size}")
        notifyDataSetChanged()
    }
}