package com.dkproject.data.usecase.login

import com.dkproject.data.model.LoginParam
import com.dkproject.data.retrofit.UserService
import com.dkproject.domain.model.CommonResponse
import com.dkproject.domain.usecase.login.LoginUseCase
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val userService: UserService
) :LoginUseCase{
    override suspend fun invoke(id:String,pw:String): Result<CommonResponse<String>>  = runCatching {
        val requestBody =LoginParam(loginId=id, password = pw).toRequestBody()
        userService.login(requestBody = requestBody)
    }
}