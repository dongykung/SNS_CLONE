package com.dkproject.presentation.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dkproject.presentation.ui.screen.editprofile.EditProfileScreen
import com.dkproject.presentation.ui.screen.editprofile.EditProfileVIewModel
import com.dkproject.presentation.ui.theme.SNS_CloneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileActivity:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SNS_CloneTheme {
                val profileImageUrl = intent.getStringExtra("profile")?:""
                val username = intent.getStringExtra("name")?:""
                val status = intent.getStringExtra("status")?:""
                val viewModel:EditProfileVIewModel = viewModel()
                viewModel.updateState(profile=profileImageUrl,name=username, status = status)
                EditProfileScreen(viewModel, onBackClick = {finish()}){
                    setResult(Activity.RESULT_OK, Intent())
                    finish()
                }
            }
        }
    }
}