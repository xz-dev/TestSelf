package net.xzos.testself.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.xzos.testself.ui.theme.Background
import net.xzos.testself.ui.view.About
import net.xzos.testself.ui.view.Greeting
import net.xzos.testself.ui.view.pool.PoolView

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseTheme {
                BaseView()
            }
        }
    }
}

@Composable
fun BaseView() {
    val items = listOf(
        Triple("learn", "背题", Icons.Filled.Description),
        Triple("pool", "题库", Icons.Filled.Book),
        Triple("about", "关于", Icons.Filled.Settings)
    )
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNav(items, navController) }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = items.first().first,
            Modifier.padding(innerPadding)
        ) {
            composable("learn") { Greeting() }
            composable("pool") { PoolView() }
            composable("about") { About() }
        }
    }
}

@Composable
fun BottomNav(items: List<Triple<String, String, ImageVector>>, navController: NavHostController) {
    var selectedItem by remember { mutableStateOf(0) }
    BottomNavigation(backgroundColor = Background) {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = { Icon(item.third, contentDescription = null) },
                label = { Text(item.second) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.first)
                }
            )
        }
    }
}