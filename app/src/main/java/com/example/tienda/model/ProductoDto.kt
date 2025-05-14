package com.example.tienda.model

import java.io.Serializable

data class ProductoDto(
    val id: Int,
    val name: String,
    val imageurl: String,
    val description: String,
    val price: Double,
    val stock: Int,
    val manufacture: String,
    val motor: String,
    val hp: Int,
    val maxVelocity: Int,
    val categoryId: Int,
    val units: Int = 0
) : Serializable

data class ProductoPage(
    val content: List<ProductoDto>,
    val totalElements: Int,
    val totalPages: Int,
    val number: Int,
    val size: Int,
    val last: Boolean
)
