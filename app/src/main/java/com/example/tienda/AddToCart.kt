package com.example.tienda

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.tienda.model.ProductoDto
import com.example.tienda.viewholder.AddToCartViewHolder
import com.example.tienda.viewmodel.AddCartViewModel

class AddToCart : AppCompatActivity() {

    private lateinit var producto: ProductoDto
    private lateinit var viewModel: AddCartViewModel
    private lateinit var viewHolder: AddToCartViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_cart)

        viewHolder = AddToCartViewHolder(findViewById(android.R.id.content))

        producto = intent.getSerializableExtra("producto") as ProductoDto
        viewModel = ViewModelProvider(this)[AddCartViewModel::class.java]

        viewHolder.nombre.text = producto.name
        viewHolder.precio.text = "${producto.price} €"
        Glide.with(this).load(producto.imageurl).into(viewHolder.imagen)

        viewHolder.btnConfirmar.setOnClickListener {
            val cantidad = viewHolder.cantidadInput.text.toString().toIntOrNull() ?: 1
            viewModel.anadirAlCarrito(producto.id.toLong(), cantidad)
        }

        viewModel.addSuccess.observe(this) {
            if (it) {
                Toast.makeText(this, "Producto añadido al carrito", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al añadir al carrito", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }
}
