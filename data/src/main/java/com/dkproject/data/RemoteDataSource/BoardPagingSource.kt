package com.dkproject.data.RemoteDataSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dkproject.domain.model.Board
import com.dkproject.domain.usecase.token.GetTokenUseCase
import javax.inject.Inject

class BoardPagingSource @Inject constructor(
    private val boardDataSource: BoardDataSource,
    private val userId:Long = -1
) : PagingSource<Int, Board>() {
    override fun getRefreshKey(state: PagingState<Int, Board>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Board> {
        try {
            val page:Int = params.key?:1
            val loadSize = params.loadSize
            var setdata :MutableList<Board> =  mutableListOf()
            val data = boardDataSource.getBoard(page = page, size = loadSize).onSuccess {
                Log.d("BoardPagingSource", "sucess")

                setdata=it.toMutableList()
            }.onFailure {
                Log.d("BoardPagingSource", "errorfail")
            }
            Log.d("BoardPagingSource", data.toString())

            val size = setdata.size
            return LoadResult.Page(
                data = setdata,
                prevKey = null,
                nextKey = if (size == params.loadSize) page + 1 else null
            )
        } catch (e: Exception) {
            Log.d("BoardPagingSource", "error")
            return LoadResult.Error(e)
        }
    }
}