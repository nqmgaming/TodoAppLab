package com.nqmgaming.todoapplab

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nqmgaming.todoapplab.presentation.Screen
import com.nqmgaming.todoapplab.presentation.add_todo.components.AddTodoScreen
import com.nqmgaming.todoapplab.presentation.edit_todo.components.EditTodoScreen
import com.nqmgaming.todoapplab.presentation.home.components.HomeScreen
import com.nqmgaming.todoapplab.presentation.theme.TodoAppLabTheme
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoAppLabTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomeScreen.route,
                    ) {
                        composable(route = Screen.HomeScreen.route) {
                            HomeScreen(navController)
                        }
                        composable(route = Screen.AddTodoScreen.route + "?todoId={todoId}",
                            arguments = listOf(
                                navArgument("todoId")
                                {
                                    type = NavType.LongType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            AddTodoScreen(navController = navController)
                        }
                        composable(route = Screen.EditTodoScreen.route + "?todoId={todoId}",
                            arguments = listOf(
                                navArgument("todoId")
                                {
                                    type = NavType.LongType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            EditTodoScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
