package com.dkproject.domain.usecase.postwrite

import com.dkproject.domain.model.Image

interface PostBoardUseCase {
     suspend operator fun invoke(
        title:String,
        content:String,
        imageList:List<Image>
    )
}