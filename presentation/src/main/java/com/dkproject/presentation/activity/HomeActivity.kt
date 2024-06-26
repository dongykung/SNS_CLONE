package com.dkproject.presentation.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.dkproject.domain.model.ACTION_POSTED
import com.dkproject.presentation.navigation.HomeNavigation
import com.dkproject.presentation.ui.screen.board.BoardViewModel
import com.dkproject.presentation.ui.screen.setting.SettingViewModel
import com.dkproject.presentation.ui.theme.SNS_CloneTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    private val boardViewModel: BoardViewModel by viewModels()
    private val settingViewModel:SettingViewModel by viewModels()
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == ACTION_POSTED) {
                boardViewModel.load()
               if(settingViewModel.state.value.userId!=null){
                   settingViewModel.getMyBoard(settingViewModel.state.value.userId!!)
               }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SNS_CloneTheme {
                HomeNavigation(boardViewModel,settingViewModel, onBoardDelete = {id->
                    boardViewModel.onBoardDelete(id, deleteSuccess = {
                        Log.d("deletetest","dete")
                        settingViewModel.onBoardDelete(id)
                    })

                })
            }
        }
        ContextCompat.registerReceiver(
            this,
            receiver,
            IntentFilter(ACTION_POSTED),
            ContextCompat.RECEIVER_NOT_EXPORTED)
    }
}