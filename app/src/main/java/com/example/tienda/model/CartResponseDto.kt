package com.example.tienda.model

data class CartResponseDto(
    val items: List<CartItemDto>,
    val totalUnits: Int,
    val totalPrice: Double
)