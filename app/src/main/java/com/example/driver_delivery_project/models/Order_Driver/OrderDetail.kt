package com.example.driver_delivery_project.models.Order_Driver


data class OrderDetail (
    val id: Long,
    val quantity: Long,
    val price: String,
    val product: Product
)