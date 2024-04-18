package com.dkproject.presentation.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.dkproject.domain.model.ACTION_POSTED
import com.dkproject.presentation.navigation.HomeNavigation
import com.dkproject.presentation.ui.screen.board.BoardViewModel
import com.dkproject.presentation.ui.theme.SNS_CloneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    private val boardViewModel: BoardViewModel by viewModels()
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == ACTION_POSTED) {
                boardViewModel.load()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SNS_CloneTheme {
                HomeNavigation(boardViewModel)
            }
        }
        ContextCompat.registerReceiver(
            this,
            receiver,
            IntentFilter(ACTION_POSTED),
            ContextCompat.RECEIVER_NOT_EXPORTED)
    }
}