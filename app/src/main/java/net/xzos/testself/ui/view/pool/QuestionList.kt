package net.xzos.testself.ui.view.pool

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.xzos.testself.core.database.table.QuestionEntity


@Composable
fun QuestionList(questions: List<QuestionEntity>) {
    LazyColumn(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        itemsIndexed(questions) { index, question ->
            QuestionInPoolCard(question)
            if (index < questions.size - 2)
                Divider()
        }
    }
}