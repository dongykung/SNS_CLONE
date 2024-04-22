package com.dkproject.domain.repository

import com.dkproject.domain.model.User

interface UserRepository {
    suspend fun getUserInfo(userId:Long): Result<User>
}