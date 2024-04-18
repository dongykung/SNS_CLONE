package com.dkproject.domain.usecase.user

interface UpdateMyInfoUseCase {
    suspend operator fun invoke(username:String,extrauserinfo:String,profilefilepath:String,
                                profileChange:Boolean):Result<Unit>
}