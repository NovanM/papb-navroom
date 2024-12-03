package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myapplication.ui.NoteViewModel
import com.example.myapplication.ui.screens.DetailScreen
import com.example.myapplication.ui.screens.HomeScreen

@Composable
fun AppNavGraph(navController: NavHostController, viewModel: NoteViewModel) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController, viewModel) }
        composable("detail") { DetailScreen(null, navController, viewModel) }
        composable(
            "detail/{noteId}",
            arguments = listOf(navArgument("noteId") { nullable = true })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toInt()
            DetailScreen(noteId, navController,viewModel)
        }
    }
}
