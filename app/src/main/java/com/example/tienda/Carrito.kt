package com.example.tienda

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tienda.model.AddItemCartDTO
import com.example.tienda.model.MainState
import com.example.tienda.model.ProductoData
import com.example.tienda.recycler.AdapterCarrito
import kotlinx.coroutines.launch

class Carrito : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterCarrito
    private lateinit var mainState: MainState

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_carrito, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerCarrito)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = AdapterCarrito(emptyList(), { producto ->
            sumarProducto(producto)
        }, { producto ->
            restarProducto(producto)
        }) { producto ->
            mostrarDialogoEliminar(producto)
        }

        recyclerView.adapter = adapter

        mainState = MainState(requireContext())

        lifecycleScope.launch {
            try {
                val carrito = mainState.recuperarCarrito()
                adapter.updateProductos(carrito)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error al cargar el carrito", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sumarProducto(producto: ProductoData) {
        lifecycleScope.launch {
            try {
                val dto = AddItemCartDTO(productId = producto.id.toLong(), addUnits = 1)
                val exito = mainState.anadirProductoCarrito(dto)
                if (exito) {
                    val actualizado = mainState.recuperarCarrito()
                    adapter.updateProductos(actualizado)
                } else {
                    Toast.makeText(requireContext(), "Error al añadir unidad", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error al añadir producto", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun restarProducto(producto: ProductoData) {
        if (producto.units > 1) {
            lifecycleScope.launch {
                try {
                    val dto = AddItemCartDTO(productId = producto.id.toLong(), addUnits = -1)
                    val exito = mainState.anadirProductoCarrito(dto)
                    if (exito) {
                        val actualizado = mainState.recuperarCarrito()
                        adapter.updateProductos(actualizado)
                    } else {
                        Toast.makeText(requireContext(), "Error al restar unidad", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error al restar producto", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "La cantidad no puede ser menor que 1", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mostrarDialogoEliminar(producto: ProductoData) {
        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Eliminar del carrito")
        builder.setMessage("¿Eliminar ${producto.name} del carrito?")
        builder.setPositiveButton("Sí") { _, _ ->
            lifecycleScope.launch {
                try {
                    mainState.eliminarProductoCarrito(AddItemCartDTO(productId = producto.id.toLong()))
                    val actualizado = mainState.recuperarCarrito()
                    adapter.updateProductos(actualizado)
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error al eliminar", Toast.LENGTH_SHORT).show()
                }
            }
        }
        builder.setNegativeButton("No", null)
        builder.show()
    }
}
