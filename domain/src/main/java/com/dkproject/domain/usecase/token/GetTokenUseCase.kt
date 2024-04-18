package com.dkproject.domain.usecase.token

import com.dkproject.domain.repository.TokenRepository




class GetTokenUseCase(private val tokenRepository: TokenRepository) {
     suspend operator fun invoke(): String? {
        return tokenRepository.getToken()
    }
}