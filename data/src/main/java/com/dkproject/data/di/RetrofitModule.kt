package com.dkproject.data.di

import com.dkproject.data.retrofit.BoardService
import com.dkproject.data.retrofit.FileService
import com.dkproject.data.retrofit.RetrofitInterceptor
import com.dkproject.data.retrofit.UserService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

val BASE_URL = "http://192.168.35.158:8080"

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideOkhttpClient(retrofitInterceptor: RetrofitInterceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(retrofitInterceptor)
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val converterFactory = Json{
            ignoreUnknownKeys=true
        }.asConverterFactory("application/json; charset=UTF8".toMediaType())
        return Retrofit.Builder()
            .baseUrl("${BASE_URL}/")

            //kotlin seriallization 을 사용하여 Json 응답 파싱 후 kotlin 객체로 변
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit):UserService{
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideFileService(retrofit: Retrofit):FileService{
        return retrofit.create(FileService::class.java)
    }

    @Provides
    @Singleton
    fun provideBoardService(retrofit: Retrofit):BoardService{
        return retrofit.create(BoardService::class.java)
    }


}