package com.example.driver_delivery_project.UI.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.delivery_project.UI.viewmodels.DriversViewModel

import com.example.driver_delivery_project.databinding.RegisterFormBinding
import com.example.driver_delivery_project.models.Order_Driver.Driver

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: RegisterFormBinding
    private val viewModel: DriversViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RegisterFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            submitForm()

        }
    }

   fun submitForm() {
        val name = binding.etNameRegister.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val role = 2

        Log.d("SubmitForm", "Datos de la persona a enviar Name: $name , Email: $email, Password: $password, Role: $role")

        val driver = Driver(
            id = 0,
            name = name,
            email = email,
            password = password,
            role = role
        )

        Log.d("SubmitForm", "User Object: $driver")

        viewModel.addDriver(driver)

    }


}