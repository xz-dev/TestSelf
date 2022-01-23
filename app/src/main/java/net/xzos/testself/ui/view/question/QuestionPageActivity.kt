package net.xzos.testself.ui.view.question

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.runBlocking
import net.xzos.testself.core.manager.PoolManager
import net.xzos.testself.ui.BaseTheme
import net.xzos.testself.ui.theme.Background
import net.xzos.testself.ui.view.pool.viewModel

class QuestionPageActivity : ComponentActivity() {

    private lateinit var poolViewModel: PoolViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        poolViewModel = PoolViewModel(
            runBlocking { PoolManager.getEnablePool() } ?: kotlin.run {
                super.finish()
                return
            },
            intent.getStringExtra("review")?.let { ReviewMode.valueOf(it) } ?: kotlin.run {
                super.finish()
                return
            }
        )
        if (poolViewModel.questionDataList.isEmpty()) {
            finish()
        }
        setContent {
            val questionViewModel = QuestionViewModel(poolViewModel.question ?: kotlin.run {
                super.finish()
                return@setContent
            })
            BaseTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            backgroundColor = Background,
                            title = {
                                Text("题库（${viewModel.currentPool?.name}）")
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    onBackPressed()
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = "Back",
                                    )
                                }
                            }
                        )
                    },
                    floatingActionButton = {
                        with(poolViewModel) {
                            ExtendedFloatingActionButton(
                                text = { Text(text = floatingActionButtonText) },
                                onClick = {
                                    questionViewModel.checkAnswer()
                                    floatingActionButtonClick()
                                },
                                icon = { Icon(floatingActionButtonIcon, "提交") }
                            )
                        }
                    },
                    isFloatingActionButtonDocked = true,
                    bottomBar = {
                        BottomAppBar(
                            backgroundColor = Color.Black,
                            cutoutShape = MaterialTheme.shapes.small.copy(
                                CornerSize(percent = 50)
                            )
                        ) {
                            Row {
                                TextButton(
                                    enabled = poolViewModel.haveLastQuestion,
                                    onClick = {
                                        poolViewModel.toLastQuestion()
                                        questionViewModel.checkAnswer()
                                    }) {
                                    Text(text = "上一题")
                                }
                                TextButton(
                                    enabled = poolViewModel.haveNextQuestion,
                                    onClick = { poolViewModel.toNextQuestion() }) {
                                    Text(text = "下一题")
                                }
                            }
                        }
                    }
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column {
                            QuestionCard(questionViewModel)
                        }
                    }
                }
            }
        }
    }
}