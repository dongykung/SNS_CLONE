package com.dkproject.domain.usecase.token

import com.dkproject.domain.repository.TokenRepository

class SetTokenUseCase(private val tokenRepository: TokenRepository) {
    suspend operator fun invoke(token:String){
        tokenRepository.setToken(token)
    }
}