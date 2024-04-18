package com.dkproject.presentation.model

import com.dkproject.domain.model.Board

data class BoardCardModel(
    val boardId:Long,
    val username:String,
    val userId:Long,
    val images:List<String>,
    val text:String,
    val userProfileUrl:String,
    val createAt:String,
)


fun Board.toUiModel():BoardCardModel{
    return BoardCardModel(
        boardId = this.id,
        username = this.updateUserName,
        userId = this.updateUserId,
        images = this.images,
        text = this.content,
        userProfileUrl = this.updateUserProfileFilePath,
        createAt = this.createdAt
    )
}
