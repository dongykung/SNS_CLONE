package com.dkproject.presentation.util

import android.annotation.SuppressLint
import android.os.Build
import android.view.ViewTreeObserver
import android.view.WindowInsets
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat



@Composable
fun rememberImeState(): State<Boolean> {
    val keyboardState = remember { mutableStateOf(false) }
    val view = LocalView.current
    DisposableEffect(key1 = view) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener{
            val rect = android.graphics.Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight-rect.bottom
            keyboardState.value = if(keypadHeight>screenHeight*0.15){
                true
            }else{
               false
            }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }


    return keyboardState
}