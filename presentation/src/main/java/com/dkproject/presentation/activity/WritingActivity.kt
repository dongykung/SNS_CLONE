package com.dkproject.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dkproject.presentation.navigation.WritingNavigation
import com.dkproject.presentation.ui.theme.SNS_CloneTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WritingActivity:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SNS_CloneTheme {
                WritingNavigation(writeExit = {
                    finish()
                },
                    postDone = {finish()})
            }
        }
    }
}