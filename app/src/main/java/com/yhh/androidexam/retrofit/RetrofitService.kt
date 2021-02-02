package com.yhh.androidexam.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {
    @GET("json/students/")
    fun getStudentsList(): Call<ArrayList<Students>>
}