package net.xzos.testself.ui.view.question

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import net.xzos.testself.core.database.table.QuestionEntity
import net.xzos.testself.core.manager.PoolManager

class QuestionViewModel(
    question: QuestionEntity,
) : ViewModel() {
    var question by mutableStateOf(question)
        private set

    var isRadio by mutableStateOf(question.answer.size <= 1)

    var choiceList by mutableStateOf(setOf<String>())
        private set

    val explain by mutableStateOf(question.explain)
    var explainShow by mutableStateOf(false)

    fun setQuestionView(question: QuestionEntity) {
        this.question = question
    }

    fun setChoice(optionNum: String) {
        choiceList = if (isRadio) {
            setOf(optionNum)
        } else {
            if (optionNum in choiceList)
                choiceList.minus(optionNum)
            else choiceList.plus(optionNum)
        }
    }

    fun checkAnswer(): Boolean {
        val question = question
        val answerList = question.answer.toSet()
        return (if (choiceList != answerList) {
            choiceList = answerList
            explainShow = true
            false
        } else {
            explainShow = false
            true
        }).also {
            runBlocking(Dispatchers.Default) {
                PoolManager.recordQuestion(question, it && !explainShow)
            }
        }
    }
}