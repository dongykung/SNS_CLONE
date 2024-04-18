package com.dkproject.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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


@Composable
fun HomeNavigation(
    boardViewModel: BoardViewModel,
    navController: NavHostController = rememberNavController(),
                  ) {
    Scaffold(
        bottomBar = {
            CustomBottomBar(navController = navController)
        }
    ) {innerPadding->
        HomeScreenNavigation(navController = navController, padding = innerPadding,boardViewModel)
    }
}

@Composable
fun HomeScreenNavigation(
    navController: NavHostController,
    padding:PaddingValues,
    boardViewModel: BoardViewModel
){


    NavHost(navController = navController, startDestination = HomeRoute.BOARD.route) {
        composable(route = HomeRoute.BOARD.route){
            BoardScreen(modifier=Modifier.padding(padding),boardViewModel)
        }
        composable(route = HomeRoute.SETTING.route){
            SettingScreen()
        }

    }
}