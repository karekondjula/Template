package com.team2.template.service

import com.team2.template.model.GithubUser
import retrofit2.Call
import retrofit2.http.GET

interface GithubApi {

    @GET("users")
    fun getUsers(): Call<List<GithubUser>>
}