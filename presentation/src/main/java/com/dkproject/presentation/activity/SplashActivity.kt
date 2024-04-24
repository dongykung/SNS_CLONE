package com.dkproject.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.dkproject.domain.usecase.token.GetTokenUseCase
import com.dkproject.domain.usecase.user.GetMyInfoUseCase
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.theme.SNS_CloneTheme
import com.dkproject.presentation.util.Constants
import com.dkproject.presentation.util.showToastMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity:ComponentActivity() {
    @Inject
    lateinit var getTokenUseCaseImpl : GetTokenUseCase
    @Inject
    lateinit var getUserInfoUseCase: GetMyInfoUseCase
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            val isLoggedIn = !getTokenUseCaseImpl().isNullOrEmpty()
            if(isLoggedIn){
                runBlocking {
                    getUserInfoUseCase().onSuccess {
                        Constants.myId = it.id
                        Constants.myProfileUrl=it.profileFilePath.toString()
                        Constants.myName=it.userName
                        startActivity(Intent(this@SplashActivity,HomeActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        })
                    }.onFailure {
                        showToastMessage(this@SplashActivity,"유저 정보를 가져올 수 없습니다")
                        startActivity(Intent(this@SplashActivity,LoginActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        })
                    }
                }
            }else{
                startActivity(Intent(this@SplashActivity,LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                })
            }
        }
        setContent {
            SNS_CloneTheme {
                Surface(modifier=Modifier.fillMaxSize(),color = Color(31,33,37)) {
                        CircularProgressIndicator()
                    Column(modifier=Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(200.dp))
                        Text(text = stringResource(id = R.string.appName),
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color(21,239,201))

                    }
                }
            }
        }
    }
}



