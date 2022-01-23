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
import net.xzos.testself.core.manager.PoolManager

class PoolViewModel(
    pool: PoolEntity,
    private val reviewMode: ReviewMode
) : ViewModel() {
    val questionDataList by mutableStateOf(getQuestionList(pool).map { QuestionData(it) })
    var question by mutableStateOf(questionDataList.firstOrNull())
        private set

    var haveNextQuestion by mutableStateOf(getNextQuestion() != null)
        private set
    var haveLastQuestion by mutableStateOf(getLastQuestion() != null)
        private set

    val quit by mutableStateOf(question != null)

    var floatingActionButtonText by mutableStateOf("提交")
    var floatingActionButtonIcon by mutableStateOf(Icons.Filled.Done)

    private val pool by mutableStateOf(pool)

    fun floatingActionButtonClick() {
        if (floatingActionButtonText == "下一题") {
            toNextQuestion()
            floatingActionButtonText = "提交"
            floatingActionButtonIcon = Icons.Filled.Done
        } else {
            floatingActionButtonText = "下一题"
            floatingActionButtonIcon = Icons.Filled.ArrowForward
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

    fun getNextQuestion() = try {
        questionDataList[questionDataList.indexOf(question) + 1]
    } catch (e: IndexOutOfBoundsException) {
        null
    }

    fun getLastQuestion() = try {
        questionDataList[questionDataList.indexOf(question) - 1]
    } catch (e: IndexOutOfBoundsException) {
        null
    }

    fun toNextQuestion() {
        question = getNextQuestion()
    }

    fun toLastQuestion() {
        question = getLastQuestion()
    }
}

enum class ReviewMode {
    LEARN,
    REVIEW,
    REVIEW_FAIL,
}