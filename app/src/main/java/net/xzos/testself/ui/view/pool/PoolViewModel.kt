package net.xzos.testself.ui.view.pool

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
    pool: PoolEntity? = null,
    poolList: List<PoolEntity> = listOf(),
) : ViewModel() {
    var poolList by mutableStateOf(poolList)
        private set

    var currentPool by mutableStateOf(pool)
        private set

    var questionList by mutableStateOf(listOf<QuestionEntity>())
        private set

    fun renew() {
        val pool = runBlocking(Dispatchers.Default) { PoolManager.getEnablePool() }
        currentPool = pool
        poolList = runBlocking(Dispatchers.Default) { PoolManager.getPoolList() }
        questionList = runBlocking(Dispatchers.Default) {
            pool?.let { PoolManager.getQuestionList(it) } ?: listOf()
        }
    }

    fun setPool(pool: PoolEntity?) {
        pool?.let {
            runBlocking(Dispatchers.Default) {
                PoolManager.enablePool(it)
            }
        }
        renew()
    }
}