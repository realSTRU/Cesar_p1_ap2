package com.example.cesar_p1_ap2.Nav

sealed class AppScreens(val route : String) {
    object OperationsScreen: AppScreens("operation_screen")
    object OperationsConsult: AppScreens("operation_consult")

    object MainScreen: AppScreens("main_screen")

}