package net.xzos.testself.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import net.xzos.testself.ui.theme.Grey
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
    var pageIndex by remember { mutableStateOf(0) }
    val items = listOf(
        Triple("背题", Icons.Filled.Description, {
            pageIndex = 0
        }),
        Triple("题库", Icons.Filled.Book, {
            pageIndex = 1
        }),
        Triple("设置", Icons.Filled.Settings, {
            pageIndex = 2
        })
    )
    Scaffold(
        bottomBar = { BottomNav(items) }
    ) {
        when (pageIndex) {
            0 -> Greeting()
            1 -> PoolView()
            else -> Greeting()
        }
    }
}

@Composable
fun BottomNav(items: List<Triple<String, ImageVector, () -> Unit>>) {
    var selectedItem by remember { mutableStateOf(0) }
    BottomNavigation(contentColor = Grey) {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = { Icon(item.second, contentDescription = null) },
                label = { Text(item.first) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    item.third()
                }
            )
        }
    }
}
