package com.example.tienda

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tienda.model.CartItemDto
import com.example.tienda.model.MainState
import com.example.tienda.recycler.AdapterCarrito
import kotlinx.coroutines.launch

class Carrito : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterCarrito
    private lateinit var mainState: MainState

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_carrito, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerCarrito)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = AdapterCarrito(
            dataSet = emptyList(),
            onSumarClick = { cartItem -> sumarProducto(cartItem) },
            onRestarClick = { cartItem, itemView -> restarProducto(cartItem, itemView) },
            onItemClick = { cartItem, itemView -> mostrarDialogoEliminar(cartItem, itemView) }
        )

        recyclerView.adapter = adapter
        mainState = MainState(requireContext())

        cargarCarrito()
    }

    private fun cargarCarrito() {
        lifecycleScope.launch {
            try {
                val cartResponse = mainState.recuperarCarrito()
                adapter.updateProductos(cartResponse.items)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error al cargar el carrito", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sumarProducto(cartItem: CartItemDto) {
        lifecycleScope.launch {
            try {
                val exito = mainState.anadirProductoCarrito(cartItem.productId, 1)
                if (exito) cargarCarrito()
                else Toast.makeText(requireContext(), "Error al añadir unidad", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error al añadir producto", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun restarProducto(cartItem: CartItemDto, itemView: View) {
        if (cartItem.quantity > 1) {
            lifecycleScope.launch {
                try {
                    val exito = mainState.anadirProductoCarrito(cartItem.productId, -1)
                    if (exito) cargarCarrito()
                    else Toast.makeText(requireContext(), "Error al restar unidad", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error al restar producto", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            mostrarDialogoEliminar(cartItem, itemView)
        }
    }

    private fun mostrarDialogoEliminar(cartItem: CartItemDto, itemView: View) {
        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Eliminar del carrito")
        builder.setMessage("¿Eliminar ${cartItem.productName} del carrito?")
        builder.setPositiveButton("Sí") { _, _ ->
            lifecycleScope.launch {
                try {
                    val exito = mainState.eliminarProductoCarrito(cartItem.productId)
                    if (exito) {
                        cargarCarrito()
                        itemView.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.white))
                    }
                    else Toast.makeText(requireContext(), "Error al eliminar producto", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error al eliminar producto", Toast.LENGTH_SHORT).show()
                }
            }
        }
        builder.setNegativeButton("No") { _, _ ->
            itemView.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.white))
        }
        builder.show()
    }
}
