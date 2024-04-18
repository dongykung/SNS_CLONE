package com.dkproject.data.usecase.board

import com.dkproject.data.retrofit.BoardService
import com.dkproject.domain.usecase.board.DeleteBoardUseCase
import javax.inject.Inject

class DeleteBoardUseCaseImpl @Inject constructor(
    private val boardService: BoardService
) :DeleteBoardUseCase{
    override suspend fun invoke(boardId: Long): Result<Long>  = runCatching{
        boardService.deleteBoard(boardId).data
    }
}