package com.example.tienda.model

data class CartItemDto(
    val productId: Long,
    val productName: String,
    val quantity: Int,
    val unitPrice: Double,
    val subtotal: Double,
    val imageUrl: String,
    val units: Int,
    val product: ProductoDto
)