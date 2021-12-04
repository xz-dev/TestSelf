package net.xzos.testself.ui.view

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import net.xzos.testself.ui.view.question.QuestionPageActivity

@Preview
@Composable
fun Greeting() {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Alfred Sisley")
        Text("3 minutes ago")
        Button(onClick = {
            context.startActivity(Intent(context, QuestionPageActivity::class.java))
        }) {
            Text(text = "练习")
        }
    }
}