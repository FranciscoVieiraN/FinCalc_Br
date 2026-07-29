package com.financalcbr.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.financalcbr.app.ui.navigation.FinanCalcBRNavHost
import com.financalcbr.app.ui.navigation.FinanCalcBRRoute
import com.financalcbr.app.ui.navigation.NavDrawer
import com.financalcbr.app.ui.theme.FinanCalcBRTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinanCalcBRTheme {
                MainScreen()
            }
        }
    }
}


@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val destination = navBackStackEntry?.destination

    val currentTitle = when {
        destination?.hasRoute<FinanCalcBRRoute.Calculator>() == true -> "Calculadora"
        destination?.hasRoute<FinanCalcBRRoute.CompoundInterest>() == true -> "Juros Compostos"
        destination?.hasRoute<FinanCalcBRRoute.SimpleInterest>() == true -> "Juros Simples"
        destination?.hasRoute<FinanCalcBRRoute.Overtime>()== true -> "Hora Extra"
        destination?.hasRoute<FinanCalcBRRoute.NetSalary>() == true -> "Salário Líquido"
        destination?.hasRoute<FinanCalcBRRoute.Vacation>() == true -> "Calculadora de Férias"
        else -> "FinanCalc BR"
    }


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavDrawer { routeObject ->
                scope.launch { drawerState.close() }
                navController.navigate(routeObject) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(currentTitle) },
                    modifier = Modifier.padding(top = 5.dp),
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                FinanCalcBRNavHost(
                    navController = navController
                )
            }
        }
    }
}

