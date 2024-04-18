package com.dkproject.data.usecase.postwrite

import android.content.Context
import android.content.Intent
import com.dkproject.data.model.Parcel.BoardParcel
import com.dkproject.data.model.Parcel.ImageParcel
import com.dkproject.data.service.BoardPostingService
import com.dkproject.domain.model.Image
import com.dkproject.domain.usecase.postwrite.PostBoardUseCase
import javax.inject.Inject

class PostBoardUseCaseImpl @Inject constructor(
   private val context: Context
) :PostBoardUseCase{
    override suspend fun invoke(title: String, content: String, imageList: List<Image>) {
        val imageParcelList = mutableListOf<ImageParcel>()
         imageList.forEach {
            val imageParcel = ImageParcel(
                uri=it.uri,
                name=it.uri,
                size=it.size,
                mimeType = it.mimeType
            )
             imageParcelList.add(imageParcel)
        }
        val board = BoardParcel(title=title,content=content, images = imageParcelList)

        context.startForegroundService(
            Intent(context,BoardPostingService::class.java).apply {
                putExtra(BoardPostingService.EXTRA_BOARD,board)
            }
        )
    }
}