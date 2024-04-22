package com.dkproject.data.usecase.user

import com.dkproject.domain.model.User
import com.dkproject.domain.repository.UserRepository
import com.dkproject.domain.usecase.user.GetUserInfoUseCase
import javax.inject.Inject

class GetUserInfoUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
):GetUserInfoUseCase {
    override suspend fun invoke(userId: Long): Result<User> =
        userRepository.getUserInfo(userId)
}