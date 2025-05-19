package com.example.tienda.viewmodel

import LoginUserDto
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tienda.repository.MainState
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val mainState = MainState(application)

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> get() = _loginSuccess

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val success = mainState.loginUser(LoginUserDto(email, password), getApplication())
                _loginSuccess.value = success
                if (!success) _error.value = "Credenciales inválidas"
            } catch (e: Exception) {
                _error.value = "Error del servidor, inténtelo más tarde."
            }
        }
    }
}
