package com.dkproject.presentation.ui.components

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AddToPhotos
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dkproject.presentation.activity.WritingActivity
import com.dkproject.presentation.util.PermissionDialog

enum class HomeRoute(
    val route: String,
    val description: String,
    val selected: ImageVector,
    val noselected: ImageVector
) {
    BOARD(
        route = "BoardScreen",
        description = "BoardScreen",
        selected = Icons.Filled.Home,
        noselected = Icons.Outlined.Home
    ),
    WRITING(
        route = "WritingScreen",
        description = "Writing",
        selected = Icons.Outlined.AddToPhotos,
        noselected = Icons.Outlined.AddToPhotos
    ),
    SETTING(
        route = "SettingScreen",
        description = "Setting",
        selected = Icons.Filled.Settings,
        noselected = Icons.Outlined.Settings
    )
}


@Composable
fun CustomBottomBar(navController: NavController) {
    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute: HomeRoute = navBackStackEntry?.destination?.route.let { currentRoute ->
        HomeRoute.entries.find { route ->
            currentRoute == route.route
        }
    } ?: HomeRoute.BOARD
    var permissionDialog by remember {
        mutableStateOf(false)
    }
    val writePostLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) {
           if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU && (
                   ContextCompat.checkSelfPermission(context, READ_MEDIA_IMAGES)==PERMISSION_GRANTED||
                   ContextCompat.checkSelfPermission(context, READ_MEDIA_VIDEO)== PERMISSION_GRANTED
                   )
               ){
               context.startActivity(Intent(context,WritingActivity::class.java))
           }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.UPSIDE_DOWN_CAKE&&
               ContextCompat.checkSelfPermission(context, READ_MEDIA_VISUAL_USER_SELECTED)== PERMISSION_GRANTED){
               context.startActivity(Intent(context,WritingActivity::class.java))
           }else if(ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE)== PERMISSION_GRANTED){
               context.startActivity(Intent(context,WritingActivity::class.java))
           }else{
                permissionDialog=true
           }
        }
    if(permissionDialog){
        PermissionDialog(context = context) {
            permissionDialog=it
        }
    }
    NavigationBar {
        val navigationItem = listOf(HomeRoute.BOARD, HomeRoute.WRITING, HomeRoute.SETTING)

        navigationItem.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = currentRoute == item,
                onClick = {
                    if (item == HomeRoute.WRITING) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                            writePostLauncher.launch(
                                arrayOf(
                                    READ_MEDIA_IMAGES, READ_MEDIA_VIDEO,
                                    READ_MEDIA_VISUAL_USER_SELECTED
                                )
                            )
                        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
                            writePostLauncher.launch(arrayOf(READ_MEDIA_IMAGES, READ_MEDIA_VIDEO))
                        }else{
                            writePostLauncher.launch(arrayOf(READ_EXTERNAL_STORAGE))
                        }
                    } else {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let {
                                popUpTo(it) {
                                    saveState = true
                                }
                            }
                            this.launchSingleTop = true
                            this.restoreState = true
                        }
                    }
                },
                icon = {
                    if (item == currentRoute) Icon(
                        imageVector = item.selected,
                        contentDescription = item.description
                    )
                    else Icon(imageVector = item.noselected, contentDescription = item.description)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.White,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}