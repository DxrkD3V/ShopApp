package com.example.tienda.model

import LoginUserDto
import TokensDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    suspend fun getProductos(): Response<List<ProductoData>>

    @GET("products/category/{id}/")
    suspend fun getProductosPorCategoria(@Path("id") categoryId: Int): Response<List<ProductoData>>

    @GET("cart")
    suspend fun getCarrito(): Response<List<ProductoData>>

    @HTTP(method = "DELETE", path = "cart/delete", hasBody = true)
    suspend fun eliminarDelCarrito(@Body dto: AddItemCartDTO): Response<Unit>

    @HTTP(method = "POST", path = "cart/add", hasBody = true)
    suspend fun anadirAlCarrito(@Body dto: AddItemCartDTO): Response<Unit>

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