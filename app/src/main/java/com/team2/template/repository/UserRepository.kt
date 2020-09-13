package com.team2.template.repository

import com.team2.template.service.GithubApi

class UserRepository(private val api: GithubApi) {
    fun getAllUsers() = api.getUsers()
}