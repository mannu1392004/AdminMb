package com.example.adminmb.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.adminmb.viewmodel.Viewmodel

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val viewmodel = Viewmodel()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute == Screen.Main.name,
                    onClick = {
                        if (currentRoute != Screen.Main.name) {
                            navController.navigate(Screen.Main.name) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home"
                        )
                    }
                )

                NavigationBarItem(
                    selected = currentRoute == Screen.Plans.name,
                    onClick = {
                        if (currentRoute != Screen.Plans.name) {
                            navController.navigate(Screen.Plans.name) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Plans"
                        )
                    }
                )
            }
        }
    ) { padding ->
        Surface(modifier = Modifier.padding(padding)) {
            NavHost(navController = navController, startDestination = Screen.Main.name) {
                composable(route = Screen.Main.name) {
                    UserScreen(navController,viewmodel)
                }
                composable(route = Screen.Plans.name) {
                    PlanScreen(navController,viewmodel)
                }
            }
        }
    }
}
