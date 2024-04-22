package com.dkproject.data.RemoteDataSource

import android.service.autofill.UserData
import com.dkproject.data.retrofit.UserService
import com.dkproject.domain.model.User
import javax.inject.Inject


interface UserDataSource{
    suspend fun getUserInfo(userId:Long):User
}

class UserDataSourceImpl @Inject constructor(
    private val userService: UserService
):UserDataSource{
    override suspend fun getUserInfo(userId: Long): User {
        return userService.userPage(userId).data.toDomainModel()
    }

}