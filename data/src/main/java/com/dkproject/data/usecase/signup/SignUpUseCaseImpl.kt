package com.dkproject.data.usecase.signup

import com.dkproject.data.model.SignUpParam
import com.dkproject.data.retrofit.UserService
import com.dkproject.domain.model.CommonResponse
import com.dkproject.domain.usecase.signup.SignUpUseCase
import javax.inject.Inject

class SignUpUseCaseImpl @Inject constructor(
    private val userService: UserService
) : SignUpUseCase {
    override suspend fun invoke(
        id: String,
        name: String,
        pw: String
    ): Result<CommonResponse<Long>> = runCatching {
        val requestBody = SignUpParam(
            loginId = id, name = name, password = pw,
            extraUserInfo = "", profileFilePath = ""
        ).toRequestBody()
        userService.signUp(requestBody = requestBody)
    }

}