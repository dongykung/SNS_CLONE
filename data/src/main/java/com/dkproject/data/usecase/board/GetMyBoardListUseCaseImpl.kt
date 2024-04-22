package com.dkproject.data.usecase.board

import androidx.paging.PagingData
import com.dkproject.domain.model.Board
import com.dkproject.domain.repository.BoardRepository
import com.dkproject.domain.usecase.board.GetMyBoardUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyBoardListUseCaseImpl @Inject constructor(
    private val boardRepository: BoardRepository
) :GetMyBoardUseCase{
    override suspend fun invoke(userId:Long): Result<Flow<PagingData<Board>>>  =
        boardRepository.getMyBoard(userId)


}