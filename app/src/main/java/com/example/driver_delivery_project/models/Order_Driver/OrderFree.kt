package com.example.driver_delivery_project.models.Order_Driver


typealias OrdersFree = List<OrderFree>

data class OrderFree (
    val id: Int,
    val userID: Int,
    val restaurantID: Int,
    val total: String,
    val latitude: String,
    val longitude: String,
    val address: String,
    val driverID: Any? = null,
    val status: String,
    val createdAt: String,
    val deliveryProof: String,
    val orderDetails: List<OrderDetail>
)
