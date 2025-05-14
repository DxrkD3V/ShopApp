package com.example.tienda.model

import LoginUserDto
import TokensDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    suspend fun getProductos(): Response<List<ProductoDto>>

    @GET("cart/")
    suspend fun getCarrito(): Response<CartResponseDto>

    @POST("cart/{productId}/{count}")
    suspend fun anadirAlCarrito(
        @Path("productId") productId: Long,
        @Path("count") count: Int
    ): Response<CartResponseDto>

    @DELETE("cart/{productId}")
    suspend fun eliminarDelCarrito(
        @Path("productId") productId: Long
    ): Response<CartResponseDto>


    @POST("auth/login")
    suspend fun login(@Body loginUserDto: LoginUserDto): Response<TokensDto>

    @GET("products/find")
    suspend fun getProductosFiltrados(
        @Query("search") search: String? = null,
        @Query("cat") cat: Long? = null,
        @Query("sortBy") sortBy: String = "name",
        @Query("sortDir") sortDir: String = "asc",
        @Query("pageNumber") page: Int = 0,
        @Query("pageSize") size: Int = 5
    ): Response<ProductoPage>
}