package com.example.driver_delivery_project.repositories

import android.content.Context
import com.example.driver_delivery_project.models.AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitRepository {


    fun getRetrofitInstance(context: Context): Retrofit {
        val driver = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()


        return Retrofit.Builder()
            .baseUrl("https://proyectodelivery.jmacboy.com/api/")
            .client(driver)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    }

