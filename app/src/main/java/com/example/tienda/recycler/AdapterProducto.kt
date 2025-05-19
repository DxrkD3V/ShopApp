package com.example.tienda.recycler

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tienda.AddToCart
import com.example.tienda.R
import com.example.tienda.model.ProductoDto

class AdapterProducto(
    private var dataSet: List<ProductoDto>
) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val producto = dataSet[position]

        Glide.with(holder.itemView.context)
            .load(producto.imageurl)
            .into(holder.productImage)

        holder.nombreProducto.text = producto.name
        holder.precioProducto.text = "$${producto.price}"

        holder.btnAddToCart.setOnClickListener {
            val intent = Intent(holder.itemView.context, AddToCart::class.java)
            intent.putExtra("producto", producto)
            holder.itemView.context.startActivity(intent)
        }
    }


    fun updateProductos(newDataSet: List<ProductoDto>) {
        dataSet = newDataSet
        notifyDataSetChanged()
    }
}