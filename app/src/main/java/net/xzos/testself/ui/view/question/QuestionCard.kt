package net.xzos.testself.ui.view.question

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun QuestionCard(viewModel: QuestionViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = viewModel.question.stem)
        ChoiceList(viewModel = viewModel)
        with(viewModel.pageData) {
            answerString?.apply { Text(text = this) }
            explain?.apply { Text(text = this) }
        }
    }
}

@Composable
fun ChoiceList(viewModel: QuestionViewModel) {
    Column(
        modifier = Modifier.padding(top = 16.dp)
    ) {
        viewModel.pageData.optionList.forEach { item ->
            Row(
                modifier = Modifier
                    .defaultMinSize(minHeight = 30.dp)
                    .fillMaxWidth()
                    .clickable { viewModel.clickChoice(item.key) },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (viewModel.pageData.isRadio) RadioButton(
                    selected = item.selected,
                    onClick = { viewModel.clickChoice(item.key) },
                    colors = RadioButtonDefaults.colors(
                        item.color ?: MaterialTheme.colors.secondary
                    )
                )
                else Checkbox(
                    checked = item.selected,
                    onCheckedChange = { viewModel.clickChoice(item.key) },
                )
                Text(
                    text = "${item.key}. ${item.text}",
                    modifier = Modifier.padding(start = 4.dp),
                    color = item.color ?: Color.Unspecified
                )
            }
        }
    }
}