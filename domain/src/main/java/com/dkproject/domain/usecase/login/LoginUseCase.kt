package com.dkproject.domain.usecase.login

import com.dkproject.domain.model.CommonResponse

interface LoginUseCase {
    suspend operator fun invoke(id:String,pw:String):Result<CommonResponse<String>>
}