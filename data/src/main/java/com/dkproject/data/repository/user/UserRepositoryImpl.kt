package com.dkproject.data.repository.user

import com.dkproject.data.RemoteDataSource.UserDataSource
import com.dkproject.domain.model.User
import com.dkproject.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) :UserRepository{
    override suspend fun getUserInfo(userId: Long): Result<User>  = runCatching{
        userDataSource.getUserInfo(userId)
    }
}