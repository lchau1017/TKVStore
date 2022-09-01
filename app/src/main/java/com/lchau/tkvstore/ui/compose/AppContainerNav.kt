package com.lchau.tkvstore.ui.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lchau.tkvstore.ui.compose.view.StoreScreen

@Composable
fun AppContainerNavHost(
    navController: NavHostController,
    padding: PaddingValues
) = NavHost(
    navController = navController,
    startDestination = Destination.Store.route,
    modifier = androidx.compose.ui.Modifier.padding(padding)
) {
    composable(Destination.Store.route) {
        StoreScreen()
    }
}

sealed class Destination(val route: String) {
    object Store : Destination("store")
}