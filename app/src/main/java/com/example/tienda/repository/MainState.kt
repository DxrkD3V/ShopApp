package com.example.tienda.repository

import LoginUserDto
import android.content.Context
import com.example.tienda.model.ApiService
import com.example.tienda.model.CartResponseDto
import com.example.tienda.model.CategoryDto
import com.example.tienda.model.ProductoDto
import com.example.tienda.utils.TokenManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainState(private val context: Context) {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder().addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                val token = TokenManager.getAccessToken(context)
                if (!token.isNullOrEmpty()) {
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }
                chain.proceed(requestBuilder.build())
            }.build()
        )
        .build()

    private val service = retrofit.create(ApiService::class.java)

    suspend fun loginUser(loginUserDto: LoginUserDto, context: Context): Boolean {
        val response = service.login(loginUserDto)
        return if (response.isSuccessful && response.body() != null) {
            val tokens = response.body()!!
            TokenManager.saveTokens(context, tokens.accessToken, tokens.refreshToken)
            true
        } else {
            false
        }
    }

//    suspend fun recuperarProductos(): List<ProductoDto> {
//        val response = service.getProductos()
//        return if (response.isSuccessful && response.body() != null) {
//            response.body()!!
//        } else {
//            emptyList()
//        }
//    }

    suspend fun recuperarProductosFiltrados(
        categoriaId: Long?,
        pagina: Int,
        tamanio: Int = 5
    ): Pair<List<ProductoDto>?, Int>? {
        val response = service.getProductosFiltrados(
            cat = categoriaId,
            page = pagina,
            size = tamanio
        )
        return if (response.isSuccessful) {
            val productos = response.body()?.content ?: emptyList()
            val totalPages = response.body()?.totalPages ?: 1
            Pair(productos, totalPages)
        } else {
            null
        }
    }

    suspend fun getAllCategories(): List<CategoryDto> {
        val response = service.getAllCategories()
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            return emptyList()
        }
    }

    suspend fun recuperarCarrito(): CartResponseDto {
        val response = service.getCarrito()

        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            return CartResponseDto(emptyList(), 0, 0.0)
        }
    }

    suspend fun eliminarProductoCarrito(productId: Long): Boolean {
        val response = service.eliminarDelCarrito(productId)
        return response.isSuccessful
    }

//    suspend fun eliminarAllProductosCarrito(): Boolean {
//        val response = service.eliminarAllDelCarrito()
//        return response.isSuccessful
//    }

    suspend fun anadirProductoCarrito(productId: Long, cantidad: Int): Boolean {
        val response = service.anadirAlCarrito(productId, cantidad)
        return response.isSuccessful
    }
}