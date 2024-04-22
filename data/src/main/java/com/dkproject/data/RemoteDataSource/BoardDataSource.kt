package com.dkproject.data.RemoteDataSource

import com.dkproject.data.model.dto.BoardDTO
import com.dkproject.data.retrofit.BoardService
import com.dkproject.domain.model.Board
import com.dkproject.domain.model.CommonResponse
import com.dkproject.domain.usecase.token.GetTokenUseCase
import javax.inject.Inject

interface BoardDataSource {
    suspend fun getBoard(page:Int,size:Int):Result<List<Board>>
}

class BoardRemoteDataSource @Inject constructor(
    private val boardService: BoardService,
    private val getTokenUseCase: GetTokenUseCase
):BoardDataSource {
    override suspend fun getBoard(page: Int, size: Int): Result<List<Board>> = runCatching {
        boardService.getBoard(page=page,size=size).data.map { it.toDomainModel() }
    }

}