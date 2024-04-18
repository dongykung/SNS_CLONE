package com.dkproject.data.di

import com.dkproject.data.usecase.file.GetImageListUseCaseImpl
import com.dkproject.data.usecase.file.GetImageUseCaseImpl
import com.dkproject.data.usecase.file.GetInputStreamUseCaseImpl
import com.dkproject.data.usecase.file.UploadImageUseCaseImpl
import com.dkproject.domain.usecase.file.GetImageListUseCase
import com.dkproject.domain.usecase.file.GetImageUseCase
import com.dkproject.domain.usecase.file.GetInputStreamUseCase
import com.dkproject.domain.usecase.file.UploadImageUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class FileModule {

    @Binds
    abstract fun bindGetInputStreamUseCase(uc:GetInputStreamUseCaseImpl) : GetInputStreamUseCase

    @Binds
    abstract fun bindGetImageUseCase(uc:GetImageUseCaseImpl) : GetImageUseCase

    @Binds
    abstract fun bindUploadImageUseCase(uc:UploadImageUseCaseImpl) : UploadImageUseCase

}