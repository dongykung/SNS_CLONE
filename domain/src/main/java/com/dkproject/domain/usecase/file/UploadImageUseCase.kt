package com.dkproject.domain.usecase.file

import com.dkproject.domain.model.Image

interface UploadImageUseCase {
    suspend operator fun invoke(
        image:Image
    ):Result<String>
}