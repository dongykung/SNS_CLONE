package com.dkproject.data.usecase.user

import com.dkproject.data.retrofit.UserService
import com.dkproject.domain.model.User
import com.dkproject.domain.usecase.user.GetMyInfoUseCase
import javax.inject.Inject

class GetMyInfoUseCaseImpl @Inject constructor(
    private val userService: UserService
):GetMyInfoUseCase {
    override suspend fun invoke(): Result<User>  = runCatching {
         userService.myPage().data.toDomainModel()
    }
}