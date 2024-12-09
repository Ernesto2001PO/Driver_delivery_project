package com.example.driver_delivery_project.UI.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.driver_delivery_project.R
import com.example.driver_delivery_project.UI.activities.MapsActivity
import com.example.driver_delivery_project.models.Order_Driver.OrderFree
import com.example.driver_delivery_project.models.Order_Driver.OrdersFree

class OrdersAdapter(
    private var orders: MutableList<OrderFree>,
    private val onAcceptClick: (Int) -> Unit,
    private var ordersDenied: ArrayList<OrderFree>,



    ): RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrdersViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int,) {
        if (position < orders.size) {
            holder.bind(orders[position])
        } else {
            holder.bindDenied(ordersDenied[position - orders.size])
        }
    }

    override fun getItemCount(): Int {
        return orders.size + ordersDenied.size
    }

    inner class OrdersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(order: OrderFree) {
            itemView.findViewById<TextView>(R.id.txtOrderAddress).text = "Direction: " + order.address
            itemView.findViewById<TextView>(R.id.txtTotal).text = order.total + "Bs"
            itemView.findViewById<TextView>(R.id.txtCreated).text = "Date: " + order.createdAt


            itemView.findViewById<Button>(R.id.btnAcept).setOnClickListener {
                val orderId = order.id
                Log.d("OrdersAdapter", "Order id: $orderId")
                onAcceptClick(orderId)

                val intent = Intent(itemView.context, MapsActivity::class.java)
                intent.putExtra("latitude", order.latitude)
                intent.putExtra("longitude", order.longitude)
                itemView.context.startActivity(intent)
            }

            itemView.findViewById<Button>(R.id.btnDenied).setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    if (position < orders.size) {
                        val removedOrder = orders[position]

                        ordersDenied.add(removedOrder)
                        orders.removeAt(position)

                        notifyItemRemoved(position)

                        notifyItemInserted(orders.size + ordersDenied.size - 1) // -1 porque el índice empieza en 0

                        Log.d("OrdersAdapter", "Orden movida a denegada: ${removedOrder.address}")
                    }
                }
            }

        }

        fun bindDenied(order: OrderFree) {
            itemView.findViewById<TextView>(R.id.txtOrderAddress).text = "Direction: " + order.address
            itemView.findViewById<TextView>(R.id.txtTotal).text = order.total + "Bs"
            itemView.findViewById<TextView>(R.id.txtCreated).text = "Date: " + order.createdAt

            itemView.findViewById<Button>(R.id.btnAcept).setOnClickListener {
                val orderId = order.id
                Log.d("OrdersAdapter", "Order id: $orderId")
                onAcceptClick(orderId)

                val intent = Intent(itemView.context, MapsActivity::class.java)
                intent.putExtra("latitude", order.latitude)
                intent.putExtra("longitude", order.longitude)
                itemView.context.startActivity(intent)
            }

            itemView.findViewById<Button>(R.id.btnDenied).setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    ordersDenied.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }
    }

    fun updateOrders(newOrders: MutableList<OrderFree>) {
        this.orders = newOrders
        notifyDataSetChanged()
    }

}