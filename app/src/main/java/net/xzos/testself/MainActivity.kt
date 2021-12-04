package net.xzos.testself

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import net.xzos.testself.ui.theme.TestSelfTheme
import net.xzos.testself.ui.view.Greeting
import net.xzos.testself.ui.view.pool.PoolView

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseView(activity = this) {
                DefaultPreview()
            }
        }
    }
}

@Composable
fun BaseView(
    activity: ComponentActivity,
    content: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = { BottomNav(activity) }
    ) {
        content()
    }
}

@Composable
fun BottomNav(activity: ComponentActivity) {
    val items = listOf(
        Triple("背题", Icons.Filled.Description, {
            activity.setContent {
                BaseView(activity = activity) {
                    Greeting()
                }
            }
        }),
        Triple("题库", Icons.Filled.Book, {
            activity.setContent {
                BaseView(activity = activity) {
                    PoolView()
                }
            }
        }),
        Triple("设置", Icons.Filled.Settings, {})
    )
    var selectedItem by remember { mutableStateOf(0) }
    BottomNavigation {
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

@Composable
fun DefaultPreview() {
    TestSelfTheme {
        Greeting()
    }
}