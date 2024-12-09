package com.example.driver_delivery_project.UI.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.delivery_project.UI.viewmodels.DriversViewModel
import com.example.driver_delivery_project.R
import com.example.driver_delivery_project.databinding.ActivityMainBinding
import com.example.driver_delivery_project.models.Login.LoginRequest

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: DriversViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLogin.setOnClickListener {
            login()
            viewModel.checkDriver()


        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


        viewModel.driverHasOrder.observe(this) { hasOrder ->
            if (hasOrder) {
                viewModel.driverOrder.observe(this) { order ->
                    if (order != null) {
                        val intent = Intent(this, MapsActivity::class.java).apply {
                            putExtra("latitude", order.latitude)
                            putExtra("longitude", order.longitude)
                            putExtra("address", order.address)

                            Log.d("MainActivity", "Datos de la orden: ${order.latitude}, ${order.longitude}, ${order.address}")
                        }
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "No se pudo obtener la orden del conductor", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                val intent = Intent(this, Menu_PedidosActivity::class.java)
                startActivity(intent)
            }
        }





    }

    fun login() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPasswordLogin.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            binding.etEmail.error = "Email is required"
            binding.etPasswordLogin.error = "Password is required"
            return
        }

        val loginRequest = LoginRequest(
            email = email,
            password = password
        )

        Log.d("Login", "Datos a enviar para el login: Email: $email, Password: $password")

        viewModel.loginDriver(loginRequest)
    }






}