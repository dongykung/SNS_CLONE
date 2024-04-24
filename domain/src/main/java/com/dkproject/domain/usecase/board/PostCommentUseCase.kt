package com.dkproject.domain.usecase.board

interface PostCommentUseCase {
    suspend operator fun invoke(boardId:Long,text:String
    ):Result<Long>
}