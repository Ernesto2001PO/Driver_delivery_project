package com.example.delivery_project.repositories


import android.util.Log
import com.example.driver_delivery_project.api.JSONPlaceHolderService
import com.example.driver_delivery_project.models.App
import com.example.driver_delivery_project.models.Order_Driver.Driver
import com.example.driver_delivery_project.models.Login.LoginRequest
import com.example.driver_delivery_project.models.Login.LoginResponse
import com.example.driver_delivery_project.models.Order_Driver.OrderFree
import com.example.driver_delivery_project.models.Order_Driver.OrdersFree
import com.example.driver_delivery_project.repositories.RetrofitRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DriverRepository {

    private val retrofit = RetrofitRepository.getRetrofitInstance(App.instance.applicationContext)
    private val service = retrofit.create(JSONPlaceHolderService::class.java)


    fun createDriver(
        driver: Driver,
        onSuccess: (Driver) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        service.createUser(driver).enqueue(object : Callback<Driver> {
            override fun onResponse(call: Call<Driver>, response: Response<Driver>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) }
                } else {
                    onError(Throwable("Error en la respuesta al crear Persona"))
                }
            }

            override fun onFailure(call: Call<Driver>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun loginUser(
        loginRequest: LoginRequest,
        onSuccess: (String) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        service.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val token = response.body()?.access_token
                    if (token != null) {
                        onSuccess(token)
                    } else {
                        onError(Throwable("Token nulo"))
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Respuesta desconocida"
                    onError(Throwable(errorBody))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onError(t)
            }
        })
    }


    fun checkDriverOrder(
        onSuccess: (Boolean,OrderFree?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        service.getOrders().enqueue(object : Callback<List<OrderFree>> {
            override fun onResponse(call: Call<List<OrderFree>>, response: Response<List<OrderFree>>) {
                if (response.isSuccessful) {
                    val orders = response.body()
                    val hasOrder = orders?.isNotEmpty() == true
                    val firstOrder = if (hasOrder) orders?.firstOrNull() else null
                    onSuccess(hasOrder,firstOrder)
                    Log.d("DriverRepository", "El conductor tiene orden: $hasOrder")
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Error desconocido"
                    Log.e("DriverRepository", "Error en la respuesta: $errorBody")
                    onError(Throwable(errorBody))
                }
            }

            override fun onFailure(call: Call<List<OrderFree>>, t: Throwable) {
                onError(t)
            }
        })
    }




}