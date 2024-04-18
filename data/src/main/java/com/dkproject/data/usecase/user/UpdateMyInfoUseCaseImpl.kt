package com.dkproject.data.usecase.user

import com.dkproject.data.model.UpdateMyInfoParam
import com.dkproject.data.retrofit.UserService
import com.dkproject.domain.model.Image
import com.dkproject.domain.usecase.file.GetImageUseCase
import com.dkproject.domain.usecase.file.UploadImageUseCase
import com.dkproject.domain.usecase.user.UpdateMyInfoUseCase
import javax.inject.Inject

class UpdateMyInfoUseCaseImpl @Inject constructor(
    private val userService: UserService,
    private val uploadImageUseCase: UploadImageUseCase,
    private val getImageUseCase: GetImageUseCase
) : UpdateMyInfoUseCase {
    override suspend fun invoke(
        username: String,
        extrauserinfo: String,
        profilefilepath: String,
        profileChange: Boolean
    ): Result<Unit> = runCatching {
        if (profileChange) {
            val image: Image = getImageUseCase(contentUri = profilefilepath)
                ?: throw NullPointerException("이미지를 찾을 수 없습니다")
            val imagePath = uploadImageUseCase(image).getOrThrow()
            val requestBody = UpdateMyInfoParam(
                userName = username,
                extraUserInfo = extrauserinfo,
                profileFilePath = imagePath
            ).toRequestBody()
            userService.patchMyPage(requestBody=requestBody)
        } else {
            val requestBody = UpdateMyInfoParam(
                userName = username,
                extraUserInfo = extrauserinfo,
                profileFilePath = profilefilepath
            ).toRequestBody()
            userService.patchMyPage(requestBody = requestBody)
        }
    }
}