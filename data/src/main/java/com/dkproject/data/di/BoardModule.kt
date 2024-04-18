package com.dkproject.data.di

import com.dkproject.data.RemoteDataSource.BoardDataSource
import com.dkproject.data.RemoteDataSource.BoardRemoteDataSource
import com.dkproject.data.repository.board.BoardRepositoryImpl
import com.dkproject.data.usecase.board.DeleteBoardUseCaseImpl
import com.dkproject.data.usecase.board.GetBoardListUseCaseImpl
import com.dkproject.domain.repository.BoardRepository
import com.dkproject.domain.usecase.board.DeleteBoardUseCase
import com.dkproject.domain.usecase.board.GetBoardListUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent


@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class BoardModule {

    @Binds
    abstract fun bindBoardDataSource(uc:BoardRemoteDataSource):BoardDataSource

    @Binds
    abstract fun bindBoardRepository(uc:BoardRepositoryImpl):BoardRepository

    @Binds
    abstract fun bindGetBoardListUseCase(uc:GetBoardListUseCaseImpl):GetBoardListUseCase

    @Binds
    abstract fun bindDeleteBoardUseCase(uc:DeleteBoardUseCaseImpl):DeleteBoardUseCase
}