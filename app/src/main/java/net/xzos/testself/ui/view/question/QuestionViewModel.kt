package net.xzos.testself.ui.view.question

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import net.xzos.testself.core.manager.PoolManager

class QuestionViewModel(
    private val questionData: QuestionData,
) : ViewModel() {
    var question by mutableStateOf(questionData.question)
        private set

    val pageData = QuestionPageData().apply {
        renewView(questionData)
    }

    fun setAnswer(choiceList: Set<String>) {
        questionData.answerList = choiceList
        pageData.setChoiceList(questionData)
    }

    fun clickChoice(optionNum: String) {
        with(pageData) {
            val choiceList = if (isRadio) {
                listOf(optionNum)
            } else {
                val rawChoiceList = optionList.map { it.key }
                if (rawChoiceList.contains(optionNum))
                    rawChoiceList.minus(optionNum)
                else rawChoiceList.plus(optionNum)
            }
            setAnswer(choiceList.toSet())
        }
    }

    fun checkAnswer(): Boolean {
        val question = question
        pageData.renewView(questionData)
        return (questionData.question.answer == questionData.answerList).apply {
            runBlocking(Dispatchers.Default) {
                PoolManager.recordQuestion(
                    question, this@apply
                )
            }
        }
    }
}