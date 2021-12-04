package net.xzos.testself.core.manager

import net.xzos.testself.core.database.metaDatabase
import net.xzos.testself.core.database.table.PoolEntity
import net.xzos.testself.core.database.table.QuestionEntity

object PoolManager {
    suspend fun getPoolList(): List<PoolEntity> = metaDatabase.poolDao().loadAll()

    suspend fun enablePool(pool: PoolEntity) {
        val enablePool = getEnablePool()
        if (enablePool != pool) {
            enablePool?.apply { enable = false }?.let {
                metaDatabase.poolDao().update(it)
            }
            metaDatabase.poolDao().update(pool.apply { enable = true })
        }
    }

    suspend fun getEnablePool() = metaDatabase.poolDao().getEnableItem()

    suspend fun savePool(poolName: String): PoolEntity {
        val dao = metaDatabase.poolDao()
        return dao.getItemByName(poolName) ?: kotlin.run {
            val id = PoolEntity(poolName).let {
                dao.insert(it)
            }
            dao.getItem(id)!!
        }
    }

    suspend fun saveQuestion(questionEntity: QuestionEntity) {
        val dao = metaDatabase.questionDao()
        dao.getItem(questionEntity.id)?.also {
            dao.update(questionEntity)
        } ?: kotlin.run {
            dao.insert(questionEntity)
        }
    }

    suspend fun getQuestionList(pool: PoolEntity): List<QuestionEntity> =
        metaDatabase.poolDao().getPoolAndQuestion(pool.id).questionList

    suspend fun getNeedAnswerQuestionList(pool: PoolEntity): List<QuestionEntity> =
        metaDatabase.poolDao().getPoolAndQuestion(pool.id).questionList.filter {
            ExtraManager.getQuestionExtra(it).answerable
        }

    suspend fun recordQuestion(question: QuestionEntity, answerRight: Boolean) {
        val questionExtra = ExtraManager.getQuestionExtra(question)
        if (answerRight) {
            questionExtra.ansRightNum += 1
            questionExtra.answerable = false
        } else{
            questionExtra.ansRightNum -= 1
        }
        ExtraManager.saveQuestionExtra(questionExtra)
    }

    suspend fun enableQuestionAnswer(question: QuestionEntity) {
        val questionExtra = ExtraManager.getQuestionExtra(question)
        questionExtra.answerable = true
        ExtraManager.saveQuestionExtra(questionExtra)
    }
}