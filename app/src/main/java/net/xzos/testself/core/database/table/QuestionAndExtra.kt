package net.xzos.testself.core.database.table

import androidx.room.Embedded
import androidx.room.Relation

data class QuestionAndExtra(
    @Embedded
    val question: QuestionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "question_owner_id"
    ) val questionExtra: QuestionExtraEntity?
)