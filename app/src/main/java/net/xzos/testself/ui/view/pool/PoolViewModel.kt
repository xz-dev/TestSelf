package net.xzos.testself.ui.view.pool

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import net.xzos.testself.core.database.metaDatabase
import net.xzos.testself.core.database.table.PoolEntity
import net.xzos.testself.core.database.table.QuestionEntity
import net.xzos.testself.core.manager.PoolManager

class PoolViewModel(
    pool: PoolEntity? = null,
    poolList: List<PoolEntity> = listOf(),
    questionList: List<QuestionEntity> = listOf(),
) : ViewModel() {
    var poolList by mutableStateOf(poolList)

    var currentPool by mutableStateOf(pool)
        private set

    var questionList by mutableStateOf(questionList)
        private set

    fun renew() {
        val pool = runBlocking(Dispatchers.Default) { PoolManager.getEnablePool() }
        val poolList = runBlocking(Dispatchers.Default) { PoolManager.getPoolList() }
        val questionList = runBlocking(Dispatchers.Default) { pool?.let { PoolManager.getQuestionList(it) } ?: listOf() }
        currentPool = pool
        this.poolList = poolList
        this.questionList = questionList
    }

    fun setPool(pool: PoolEntity?) {
        pool?.let {
            runBlocking(Dispatchers.Default) {
                PoolManager.enablePool(it)
            }
        }
        currentPool = pool
    }
}
