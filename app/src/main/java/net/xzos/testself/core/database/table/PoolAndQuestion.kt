package net.xzos.testself.core.database.table

import androidx.room.Embedded
import androidx.room.Relation

data class PoolAndQuestion(
    @Embedded val pool: PoolEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "pool_owner_id"
    ) val questionList: List<QuestionEntity>
)