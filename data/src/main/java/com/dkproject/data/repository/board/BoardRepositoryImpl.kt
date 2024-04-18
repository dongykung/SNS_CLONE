package com.dkproject.data.repository.board

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dkproject.data.RemoteDataSource.BoardDataSource
import com.dkproject.data.RemoteDataSource.BoardPagingSource
import com.dkproject.domain.model.Board
import com.dkproject.domain.repository.BoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BoardRepositoryImpl @Inject constructor(
    private val boardDataSource: BoardDataSource,
) :BoardRepository{
    override suspend fun invoke(): Result<Flow<PagingData<Board>>> = kotlin.runCatching {
        Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {BoardPagingSource(boardDataSource)}
        ).flow
    }
}