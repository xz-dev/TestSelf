package net.xzos.testself.ui.view.question

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import net.xzos.testself.core.database.table.PoolEntity
import net.xzos.testself.core.database.table.QuestionEntity
import net.xzos.testself.core.manager.PoolManager

class PoolViewModel(
    pool: PoolEntity,
    private val reviewMode: ReviewMode
) : ViewModel() {
    var question by mutableStateOf<QuestionEntity?>(null)
        private set

    var haveNextQuestion by mutableStateOf(false)
        private set
    var haveLastQuestion by mutableStateOf(false)
        private set

    var floatingActionButtonText by mutableStateOf("提交")
    var floatingActionButtonIcon by mutableStateOf(Icons.Filled.Done)

    private val pool by mutableStateOf(pool)
    private var questionList: List<QuestionEntity> = getQuestionList(pool)

    fun renewData() {
        question = questionList.firstOrNull()
        renewStatus()
    }

    fun renewStatus() {
        val index = questionList.indexOf(question)
        haveNextQuestion = index < questionList.size - 1
        haveLastQuestion = index > 0
    }

    fun floatingActionButtonClick(answerRight: Boolean) {
        if (answerRight) {
            floatingActionButtonText = "提交"
            floatingActionButtonIcon = Icons.Filled.Done
            toNextQuestion()
        } else {
            floatingActionButtonIcon = Icons.Filled.ArrowForward
            floatingActionButtonText = "下一题"
        }
    }

    private fun getQuestionList(pool: PoolEntity) =
        runBlocking(Dispatchers.Default) {
            when (reviewMode) {
                ReviewMode.LEARN -> PoolManager.getNeedAnswerQuestionList(pool)
                ReviewMode.REVIEW -> PoolManager.getNeedReviewAnswerQuestionList(pool)
                ReviewMode.REVIEW_FAIL -> PoolManager.getIncorrectAnswerQuestionList(pool)
            }
        }

    fun toNextQuestion() {
        question = questionList[questionList.indexOf(question) + 1]
        renewStatus()
    }

    fun toLastQuestion() {
        question = questionList[questionList.indexOf(question) - 1]
        renewStatus()
    }
}

enum class ReviewMode {
    LEARN,
    REVIEW,
    REVIEW_FAIL,
}