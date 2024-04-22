package com.dkproject.data.di


import com.dkproject.data.RemoteDataSource.UserDataSource
import com.dkproject.data.RemoteDataSource.UserDataSourceImpl
import com.dkproject.data.repository.token.TokenRepositoryImpl
import com.dkproject.data.repository.user.UserRepositoryImpl
import com.dkproject.data.usecase.login.LoginUseCaseImpl
import com.dkproject.data.usecase.signup.SignUpUseCaseImpl
import com.dkproject.data.usecase.user.GetMyInfoUseCaseImpl
import com.dkproject.data.usecase.user.GetUserInfoUseCaseImpl
import com.dkproject.data.usecase.user.UpdateMyInfoUseCaseImpl
import com.dkproject.domain.repository.TokenRepository
import com.dkproject.domain.repository.UserRepository
import com.dkproject.domain.usecase.login.LoginUseCase
import com.dkproject.domain.usecase.signup.SignUpUseCase
import com.dkproject.domain.usecase.token.ClearTokenUseCase
import com.dkproject.domain.usecase.token.GetTokenUseCase
import com.dkproject.domain.usecase.token.SetTokenUseCase
import com.dkproject.domain.usecase.user.GetMyInfoUseCase
import com.dkproject.domain.usecase.user.GetUserInfoUseCase
import com.dkproject.domain.usecase.user.UpdateMyInfoUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object  UserModule {
    @Provides
    fun providesGetTokenUseCase(tokenRepository: TokenRepository):GetTokenUseCase{
        return GetTokenUseCase(tokenRepository)
    }

    @Provides
    fun providesSetTokenUseCase(tokenRepository: TokenRepository):SetTokenUseCase{
        return SetTokenUseCase(tokenRepository)
    }

    @Provides
    fun providesClearTokenUseCase(tokenRepository: TokenRepository):ClearTokenUseCase{
        return ClearTokenUseCase(tokenRepository)
    }

}

@Module
@InstallIn(SingletonComponent::class)
abstract class BindUserMoudle(){
    @Binds
    abstract fun bindTokenRepository(uc:TokenRepositoryImpl):TokenRepository

    @Binds
    abstract fun bindLoginUseCase(uc:LoginUseCaseImpl):LoginUseCase

    @Binds
    abstract fun bindSingUpUseCase(uc:SignUpUseCaseImpl):SignUpUseCase

    @Binds
    abstract fun bindUserInfoUpUseCase(uc:GetMyInfoUseCaseImpl):GetMyInfoUseCase

    @Binds
    abstract fun bindUpdateUserInfoUpUseCase(uc:UpdateMyInfoUseCaseImpl):UpdateMyInfoUseCase

    @Binds
    abstract fun bindUserDataSource(uc:UserDataSourceImpl):UserDataSource

    @Binds
    abstract fun bindUserRepository(uc:UserRepositoryImpl):UserRepository

    @Binds
    abstract fun bindGetUserInfoUseCase(uc : GetUserInfoUseCaseImpl):GetUserInfoUseCase
}