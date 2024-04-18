package com.dkproject.data.usecase.file

import com.dkproject.data.di.BASE_URL
import com.dkproject.data.retrofit.FileService
import com.dkproject.data.retrofit.UriRequestBody
import com.dkproject.domain.model.Image
import com.dkproject.domain.usecase.file.GetImageUseCase
import com.dkproject.domain.usecase.file.GetInputStreamUseCase
import com.dkproject.domain.usecase.file.UploadImageUseCase
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import javax.inject.Inject

class UploadImageUseCaseImpl @Inject constructor(
    private val fileService: FileService,
    private val getInputStreamUseCase: GetInputStreamUseCase,
) : UploadImageUseCase {
    override suspend fun invoke(image: Image): Result<String> = runCatching {
        val fileNamePart = MultipartBody.Part.createFormData(
            "fileName",
            image.name
        )
        val requestBody = UriRequestBody(
            contentUri = image.uri,
            getInputStreamUseCase = getInputStreamUseCase,
            contentType = image.mimeType.toMediaType(),
            contentLength = image.size
            )
        val filePart = MultipartBody.Part.createFormData(
            "file",
            image.name,
            requestBody
        )
        "$BASE_URL"+fileService.uploadImage(
            fileName = fileNamePart,
            file = filePart
        ).data.filePath

    }


}