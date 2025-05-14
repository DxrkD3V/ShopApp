package com.example.tienda

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.tienda.model.MainState
import com.example.tienda.model.ProductoDto
import com.example.tienda.utils.TokenManager
import kotlinx.coroutines.launch

class AddToCart : AppCompatActivity() {

    private lateinit var producto: ProductoDto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_cart)

        producto = intent.getSerializableExtra("producto") as ProductoDto

        findViewById<TextView>(R.id.nombreProductoCarrito).text = producto.name
        findViewById<TextView>(R.id.precioProductoCarrito).text = "${producto.price} €"
        Glide.with(this).load(producto.imageurl).into(findViewById(R.id.imagenProductoCarrito))

        val cantidadInput = findViewById<EditText>(R.id.inputCantidad)

        findViewById<Button>(R.id.btnConfirmarCarrito).setOnClickListener {
            val cantidad = cantidadInput.text.toString().toIntOrNull() ?: 1
            aniadirProductoAlCarrito(producto.id, cantidad)
        }
    }

    private fun aniadirProductoAlCarrito(idProducto: Int, cantidad: Int) {
        val mainState = MainState(this)
        val token = TokenManager.getAccessToken(this)

        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "No estás logueado.", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            val success = mainState.anadirProductoCarrito(idProducto.toLong(), cantidad)

            if (success) {
                Toast.makeText(this@AddToCart, "Producto añadido al carrito", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@AddToCart, "Error al añadir al carrito", Toast.LENGTH_SHORT).show()
            }
        }
        this.finish()
    }
}

