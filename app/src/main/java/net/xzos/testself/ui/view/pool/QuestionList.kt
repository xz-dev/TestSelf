package net.xzos.testself.ui.view.pool

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import net.xzos.testself.core.database.table.QuestionEntity


@Composable
fun QuestionList(questions: List<QuestionEntity>) {
    LazyColumn {
        items(questions) { question ->
        }
    }
}

