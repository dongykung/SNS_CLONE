package com.dkproject.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.dkproject.presentation.ui.screen.userprofile.UserProfileScreen
import com.dkproject.presentation.ui.screen.userprofile.UserProfileViewModel
import com.dkproject.presentation.ui.theme.SNS_CloneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SNS_CloneTheme {
                val userId = intent.getLongExtra("userId", 0)
                val viewModel: UserProfileViewModel by viewModels()
                viewModel.load(userId, this)
                UserProfileScreen(viewModel = viewModel, onBackClick = { finish() })
            }
        }
    }
}