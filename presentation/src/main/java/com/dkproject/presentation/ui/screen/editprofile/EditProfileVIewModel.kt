package com.dkproject.presentation.ui.screen.editprofile

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkproject.domain.model.Image
import com.dkproject.domain.usecase.file.GetImageUseCase
import com.dkproject.domain.usecase.file.UploadImageUseCase
import com.dkproject.domain.usecase.user.UpdateMyInfoUseCase
import com.dkproject.presentation.ui.screen.setting.SettingUiState
import com.dkproject.presentation.util.showToastMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditProfileVIewModel @Inject constructor(
    private val updateMyInfoUseCase: UpdateMyInfoUseCase,
) : ViewModel() {
    companion object{
        const val TAG="EditProfileVIewModel"
    }
    private val _state = MutableStateFlow(SettingUiState())
    val state: StateFlow<SettingUiState> = _state
    var profileChange by mutableStateOf(false)

    fun updateState(profile: String, name: String, status: String) {
        _state.update {
            it.copy(
                profileImageUrl = profile,
                username = name,
                statusMessage = status
            )
        }
    }

    fun updateProfile(profile: String) {
        _state.update { it.copy(profileImageUrl = profile) }
    }

    fun updatename(name: String) {
        _state.update { it.copy(username = name) }
    }

    fun updatestatus(status: String) {
        _state.update { it.copy(statusMessage = status) }
    }

    fun updateUserInfo(context: Context,updateCompleted:()->Unit) {
        viewModelScope.launch {
            Log.d(TAG, "updateUserInfo")
                updateMyInfoUseCase(
                    username = state.value.username,
                    extrauserinfo = state.value.statusMessage,
                    profilefilepath = state.value.profileImageUrl.toString(),
                    profileChange = profileChange
                ).onSuccess {
                    updateCompleted()
                }.onFailure {
                    Log.d(TAG, "fail")

                }

        }
    }


}