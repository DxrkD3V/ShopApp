package com.example.tienda.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tienda.model.CategoryDto
import com.example.tienda.repository.MainState
import com.example.tienda.model.ProductoDto
import kotlinx.coroutines.launch

class ProductosViewModel(application: Application) : AndroidViewModel(application) {
    private val mainState = MainState(application)

    private val _productos = MutableLiveData<List<ProductoDto>>()
    val productos: LiveData<List<ProductoDto>> = _productos

    private val _categorias = MutableLiveData<List<CategoryDto>>()
    val categorias: LiveData<List<CategoryDto>> = _categorias

    private val _paginaActual = MutableLiveData(0)
    val paginaActual: LiveData<Int> = _paginaActual

    private val _totalPaginas = MutableLiveData(1)
    val totalPaginas: LiveData<Int> = _totalPaginas

    private var categoriaSeleccionadaId: Long? = null

    fun cargarCategorias() {
        viewModelScope.launch {
            try {
                _categorias.value = mainState.getAllCategories()
            } catch (_: Exception) {}
        }
    }

    fun cargarProductos() {
        viewModelScope.launch {
            try {
                val result = mainState.recuperarProductosFiltrados(categoriaSeleccionadaId, _paginaActual.value ?: 0)
                result?.let {
                    _productos.value = it.first ?: emptyList()
                    _totalPaginas.value = it.second
                }
            } catch (_: Exception) {
                _productos.value = emptyList()
            }
        }
    }

    fun seleccionarCategoria(posicion: Int) {
        val listaCategorias = _categorias.value ?: return
        categoriaSeleccionadaId = listaCategorias[posicion].id
        _paginaActual.value = 0
        cargarProductos()
    }

    fun paginaSiguiente() {
        if ((_paginaActual.value ?: 0) < (_totalPaginas.value ?: 1) - 1) {
            _paginaActual.value = (_paginaActual.value ?: 0) + 1
            cargarProductos()
        }
    }

    fun paginaAnterior() {
        if ((_paginaActual.value ?: 0) > 0) {
            _paginaActual.value = (_paginaActual.value ?: 0) - 1
            cargarProductos()
        }
    }
}
