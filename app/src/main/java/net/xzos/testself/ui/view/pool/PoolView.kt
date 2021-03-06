package net.xzos.testself.ui.view.pool

import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.NextPlan
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import net.xzos.testself.core.manager.addPoolLauncher
import net.xzos.testself.ui.theme.Background

val viewModel = PoolViewModel()

@Preview
@Composable
fun PoolView() {
    viewModel.renew()
    val launcherFun = addPoolLauncher { viewModel.renew() }
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Background,
                title = {
                    Text("题库（${viewModel.currentPool?.name}）")
                },
                actions = {
                    IconButton(onClick = {
                        launcherFun()
                    }) {
                        Icon(Icons.Default.NoteAdd, null)
                    }
                    Box {
                        var showMenu by remember { mutableStateOf(false) }
                        IconButton(onClick = { showMenu = true }) {
                            Icon(Icons.Default.NextPlan, null)
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            val poolList = viewModel.poolList
                            poolList.forEach {
                                DropdownMenuItem(onClick = {
                                    viewModel.setPool(it)
                                }) {
                                    Text(it.name)
                                }
                            }
                        }
                    }
                    Box {
                        var showMenu by remember { mutableStateOf(false) }
                        IconButton(onClick = { showMenu = true }) {
                            Icon(Icons.Default.MoreVert, null)
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(onClick = {
                                viewModel.reviewPool()
                            }) {
                                Text("再次复习")
                            }
                        }
                    }
                },
            )
        }
    ) {
        QuestionList(viewModel.questionList)
    }
}