package com.lchau.tkvstore.ui.compose

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.lchau.tkvstore.R

@Composable
fun AppContainer() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(R.string.app_name)) })
        },
        content = { padding ->
            AppContainerNavHost(
                navController = rememberNavController(),
                padding = padding
            )
        }
    )
}