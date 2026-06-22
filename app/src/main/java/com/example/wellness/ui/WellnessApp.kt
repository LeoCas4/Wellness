package com.example.wellness.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

enum class AppDestinations(val title: String) {
    Inicio("Hábitos"),
    Explorar("Consejos"),
    Configuracion("Ajustes")
}

@Composable
fun WellnessApp(viewModel: WellnessViewModel = viewModel()) {
    val navController = rememberNavController()
    // Observamos la ruta actual para saber qué botón iluminar
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute == AppDestinations.Inicio.name,
                    onClick = {
                        navController.navigate(AppDestinations.Inicio.name) {
                            // Evita crear un historial infinito de pantallas
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    label = { Text(AppDestinations.Inicio.title) }
                )
                NavigationBarItem(
                    selected = currentRoute == AppDestinations.Explorar.name,
                    onClick = {
                        navController.navigate(AppDestinations.Explorar.name) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.Info, contentDescription = "Explorar") },
                    label = { Text(AppDestinations.Explorar.title) }
                )
                NavigationBarItem(
                    selected = currentRoute == AppDestinations.Configuracion.name,
                    onClick = {
                        navController.navigate(AppDestinations.Configuracion.name) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.Build, contentDescription = "Configuración") },
                    label = { Text(AppDestinations.Configuracion.title) }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppDestinations.Inicio.name,
            modifier = Modifier.padding(innerPadding) // ¡Esto evita que la pantalla tape el menú!
        ) {
            composable(AppDestinations.Inicio.name) { InicioScreen(viewModel) }
            composable(AppDestinations.Explorar.name) { ExplorarScreen(viewModel) }
            composable(AppDestinations.Configuracion.name) { ConfiguracionScreen() }
        }
    }
}