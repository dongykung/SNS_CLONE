package com.dkproject.domain.usecase.board

interface DeleteBoardUseCase {
    suspend operator fun invoke(
        boardId:Long
    ):Result<Long>
}