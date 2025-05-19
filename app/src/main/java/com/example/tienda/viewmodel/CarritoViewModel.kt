package com.example.tienda.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.asLiveData
import com.example.tienda.model.CartItemDto
import com.example.tienda.repository.MainState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CarritoViewModel(application: Application) : AndroidViewModel(application) {

    private val mainState = MainState(application.applicationContext)

    private val _carrito = MutableStateFlow<List<CartItemDto>>(emptyList())
    val carrito: LiveData<List<CartItemDto>> = _carrito.asLiveData()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: LiveData<String?> = _mensaje.asLiveData()

    fun cargarCarrito() {
        viewModelScope.launch {
            try {
                val response = mainState.recuperarCarrito()
                _carrito.value = response.items
            } catch (e: Exception) {
                _mensaje.value = "Error al cargar el carrito"
            }
        }
    }

    fun sumarProducto(cartItem: CartItemDto) {
        viewModelScope.launch {
            try {
                val exito = mainState.anadirProductoCarrito(cartItem.productId, 1)
                if (exito) cargarCarrito()
                else _mensaje.value = "Insuficiente stock"
            } catch (e: Exception) {
                _mensaje.value = "Error al añadir producto"
            }
        }
    }

    fun restarProducto(cartItem: CartItemDto) {
        viewModelScope.launch {
            try {
                val exito = mainState.anadirProductoCarrito(cartItem.productId, -1)
                if (exito) cargarCarrito()
                else _mensaje.value = "Error al restar unidad"
            } catch (e: Exception) {
                _mensaje.value = "Error al restar producto"
            }
        }
    }

    fun eliminarProducto(cartItem: CartItemDto) {
        viewModelScope.launch {
            try {
                val exito = mainState.eliminarProductoCarrito(cartItem.productId)
                if (exito) cargarCarrito()
                else _mensaje.value = "Error al eliminar producto"
            } catch (e: Exception) {
                _mensaje.value = "Error al eliminar producto"
            }
        }
    }

    fun limpiarMensaje() {
        _mensaje.value = null
    }
}