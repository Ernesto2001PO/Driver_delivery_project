package com.example.driver_delivery_project.models.Order_Driver

data class Product (
    val id: Long,
    val name: String,
    val description: String,
    val price: String,
    val restaurantID: Long,
    val image: String
)