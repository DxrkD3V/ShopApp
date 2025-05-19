package com.example.tienda.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tienda.repository.MainState
import kotlinx.coroutines.launch

class AddCartViewModel(application: Application) : AndroidViewModel(application) {
    private val mainState = MainState(application)

    private val _addSuccess = MutableLiveData<Boolean>()
    val addSuccess: LiveData<Boolean> get() = _addSuccess

    fun anadirAlCarrito(id: Long, cantidad: Int) {
        viewModelScope.launch {
            val success = mainState.anadirProductoCarrito(id, cantidad)
            _addSuccess.value = success
        }
    }
}