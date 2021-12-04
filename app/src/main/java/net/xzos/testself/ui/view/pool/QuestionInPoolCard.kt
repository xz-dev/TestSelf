package net.xzos.testself.ui.view.pool

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.xzos.testself.core.database.table.QuestionEntity

@Composable
fun QuestionInPoolCard(question: QuestionEntity) {
    Column {
        Text(
            text = question.stem
        )
        Column(
            modifier = Modifier.padding(start = 16.dp, top = 4.dp),
        ) {
            var i = 1
            question.option.forEach { (num, text) ->
                if (num in question.answer) {
                    Text(
                        text = "${if (question.answer.size > 1) "$i. " else ""}$text",
                        fontWeight = FontWeight.Bold
                    )
                    i += 1
                }
            }
        }
    }
}