package com.example.delivery_project.UI.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.delivery_project.repositories.DriverRepository
import com.example.driver_delivery_project.models.Order_Driver.Driver
import com.example.driver_delivery_project.models.Login.LoginRequest
import com.example.driver_delivery_project.models.Login.LoginResponse
import com.example.driver_delivery_project.models.Order_Driver.OrderFree


class DriversViewModel(aplication : Application): AndroidViewModel(aplication) {

    private val driverRepository: DriverRepository = DriverRepository



    private val _loginResult = MutableLiveData<Result<String>>()
    val loginResult: LiveData<Result<String>> get() = _loginResult



    private val _loginResponse = MutableLiveData<LoginResponse?>()
    val loginResponse: LiveData<LoginResponse?> = _loginResponse

    val driverHasOrder = MutableLiveData<Boolean>()  // MutableLiveData para almacenar el resultado


    val driverOrder = MutableLiveData<OrderFree?>()




    fun addDriver(user: Driver) {
        DriverRepository.createDriver(
            user,
            onSuccess = { createdPersona ->

                Log.d("UsersViewModel", "Driver creado: $createdPersona")
                Toast.makeText(getApplication(), "Driver creado", Toast.LENGTH_SHORT).show()

            },
            onError = { error ->
                Log.e("SubmitPerson", "Error al crear persona", error)
            }
        )
    }

    fun loginDriver(loginRequest: LoginRequest) {
        DriverRepository.loginUser(
            loginRequest,
            onSuccess = { token ->
                // Guardar el token en SharedPreferences
                val sharedPreferences = getApplication<Application>().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("auth_token", token)
                editor.apply()

                _loginResult.postValue(Result.success(token))
            },
            onError = { error ->
                _loginResult.postValue(Result.failure(error))
            }


        )

    }

    fun checkDriver() {
        driverRepository.checkDriverOrder(
            onSuccess = { hasOrder,order ->
                driverHasOrder.value = hasOrder
                driverOrder.value = order
                if (hasOrder) {
                    Log.d("DriverViewModel", "El conductor tiene orden")

                } else {
                    Log.d("DriverViewModel", "El conductor no tiene orden")
                }
            },
            onError = { error ->
                Log.e("DriverViewModel", "Error al verificar los pedidos: ${error.message}")
            }
        )
    }











}