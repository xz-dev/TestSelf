package net.xzos.testself.ui.view.question

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

class QuestionPageData {
    var isRadio by mutableStateOf(false)
    var optionList by mutableStateOf(listOf<OptionItem>())
    var answerString by mutableStateOf<String?>(null)
    var explain by mutableStateOf<String?>(null)

    fun setChoiceList(questionData: QuestionData) {
        optionList = questionData.question.option.map {
            OptionItem(it.key, it.value, questionData.answerList.contains(it.key), null)
        }
    }

    fun renewView(questionData: QuestionData) {
        with(questionData) {
            isRadio = question.answer.size <= 1
            optionList =
                getChoiceItemList(question.option, question.answer, answerList)
            if (answerList.isNotEmpty()) {
                answerString = "答案：${question.answer.joinToString("")}"
                explain = question.explain
            } else {
                answerString = null
                explain = null
            }
        }
    }

    private fun getChoiceItemList(
        optionMap: Map<String, String>,
        trueAnswer: Set<String>,
        choiceList: Set<String>
    ): List<OptionItem> {
        return if (choiceList.isEmpty())
            optionMap.map { OptionItem(it.key, it.value, false, null) }
        else {
            optionMap.map {
                val key = it.key
                when {
                    trueAnswer.contains(key) && !choiceList.contains(key) -> OptionItem(
                        it.key, it.value, true, Color.Red
                    )
                    !trueAnswer.contains(key) && choiceList.contains(key) -> OptionItem(
                        it.key, it.value, true, Color.Gray
                    )
                    trueAnswer.contains(key) && choiceList.contains(key) -> OptionItem(
                        it.key, it.value, true, Color.Green
                    )
                    else -> OptionItem(it.key, it.value, false, null)
                }
            }
        }
    }
}

data class OptionItem(
    val key: String,
    val text: String,
    val selected: Boolean,
    val color: Color?,
)