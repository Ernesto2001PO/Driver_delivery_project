package com.example.driver_delivery_project.api

import com.example.driver_delivery_project.models.Order_Driver.Driver
import com.example.driver_delivery_project.models.Login.LoginRequest
import com.example.driver_delivery_project.models.Login.LoginResponse
import com.example.driver_delivery_project.models.Order_Driver.OrderDetail
import com.example.driver_delivery_project.models.Order_Driver.OrderFree
import com.example.driver_delivery_project.models.Order_Driver.OrdersFree
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface JSONPlaceHolderService {


    @POST("users")
    fun createUser( @Body driver: Driver): Call<Driver>


    @Headers("Accept: application/json")
    @POST("users/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @Headers("Accept: application/json")
    @GET("orders/free")
    fun getOrdersfree(): Call<OrdersFree>

    @Headers("Accept: application/json")
    @GET("drivers/orders")
    fun getOrders(): Call<List<OrderFree>>

    @Headers("Accept: application/json")
    @POST("orders/{id}/accept")
    fun getOrderDetails(@Path("id") orderId: Int): Call<OrderFree>

















}