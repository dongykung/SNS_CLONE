package com.dkproject.domain.usecase.token

import com.dkproject.domain.repository.TokenRepository

class ClearTokenUseCase(private val tokenRepository: TokenRepository) {
    suspend operator fun invoke(){
        tokenRepository.clearToken()
    }
}