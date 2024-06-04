package com.nqmgaming.todoapplab.presentation.home

import android.annotation.SuppressLint
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            Text(text = "Home Screen")
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {}
            ) {
                Text(text = "Add Todo")
            }
        }
    ) {
        TextField(value = "", onValueChange = {})
    }
}