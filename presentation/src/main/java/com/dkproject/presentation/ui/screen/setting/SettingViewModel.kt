package com.dkproject.presentation.ui.screen.setting

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkproject.domain.model.User
import com.dkproject.domain.usecase.token.ClearTokenUseCase
import com.dkproject.domain.usecase.user.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val clearTokenUseCase: ClearTokenUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SettingUiState())
    val state: StateFlow<SettingUiState> = _state.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            getUserInfoUseCase().onSuccess { user ->
                Log.d("SettingViewModel", user.toString())
                updateState(
                    name = user.userName,
                    url = user.profileFilePath.toString(),
                    stmsg = user.extraUserInfo
                )
            }.onFailure {

            }
        }
    }

    fun updateState(name: String, url: String, stmsg: String) {
        _state.update { it.copy(profileImageUrl = url, username = name, statusMessage = stmsg) }
    }

    fun logOut(moveToLogin: () -> Unit) {
        viewModelScope.launch {
            clearTokenUseCase()
            moveToLogin()
        }
    }
}


data class SettingUiState(
    val profileImageUrl: String? = null,
    val username: String = "",
    val statusMessage: String = ""
)