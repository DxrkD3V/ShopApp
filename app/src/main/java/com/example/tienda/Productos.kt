package com.example.tienda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tienda.model.MainState
import com.example.tienda.recycler.AdapterProducto
import kotlinx.coroutines.launch

class Productos : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterProducto
    private lateinit var mainState: MainState
    private lateinit var spinnerCategorias: Spinner
    private lateinit var textPageNumber: TextView
    private lateinit var buttonPrev: ImageButton
    private lateinit var buttonNext: ImageView

    private var currentPage = 0
    private var totalPages = 1
    private var categoryId: Long? = null

    private val categories = listOf("4x4", "Eléctricos", "Deportivos", "Familiares", "Lujo")
    private val categoryIds = listOf(1L, 2L, 3L, 4L, 5L)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val MyView = inflater.inflate(R.layout.fragment_productos, container, false)
        buttonPrev = MyView.findViewById(R.id.buttonPrev)
        buttonNext = MyView.findViewById<ImageView>(R.id.buttonNext)
        buttonPrev.setOnClickListener {
            Toast.makeText(requireContext(), "Anterior clickeado", Toast.LENGTH_SHORT).show()
            goToPreviousPage()
        }
        buttonNext.setOnClickListener {
            Toast.makeText(requireContext(), "Siguiente clickeado", Toast.LENGTH_SHORT).show()
            goToNextPage()
        }
        return MyView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerProductos)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = AdapterProducto(emptyList())
        recyclerView.adapter = adapter

        spinnerCategorias = view.findViewById(R.id.spinnerCategorias)
        textPageNumber = view.findViewById(R.id.textPageNumber)


        val spinnerAdapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_item, categories
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategorias.adapter = spinnerAdapter

        mainState = MainState(requireContext())
        loadProducts()



        spinnerCategorias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, view: View?, position: Int, id: Long) {
                categoryId = categoryIds[position]
                currentPage = 0
                loadProducts()
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }
    }

    private fun loadProducts() {
        lifecycleScope.launch {
            try {
                val result = mainState.recuperarProductosFiltrados(categoryId, currentPage)
                if (result != null) {
                    val productos = result.first ?: emptyList()
                    totalPages = result.second
                    if (productos.isNotEmpty()) {
                        adapter.updateProductos(productos)
                        updatePaginationControls()
                    } else {
                        adapter.updateProductos(emptyList())
                        textPageNumber.text = (currentPage + 1).toString()
                        Toast.makeText(requireContext(), "No hay productos disponibles", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Error al cargar productos", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error al cargar productos: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun updatePaginationControls() {
        textPageNumber.text = (currentPage + 1).toString()
        //buttonPrev.isEnabled = currentPage > 0
        //buttonNext.isEnabled = currentPage < totalPages - 1
    }

    private fun goToPreviousPage() {
        if (currentPage > 0) {
            currentPage--
            loadProducts()
        }
    }

    private fun goToNextPage() {
        if (currentPage < totalPages - 1) {
            currentPage++
            loadProducts()
        }
    }
}