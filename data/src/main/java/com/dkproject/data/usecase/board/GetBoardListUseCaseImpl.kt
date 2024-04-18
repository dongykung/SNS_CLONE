package com.dkproject.data.usecase.board

import androidx.paging.PagingData
import com.dkproject.domain.model.Board
import com.dkproject.domain.repository.BoardRepository
import com.dkproject.domain.usecase.board.GetBoardListUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBoardListUseCaseImpl @Inject constructor(
    private val boardRepository: BoardRepository
) :GetBoardListUseCase{
    override suspend fun invoke(): Result<Flow<PagingData<Board>>> =
        boardRepository.invoke()


}