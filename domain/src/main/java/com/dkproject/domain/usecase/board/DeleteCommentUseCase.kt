package com.dkproject.domain.usecase.board

interface DeleteCommentUseCase {
    suspend operator fun invoke(boardId:Long,commentId:Long):Result<Long>
}