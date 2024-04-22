package com.dkproject.domain.repository

import androidx.paging.PagingData
import com.dkproject.domain.model.Board
import kotlinx.coroutines.flow.Flow

interface BoardRepository {
    suspend operator fun invoke():Result<Flow<PagingData<Board>>>

    suspend fun getMyBoard(myId:Long):Result<Flow<PagingData<Board>>>
}