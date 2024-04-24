package com.dkproject.data.usecase.board

import com.dkproject.data.model.CommentParam
import com.dkproject.data.retrofit.BoardService
import com.dkproject.domain.usecase.board.PostCommentUseCase
import javax.inject.Inject

class PostCommentUseCaseImpl @Inject constructor(
    private val boardService: BoardService
) :PostCommentUseCase{
    override suspend fun invoke(boardId: Long, text: String): Result<Long>  = runCatching{
        val requestBody = CommentParam(text).toRequestBody()
        boardService.postComment(boardId,requestBody).data
    }
}