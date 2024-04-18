package com.dkproject.data.repository.token

import com.dkproject.data.datastore.UserDataStore
import com.dkproject.domain.repository.TokenRepository
import javax.inject.Inject


class TokenRepositoryImpl @Inject constructor(
    private val userDataStore: UserDataStore
) :TokenRepository{
    override suspend fun getToken():String? {
        return userDataStore.getToken()
    }

    override suspend fun setToken(token: String) {
        userDataStore.setToken(token)
    }

    override suspend fun clearToken() {
        userDataStore.clearToken()
    }
}