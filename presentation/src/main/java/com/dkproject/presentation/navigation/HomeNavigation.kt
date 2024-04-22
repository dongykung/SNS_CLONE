package com.dkproject.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dkproject.presentation.ui.components.CustomBottomBar
import com.dkproject.presentation.ui.components.HomeRoute
import com.dkproject.presentation.ui.screen.board.BoardScreen
import com.dkproject.presentation.ui.screen.board.BoardViewModel
import com.dkproject.presentation.ui.screen.home.HomeScreen
import com.dkproject.presentation.ui.screen.setting.SettingScreen
import com.dkproject.presentation.ui.screen.setting.SettingViewModel


@Composable
fun HomeNavigation(
    boardViewModel: BoardViewModel,
    settingViewModel: SettingViewModel,
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        bottomBar = {
            CustomBottomBar(navController = navController)
        }
    ) { innerPadding ->
        HomeScreenNavigation(
            navController = navController,
            padding = innerPadding,
            boardViewModel = boardViewModel,
            settingViewModel = settingViewModel
        )
    }
}

@Composable
fun HomeScreenNavigation(
    navController: NavHostController,
    padding: PaddingValues,
    boardViewModel: BoardViewModel,
    settingViewModel: SettingViewModel
) {

    val context = LocalContext.current
    NavHost(navController = navController, startDestination = HomeRoute.BOARD.route) {
        composable(route = HomeRoute.BOARD.route) {
            BoardScreen(modifier = Modifier.padding(padding), boardViewModel, onClickUser = {

            })
        }
        composable(route = HomeRoute.SETTING.route) {
            //val viewModel : SettingViewModel = hiltViewModel()
            SettingScreen(settingViewModel)
        }

    }
}