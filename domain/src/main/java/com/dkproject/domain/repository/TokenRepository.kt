package com.dkproject.domain.repository

interface TokenRepository {
    suspend fun getToken():String?

    suspend fun setToken(token:String)

    suspend fun clearToken()
}