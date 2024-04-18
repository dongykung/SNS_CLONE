package com.dkproject.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dkproject.presentation.ui.screen.writing.WriteViewModel
import com.dkproject.presentation.ui.screen.writing.WritingImageScreen
import com.dkproject.presentation.ui.screen.writing.WritingTextScreen


enum class WritingRoute(val route:String){
    WRITEIMAGE(route = "WriteImageScreen"),
    WRITETEXT(route = "WriteTextScreen")
}

@Composable
fun WritingNavigation(navHostController: NavHostController= rememberNavController(),
                      writeExit:()->Unit,
                      postDone:()->Unit) {
    val viewModel:WriteViewModel = hiltViewModel()
    NavHost(navController = navHostController, startDestination = WritingRoute.WRITEIMAGE.route) {
        composable(route = WritingRoute.WRITEIMAGE.route){
            WritingImageScreen(viewModel = viewModel, writeexit = writeExit){
                navHostController.navigate(route = WritingRoute.WRITETEXT.route)
            }
        }

        composable(route = WritingRoute.WRITETEXT.route){
            WritingTextScreen(viewModel = viewModel, onBackClick = {navHostController.popBackStack()},
                postDone = postDone)
        }

    }
}