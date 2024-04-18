package com.dkproject.data.usecase.user

import com.dkproject.data.retrofit.UserService
import com.dkproject.domain.model.User
import com.dkproject.domain.usecase.user.GetUserInfoUseCase
import javax.inject.Inject

class GetUserInfoUseCaseImpl @Inject constructor(
    private val userService: UserService
):GetUserInfoUseCase {
    override suspend fun invoke(): Result<User>  = runCatching {
         userService.myPage().data.toDomainModel()
    }
}