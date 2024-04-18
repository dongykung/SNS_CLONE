package com.dkproject.presentation.ui.screen.login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkproject.domain.usecase.signup.SignUpUseCase
import com.dkproject.presentation.util.ParseErrorResponse
import com.dkproject.presentation.util.showToastMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {
    companion object{
        const val TAG="SignUpViewModel"
    }
    var id by mutableStateOf("")
    var userName by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordConfirm by mutableStateOf("")
    var valid by mutableStateOf(false)
    fun updateId(newId: String) {
        id = newId
    }

    fun updateName(name: String) {
        userName = name
        updateValid()
    }

    fun updatePassword(pw: String) {
        password = pw
        updateValid()
    }

    fun updatePasswordConfirm(pw: String) {
        passwordConfirm = pw
        updateValid()
    }

    fun updateValid() {
        valid = id.isNotEmpty() && userName.isNotEmpty() && password.isNotEmpty() &&
                passwordConfirm.isNotEmpty() && password == passwordConfirm
    }

    fun signUp(context:Context,moveToLogin:()->Unit){
        viewModelScope.launch {
            signUpUseCase(id=id,name=userName, pw = password).onSuccess {
                showToastMessage(context,"회원가입에 성공하였습니다.")
                moveToLogin()
            }.onFailure {
                if(it is HttpException){
                    val errorMessage= it.response()?.errorBody()?.string()
                    val message = ParseErrorResponse(errorMessage?:"")
                    showToastMessage(context,message)
                }else{
                    Log.d(TAG, it.message.toString())
                    showToastMessage(context,"회원가입에 실패하였습니다.")
                }
            }
        }
    }
}