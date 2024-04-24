package com.dkproject.data.di

import com.dkproject.data.RemoteDataSource.BoardDataSource
import com.dkproject.data.RemoteDataSource.BoardRemoteDataSource
import com.dkproject.data.repository.board.BoardRepositoryImpl
import com.dkproject.data.usecase.board.DeleteBoardUseCaseImpl
import com.dkproject.data.usecase.board.DeleteCommentUseCaseImpl
import com.dkproject.data.usecase.board.GetBoardListUseCaseImpl
import com.dkproject.data.usecase.board.GetMyBoardListUseCaseImpl
import com.dkproject.data.usecase.board.PostCommentUseCaseImpl
import com.dkproject.domain.repository.BoardRepository
import com.dkproject.domain.usecase.board.DeleteBoardUseCase
import com.dkproject.domain.usecase.board.DeleteCommentUseCase
import com.dkproject.domain.usecase.board.GetBoardListUseCase
import com.dkproject.domain.usecase.board.GetMyBoardUseCase
import com.dkproject.domain.usecase.board.PostCommentUseCase
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

    @Binds
    abstract fun bindGetMYBoarddUseCase(uc:GetMyBoardListUseCaseImpl):GetMyBoardUseCase

    @Binds
    abstract fun bindCommentUseCase(uc:PostCommentUseCaseImpl):PostCommentUseCase

    @Binds
    abstract fun bindDeleteCommentUseCase(uc:DeleteCommentUseCaseImpl):DeleteCommentUseCase
}