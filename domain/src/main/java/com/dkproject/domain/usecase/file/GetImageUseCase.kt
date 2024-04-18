package com.dkproject.domain.usecase.file

import com.dkproject.domain.model.Image

interface GetImageUseCase {
    operator fun invoke(contentUri:String):Image?
}