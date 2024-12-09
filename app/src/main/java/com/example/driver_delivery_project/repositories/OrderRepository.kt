package com.example.driver_delivery_project.repositories

import android.util.Log
import com.example.driver_delivery_project.api.JSONPlaceHolderService
import com.example.driver_delivery_project.models.App
import com.example.driver_delivery_project.models.Order_Driver.OrderFree
import com.example.driver_delivery_project.models.Order_Driver.OrdersFree
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object OrderRepository {
    private val retrofit by lazy {
        RetrofitRepository.getRetrofitInstance(App.instance.applicationContext)
    }
    private val service by lazy {
        retrofit.create(JSONPlaceHolderService::class.java)
    }



    fun getOrders(
        onSuccess: (OrdersFree) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        service.getOrdersfree().enqueue(object : Callback<OrdersFree> {
            override fun onResponse(call: Call<OrdersFree>, response: Response<OrdersFree>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) }
                } else {
                    onError(Throwable("Error en la respuesta al obtener ordenes"))
                }
            }

            override fun onFailure(call: Call<OrdersFree>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun aceptOrder(
        orderId: Int,
        onSuccess: (OrderFree) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        service.getOrderDetails(orderId).enqueue(object : Callback<OrderFree> {
            override fun onResponse(call: Call<OrderFree>, response: Response<OrderFree>) {
                if (response.isSuccessful) {
                    val orderResponse = response.body()
                    if (orderResponse != null) {
                        Log.d("OrderRepository", "Orden aceptada con éxito: ${orderResponse.id}")
                        onSuccess(orderResponse)
                    } else {
                        Log.e("OrderRepository", "Error: Respuesta nula")
                        onError(Throwable("La respuesta está vacía"))
                    }
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Error desconocido"
                    Log.e("OrderRepository", "Error en la respuesta: $errorMessage")
                    onError(Throwable("Error en la respuesta del servidor: $errorMessage"))
                }
            }

            override fun onFailure(call: Call<OrderFree>, t: Throwable) {
                Log.e("OrderRepository", "Error en la llamada: ${t.message}")
                onError(t)
            }
        })
    }






}