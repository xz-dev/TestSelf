package net.xzos.testself.ui.view.question

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun QuestionCard(viewModel: QuestionViewModel) {
    Column {
        Text(
            modifier = Modifier.padding(top = 60.dp),
            text = viewModel.question.stem
        )
        ChoiceList(viewModel = viewModel)
    }
}

@Composable
fun ChoiceList(viewModel: QuestionViewModel) {
    Column(
        modifier = Modifier.padding(top = 16.dp)
    ) {
        viewModel.question.option.forEach { (num, text) ->
            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = num in viewModel.choiceList,
                    onCheckedChange = { viewModel.setChoice(num) },
                )
                Text(text = "$num. $text", modifier = Modifier.padding(start = 4.dp))
            }
        }
    }
}