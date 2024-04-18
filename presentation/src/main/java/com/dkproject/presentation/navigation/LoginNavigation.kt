package com.dkproject.presentation.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dkproject.presentation.activity.HomeActivity
import com.dkproject.presentation.ui.screen.login.LoginScreen
import com.dkproject.presentation.ui.screen.login.SignUpScreen

enum class LoginRoute(val route:String){
    LOGIN("LoginScreen"),
    SIGN_UP("SignUpScreen")
}


@Composable
fun LoginNavigation(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = LoginRoute.LOGIN.route) {
        composable(route = LoginRoute.LOGIN.route){
            LoginScreen(moveToSignUp = {navController.navigate(route = LoginRoute.SIGN_UP.route)}){
                context.startActivity(Intent(context,HomeActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                })
            }
        }
        composable(route = LoginRoute.SIGN_UP.route){
            SignUpScreen(onBackClick = {navController.popBackStack()}){
                navController.popBackStack()
            }
        }
    }
}