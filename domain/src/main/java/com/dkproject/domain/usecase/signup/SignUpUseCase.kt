package com.dkproject.domain.usecase.signup

import com.dkproject.domain.model.CommonResponse

interface SignUpUseCase {
    suspend operator fun invoke(id:String,name:String,pw:String):Result<CommonResponse<Long>>
}