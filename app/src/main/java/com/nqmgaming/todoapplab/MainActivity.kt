package com.nqmgaming.todoapplab

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nqmgaming.todoapplab.presentation.Screen
import com.nqmgaming.todoapplab.presentation.add_todo.AddTodoScreen
import com.nqmgaming.todoapplab.presentation.edit_todo.EditTodoScreen
import com.nqmgaming.todoapplab.presentation.home.HomeScreen
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
                Scaffold(modifier = Modifier.fillMaxSize()) {  innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomeScreen.route,
//                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.HomeScreen.route) {
                            HomeScreen(navController)
                        }
                        composable(Screen.AddTodoScreen.route) {
                            AddTodoScreen(navController = navController)
                        }
                        composable(Screen.EditTodoScreen.route) {
                            EditTodoScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
