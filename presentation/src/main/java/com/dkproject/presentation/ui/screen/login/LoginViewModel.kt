package com.dkproject.presentation.ui.screen.login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkproject.domain.usecase.login.LoginUseCase
import com.dkproject.domain.usecase.token.GetTokenUseCase
import com.dkproject.domain.usecase.token.SetTokenUseCase
import com.dkproject.presentation.R
import com.dkproject.presentation.util.Constants
import com.dkproject.presentation.util.ParseErrorResponse
import com.dkproject.presentation.util.showToastMessage
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val setTokenUseCase: SetTokenUseCase
) : ViewModel() {
    companion object {
        const val TAG = "LoginViewModel"
    }

    var id by mutableStateOf("")
    var password by mutableStateOf("")

    fun updateId(newId: String) {
        id = newId
    }

    fun updatePw(pw: String) {
        password = pw
    }


    fun login(context:Context,moveToHome:()->Unit) {
        viewModelScope.launch {
            loginUseCase(id = id, pw = password).onSuccess {
                setTokenUseCase(it.data)
                Constants.myToken = it.data
                moveToHome()
            }.onFailure {
                if(it is HttpException){
                    val errorMessage= it.response()?.errorBody()?.string()
                    val message = ParseErrorResponse(errorMessage?:"")
                    showToastMessage(context,message)
                }else{
                    showToastMessage(context, "로그인에 실패하였습니다")
                }
            }
        }
    }


}


