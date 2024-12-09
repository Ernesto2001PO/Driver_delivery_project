package com.example.driver_delivery_project.UI.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.driver_delivery_project.models.Order_Driver.OrdersFree
import com.example.driver_delivery_project.repositories.OrderRepository



class OrdersViewModel : ViewModel() {

private val orderRepository = OrderRepository

     private val _ordersList = MutableLiveData<OrdersFree>().apply {
            value = arrayListOf()
     }
        val ordersList: LiveData<OrdersFree> = _ordersList

    fun getOrders() {
        orderRepository.getOrders(
            onSuccess = {
                _ordersList.value = it
                Log.d("OrdersViewModel", "Orders: $it")

            },
            onError = {
                it.printStackTrace()
                Log.e("OrdersViewModel", "Error al obtener ordenes", it)
            }
        )
    }

    fun aceptOrder(orderId: Int) {
        orderRepository.aceptOrder(
            orderId = orderId,
            onSuccess = {
                Log.d("OrdersViewModel", "Orden aceptada con éxito: ${it.id}")
            },
            onError = {
                Log.e("OrdersViewModel", "Error al aceptar orden", it)
            }
        )
    }



}