package net.xzos.testself.core.manager

import net.xzos.testself.core.database.metaDatabase
import net.xzos.testself.core.database.table.QuestionEntity
import net.xzos.testself.core.database.table.QuestionExtraEntity

object ExtraManager {
    suspend fun getQuestionExtra(question: QuestionEntity): QuestionExtraEntity {
        val questionId = question.id
        return metaDatabase.questionDao().getQuestionAndExtra(questionId).questionExtra
            ?: QuestionExtraEntity(questionId).apply {
                metaDatabase.questionExtraDao().insert(this)
            }
    }

    suspend fun saveQuestionExtra(questionExtra: QuestionExtraEntity) {
        metaDatabase.questionExtraDao().update(questionExtra)
    }

    suspend fun deleteQuestionExtra(question: QuestionEntity) {
        metaDatabase.questionDao().getQuestionAndExtra(question.id).questionExtra?.let {
            metaDatabase.questionExtraDao().delete(it)
        }
    }
}