package net.xzos.testself.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import net.xzos.testself.core.database.table.QuestionAndExtra
import net.xzos.testself.core.database.table.QuestionEntity
import net.xzos.testself.core.database.table.QuestionExtraEntity

@Dao
interface QuestionDao : BaseDao<QuestionEntity> {
    @Query("SELECT * FROM question WHERE id = :id")
    suspend fun getItem(id: String): QuestionEntity?

    @Query("SELECT * FROM question")
    suspend fun loadAll(): List<QuestionEntity>

    @Query("DELETE FROM question WHERE id = :id")
    suspend fun deleteById(id: String)

    @Transaction
    @Query("SELECT * FROM question WHERE id = :id")
    fun getQuestionAndExtra(id:String): QuestionAndExtra
}