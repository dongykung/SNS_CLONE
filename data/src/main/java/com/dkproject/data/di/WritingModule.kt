package com.dkproject.data.di

import com.dkproject.data.usecase.file.GetImageListUseCaseImpl
import com.dkproject.data.usecase.postwrite.PostBoardUseCaseImpl
import com.dkproject.domain.usecase.file.GetImageListUseCase
import com.dkproject.domain.usecase.postwrite.PostBoardUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent


@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class WritingModule {
    @Binds
    abstract fun bindGetImageListUseCase(uc: GetImageListUseCaseImpl) : GetImageListUseCase

    @Binds
    abstract fun bindUploadPostUseCase(uc: PostBoardUseCaseImpl) : PostBoardUseCase
}