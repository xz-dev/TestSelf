package net.xzos.testself.ui.view

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.xzos.testself.ui.view.question.QuestionPageActivity
import net.xzos.testself.ui.view.question.ReviewMode

private val itemList = listOf(
    Pair("练习", { context: Context ->
        context.startActivity(Intent(context, QuestionPageActivity::class.java).apply {
            putExtra("review", ReviewMode.LEARN.name)
        })
    }),
    Pair("复习", { context: Context ->
        context.startActivity(Intent(context, QuestionPageActivity::class.java).apply {
            putExtra("review", ReviewMode.REVIEW.name)
        })
    }),
    Pair("复习错题", { context: Context ->
        context.startActivity(Intent(context, QuestionPageActivity::class.java).apply {
            putExtra("review", ReviewMode.REVIEW_FAIL.name)
        })
    }),
)

@Preview
@Composable
fun Greeting() {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemList.forEach {
            Card(
                shape = RoundedCornerShape(8.dp),
                backgroundColor = MaterialTheme.colors.surface,
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth()
                    .height(142.dp)
            ) {
                Column(
                    modifier = Modifier.clickable {
                        it.second(context)
                    },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = it.first,
                        style = MaterialTheme.typography.h4
                    )
                }
            }
        }
    }
}