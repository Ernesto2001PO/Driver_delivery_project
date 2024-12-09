package com.example.driver_delivery_project.UI.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.driver_delivery_project.UI.adapters.OrdersAdapter
import com.example.driver_delivery_project.UI.viewmodels.OrdersViewModel
import com.example.driver_delivery_project.databinding.MenuPedidoBinding
import com.example.driver_delivery_project.models.Order_Driver.OrderFree
import java.util.ArrayList

class Menu_PedidosActivity : AppCompatActivity(){

    private lateinit var binding: MenuPedidoBinding
    private val viewModel: OrdersViewModel by viewModels()
    private var adapter = OrdersAdapter(orders = mutableListOf(), onAcceptClick = this::acceptOrder, ordersDenied = ArrayList())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MenuPedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        setupViewModelObservers()

        viewModel.getOrders()



    }

    private fun setupRecyclerView() {
        adapter = OrdersAdapter(mutableListOf(), this::acceptOrder, ArrayList())
        binding.rvOrdersFree.adapter = adapter
        binding.rvOrdersFree.layoutManager = LinearLayoutManager(this)
    }

    private fun setupViewModelObservers() {
        viewModel.ordersList.observe(this) { orders ->
            val mutableOrders = orders.toMutableList()
            adapter.updateOrders(mutableOrders)

        }
    }


    private fun acceptOrder(orderId: Int) {
        viewModel.aceptOrder(orderId = orderId)
    }


}