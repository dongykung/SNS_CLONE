package com.dkproject.domain.usecase.user

import com.dkproject.domain.model.User

interface GetUserInfoUseCase {
    suspend operator fun invoke():Result<User>
}