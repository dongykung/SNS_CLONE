package com.dkproject.domain.usecase.file

import com.dkproject.domain.model.Image

interface GetImageListUseCase {
    suspend operator fun invoke():List<Image>
}