package com.example.cesar_p1_ap2.Nav

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cesar_ap2_p1_at2.ui.Operations.MainScreen
import com.example.cesar_p1_ap2.ui.Operations.OperationsConsult
import com.example.cesar_p1_ap2.ui.Operations.OperationsScreen

@Composable
fun AppNavigation(context: Context,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.MainScreen.route
    ) {
        //Home Screen
        composable(AppScreens.OperationsScreen.route) {
            OperationsScreen(navController = navController)
        }
        composable(AppScreens.OperationsConsult.route) {
            OperationsConsult(navController = navController)
        }
        composable(AppScreens.MainScreen.route) {
            MainScreen(navController = navController)
        }


    }
}