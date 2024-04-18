package com.dkproject.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.dkproject.domain.usecase.token.GetTokenUseCase
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.theme.SNS_CloneTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity:ComponentActivity() {
    @Inject
    lateinit var getTokenUseCaseImpl : GetTokenUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            val isLoggedIn = !getTokenUseCaseImpl().isNullOrEmpty()
            if(isLoggedIn){
               startActivity(Intent(this@SplashActivity,HomeActivity::class.java).apply {
                   flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
               })
            }else{
                startActivity(Intent(this@SplashActivity,LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                })
            }
        }
        setContent {
            SNS_CloneTheme {
                Surface(modifier=Modifier.fillMaxSize(),color = Color(31,33,37)) {
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

@Composable
fun test(){
    Surface(modifier=Modifier.fillMaxSize(),color = Color(31,33,37)) {
        Column(modifier=Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(200.dp))
            Text(text = stringResource(id = R.string.appName),
                style = MaterialTheme.typography.headlineLarge,
                color = Color(21,239,201))

        }
    }
}

@Preview
@Composable
fun splashPreview(){
    SNS_CloneTheme {
        test()
    }
}