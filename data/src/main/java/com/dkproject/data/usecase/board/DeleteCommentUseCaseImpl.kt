package com.dkproject.data.usecase.board

import com.dkproject.data.retrofit.BoardService
import com.dkproject.domain.usecase.board.DeleteCommentUseCase
import javax.inject.Inject

class DeleteCommentUseCaseImpl @Inject constructor(
    private val boardService: BoardService
) : DeleteCommentUseCase {
    override suspend fun invoke(boardId: Long, commentId: Long): Result<Long> = runCatching {
        boardService.deleteComment(boardId = boardId, commentId = commentId).data
    }
}