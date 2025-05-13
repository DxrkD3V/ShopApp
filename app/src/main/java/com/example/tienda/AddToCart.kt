package com.example.tienda

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.tienda.model.ProductoData

class AddToCart : AppCompatActivity() {

    private lateinit var producto: ProductoData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_cart)

        producto = intent.getSerializableExtra("producto") as ProductoData

        findViewById<TextView>(R.id.nombreProductoCarrito).text = producto.name
        findViewById<TextView>(R.id.precioProductoCarrito).text = "${producto.price} €"
        Glide.with(this).load(producto.imageurl).into(findViewById(R.id.imagenProductoCarrito))

        val cantidadInput = findViewById<EditText>(R.id.inputCantidad)

        findViewById<Button>(R.id.btnConfirmarCarrito).setOnClickListener {
            val cantidad = cantidadInput.text.toString().toIntOrNull() ?: 1

            // TODO: Llamada a tu API
            añadirProductoAlCarrito(producto.id, cantidad)
        }
    }

    private fun añadirProductoAlCarrito(idProducto: Int, cantidad: Int) {
        // Aquí haces la llamada a tu API DWES
        Toast.makeText(this, "Añadido $cantidad x ${producto.name} al carrito", Toast.LENGTH_SHORT).show()
        finish()
    }
}
