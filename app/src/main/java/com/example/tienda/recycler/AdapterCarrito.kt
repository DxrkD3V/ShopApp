package com.example.tienda.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tienda.R
import com.example.tienda.model.ProductoData

class AdapterCarrito(
    private var dataSet: List<ProductoData>,
    private val onSumarClick: (ProductoData) -> Unit,
    private val onRestarClick: (ProductoData) -> Unit,
    private val onItemClick: (ProductoData) -> Unit
) : RecyclerView.Adapter<CartView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartView {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartView(view)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: CartView, position: Int) {
        val producto = dataSet[position]

        Glide.with(holder.itemView.context)
            .load(producto.imageurl)
            .into(holder.imagenProducto)

        holder.nombreProducto.text = producto.name
        holder.precioUnidad.text = "$${producto.price}"
        val total = producto.price * producto.units
        holder.precioTotal.text = "Total: $${"%.2f".format(total)}"

        holder.cantidad.text = producto.units.toString()

        holder.btnSumar.setOnClickListener {
            onSumarClick(producto)
        }

        holder.btnRestar.setOnClickListener {
            onRestarClick(producto)
        }

        holder.itemView.setBackgroundColor(
            ContextCompat.getColor(holder.itemView.context, android.R.color.white)
        )

        holder.itemView.setOnClickListener {
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, android.R.color.darker_gray)
            )
            onItemClick(producto)
        }
    }

    fun updateProductos(newDataSet: List<ProductoData>) {
        dataSet = newDataSet
        notifyDataSetChanged()
    }
}
